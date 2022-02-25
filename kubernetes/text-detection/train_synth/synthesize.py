import train_synth.config as config
from src.utils.data_manipulation import denormalize_mean_variance
from train_synth.dataloader import DataLoaderEval
from src.utils.utils import generate_word_bbox, _init_fn

import cv2
import time
from torch.utils.data import DataLoader
import torch
from tqdm import tqdm
import os
import numpy as np


os.environ['CUDA_VISIBLE_DEVICES'] = str(config.num_cuda)  # Specify which GPU you want to use


def synthesize(dataloader, model):

	word_locations = { }
	with torch.no_grad():

		model.eval()
		iterator = tqdm(dataloader)

		for no, (image, image_name, original_dim) in enumerate(iterator):

			t0 = time.time()
			if config.use_cuda:
				image = image.cuda()

			output = model(image)

			if type(output) == list:

				# If using custom DataParallelModel this is necessary to convert the list to tensor
				output = torch.cat(output, dim=0)

			output = output.data.cpu().numpy()
			output[output < 0] = 0
			output[output > 1] = 1
			original_dim = original_dim.cpu().numpy()

			t1 = time.time()
			print("***Computation time : {:.3f}".format(t1-t0))

			for i in range(output.shape[0]):

				# --------- Resizing it back to the original image size and saving it ----------- #
				image_i = denormalize_mean_variance(image[i].data.cpu().numpy().transpose(1, 2, 0))

				max_dim = original_dim[i].max()
				resizing_factor = 768/max_dim
				before_pad_dim = [int(original_dim[i][0]*resizing_factor), int(original_dim[i][1]*resizing_factor)]

				output[i, :, :, :] = np.uint8(output[i, :, :, :]*255)

				height_pad = (768 - before_pad_dim[0])//2
				width_pad = (768 - before_pad_dim[1])//2

				image_i = cv2.resize(
					image_i[height_pad:height_pad + before_pad_dim[0], width_pad:width_pad + before_pad_dim[1]],
					(original_dim[i][1], original_dim[i][0])
				)

				character_bbox = cv2.resize(
					output[i, 0, height_pad:height_pad + before_pad_dim[0], width_pad:width_pad + before_pad_dim[1]],
					(original_dim[i][1], original_dim[i][0])
				)/255

				affinity_bbox = cv2.resize(
					output[i, 1, height_pad:height_pad + before_pad_dim[0], width_pad:width_pad + before_pad_dim[1]],
					(original_dim[i][1], original_dim[i][0])
				)/255

				t0 = time.time()

				predicted_bbox = generate_word_bbox(
					character_bbox,
					affinity_bbox,
					character_threshold=config.threshold_character,
					affinity_threshold=config.threshold_affinity,
					word_threshold=config.threshold_word,
					character_threshold_upper=config.threshold_character_upper,
					affinity_threshold_upper=config.threshold_affinity_upper,
					scaling_character=config.scale_character,
					scaling_affinity=config.scale_affinity
				)
				t1 = time.time()
				print("Time to predict bounding boxes : {:.3f}".format(t1-t0))

				word_bbox = predicted_bbox['word_bbox']

				word_image = image_i.copy()
				cv2.drawContours(word_image, word_bbox, -1, (255, 0, 0), 2)

				word_locations[image_name[i]] = predicted_bbox['word_bbox'].tolist()

	return word_locations


def main(image_files, model):
	# Dataloader to pre-process images given in the folder
	infer_dataloader = DataLoaderEval(image_files)
	infer_dataloader = DataLoader(
		infer_dataloader, batch_size=None,
		shuffle=False, num_workers=4, worker_init_fn=_init_fn)

	t0 = time.time()
	result = synthesize(infer_dataloader, model)
	t1 = time.time()
	print("Synthesize time : {:.3f}".format(t1-t0))

	return result