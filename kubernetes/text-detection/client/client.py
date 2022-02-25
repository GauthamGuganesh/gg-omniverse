from pprint import pprint
from matplotlib import pyplot as plt
from io import BytesIO
from PIL import Image

import requests
import numpy as np
import json
import os

# Text-Detection client
input_image_path = os.getcwd() + '/client/input_images'

input_files = { }
for name in os.listdir(input_image_path):
	image = open(input_image_path + '/' + name, 'rb').read()
	input_files[name] = image


r = requests.post(url = 'http://localhost:80/detect', files = input_files)

json.dump(open('response.txt', 'w'), str(r.json()))




# To retrieve image and save it. We need to use encoding. 
# image_data_string = r.json()['data']

# image_data = image_data_string.split('\'')[1]
# decoded_image = base64.b64decode(image_data)

# img = np.array(Image.open(BytesIO(decoded_image)))
# plt.imsave('result.jpg', img)
