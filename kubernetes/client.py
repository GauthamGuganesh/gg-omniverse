from pprint import pprint
from matplotlib import pyplot as plt
from io import BytesIO
from PIL import Image

import requests
import base64
import json
import numpy as np

# Text-Detection client
image = open('Deepa_DL.jpg', 'rb').read()

r = requests.post(url = 'http://localhost:80/detect', files = { 'image' : image })

image_data_string = r.json()['data']

image_data = image_data_string.split('\'')[1]
decoded_image = base64.b64decode(image_data)

img = np.array(Image.open(BytesIO(decoded_image)))
plt.imsave('result.jpg', img)


# # Template-recognition client
# payload = json.load(open('input.json'))
#
# r2 = requests.post(url = 'http://ocr.template-recognition.com/score', json = payload)
# print(r2.json()['template_type'])
