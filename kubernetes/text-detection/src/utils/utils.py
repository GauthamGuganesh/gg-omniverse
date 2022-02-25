from shapely.geometry import Polygon
import numpy as np
import time
import cv2
import math

import threading
import concurrent.futures as futures
from concurrent.futures import ThreadPoolExecutor

executor = ThreadPoolExecutor(5)
threadlock = threading.Lock()

def executor_task(stats, labels, character_heatmap, word_threshold, link_score, text_score, k,
det, mapper):

	img_h, img_w = character_heatmap.shape

	try:
		# size filtering
		size = stats[k, cv2.CC_STAT_AREA]
		if size < 10:
			return

		where = labels == k

		# thresholding
		if np.max(character_heatmap[where]) < word_threshold:
			return

		# make segmentation map
		seg_map = np.zeros(character_heatmap.shape, dtype=np.uint8)
		seg_map[where] = 255
		seg_map[np.logical_and(link_score == 1, text_score == 0)] = 0  # remove link area

		x, y = stats[k, cv2.CC_STAT_LEFT], stats[k, cv2.CC_STAT_TOP]
		w, h = stats[k, cv2.CC_STAT_WIDTH], stats[k, cv2.CC_STAT_HEIGHT]
		niter = int(math.sqrt(size * min(w, h) / (w * h)) * 2)
		sx, ex, sy, ey = x - niter, x + w + niter + 1, y - niter, y + h + niter + 1
		# boundary check
		if sx < 0:
			sx = 0
		if sy < 0:
			sy = 0
		if ex >= img_w:
			ex = img_w
		if ey >= img_h:
			ey = img_h

		kernel = cv2.getStructuringElement(cv2.MORPH_RECT, (1 + niter, 1 + niter))
		seg_map[sy:ey, sx:ex] = cv2.dilate(seg_map[sy:ey, sx:ex], kernel)

		# make box
		np_contours = np.roll(np.array(np.where(seg_map != 0)), 1, axis=0).transpose().reshape(-1, 2)
		rectangle = cv2.minAreaRect(np_contours)
		box = cv2.boxPoints(rectangle)

		if Polygon(box).area == 0:
			return

		# align diamond-shape
		w, h = np.linalg.norm(box[0] - box[1]), np.linalg.norm(box[1] - box[2])
		box_ratio = max(w, h) / (min(w, h) + 1e-5)
		if abs(1 - box_ratio) <= 0.1:
			l, r = min(np_contours[:, 0]), max(np_contours[:, 0])
			t, b = min(np_contours[:, 1]), max(np_contours[:, 1])
			box = np.array([[l, t], [r, t], [r, b], [l, b]], dtype=np.float32)

		# make clock-wise order
		start_idx = box.sum(axis=1).argmin()
		box = np.roll(box, 4 - start_idx, 0)
		box = np.array(box)

		threadlock.acquire(1)
		det.append(box)
		mapper.append(k)
		threadlock.release()

	except Exception as e:
		# ToDo - Understand why there is a ValueError: math domain error in line
		#  niter = int(math.sqrt(size * min(w, h) / (w * h)) * 2)
		print(e)
		return


def _init_fn(worker_id):

	"""
	Function to make the pytorch dataloader deterministic
	:param worker_id: id of the parallel worker
	:return:
	"""

	np.random.seed(0 + worker_id)


def generate_word_bbox(
		character_heatmap,
		affinity_heatmap,
		character_threshold,
		affinity_threshold,
		word_threshold,
		character_threshold_upper,
		affinity_threshold_upper,
		scaling_character,
		scaling_affinity
	):

	img_h, img_w = character_heatmap.shape

	""" labeling method """
	ret, text_score = cv2.threshold(character_heatmap, character_threshold, 1, 0)
	ret, link_score = cv2.threshold(affinity_heatmap, affinity_threshold, 1, 0)

	text_score_comb = np.clip(text_score + link_score, 0, 1)

	n_labels, labels, stats, centroids = cv2.connectedComponentsWithStats(
		text_score_comb.astype(np.uint8),
		connectivity=4)

	det = []
	mapper = []

	t0 = time.time()

	future_list = []
	for k in range(1, n_labels):
		task_future = executor.submit(executor_task, stats, labels, character_heatmap, word_threshold, link_score, text_score, k,
		det, mapper)
		future_list.append(task_future)

	for task in futures.as_completed(future_list):
		task.result()

	t1 = time.time()
	print("Loop time : {:.3f}".format(t1-t0))

#   Each word_bbox has 4 coordinates(x,y). 'det' is a list of boxes. 
#   Eg: One image has 42 words (means 42 boxes) - then the result would be of the shape 42 x 4 x 2
#   42 boxes, 4 coorindates, 2 values (x and y in each coordinate)

	return { 'word_bbox': np.array(det, dtype=np.int32).reshape([len(det),4, 2]) }
