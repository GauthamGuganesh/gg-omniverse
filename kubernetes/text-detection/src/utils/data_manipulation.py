import numpy as np

def normalize_mean_variance(in_img, mean=(0.485, 0.456, 0.406), variance=(0.229, 0.224, 0.225)):
	# should be RGB order
	img = in_img.copy().astype(np.float32)

	img -= np.array([mean[0] * 255.0, mean[1] * 255.0, mean[2] * 255.0], dtype=np.float32)
	img /= np.array([variance[0] * 255.0, variance[1] * 255.0, variance[2] * 255.0], dtype=np.float32)
	return img


def denormalize_mean_variance(in_img, mean=(0.485, 0.456, 0.406), variance=(0.229, 0.224, 0.225)):
	# should be RGB order
	img = in_img.copy()
	img *= variance
	img += mean
	img *= 255.0
	img = np.clip(img, 0, 255).astype(np.uint8)
	return img
