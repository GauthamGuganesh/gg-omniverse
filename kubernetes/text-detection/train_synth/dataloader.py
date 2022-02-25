from torch.utils import data
import numpy as np
import cv2

from io import BytesIO
from PIL import Image
from src.utils.data_manipulation import normalize_mean_variance

class DataLoaderEval(data.Dataset):

	"""
		DataLoader for evaluation on any custom folder given the path
	"""

	def __init__(self, image_files):

		self.imnames = list(image_files.keys())
		self.images = image_files

	def __getitem__(self, item):

		image = Image.open(BytesIO(self.images[self.imnames[item]]))  # Read the image
		image = np.array(image)

		if len(image.shape) == 2:
			image = np.repeat(image[:, :, None], repeats=3, axis=2)
		elif image.shape[2] == 1:
			image = np.repeat(image, repeats=3, axis=2)
		else:
			image = image[:, :, 0: 3]

		# ------ Resize the image to (768, 768) ---------- #
		height, width, channel = image.shape
		max_side = max(height, width)
		new_resize = (int(width / max_side * 768), int(height / max_side * 768))
		image = cv2.resize(image, new_resize)

		big_image = np.ones([768, 768, 3], dtype=np.float32) * np.mean(image)
		big_image[
			(768 - image.shape[0]) // 2: (768 - image.shape[0]) // 2 + image.shape[0],
			(768 - image.shape[1]) // 2: (768 - image.shape[1]) // 2 + image.shape[1]] = image
		big_image = normalize_mean_variance(big_image)
		big_image = big_image.transpose(2, 0, 1)

		return big_image.astype(np.float32), self.imnames[item], np.array([height, width])

	def __len__(self):
		return len(self.imnames)
