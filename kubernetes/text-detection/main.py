from flask import Flask, request

from src.utils.parallel import DataParallelModel
from src.craft_model import CRAFT
from train_synth import synthesize

import torch
import os
import json
import flask
from pprint import pprint
import train_synth.config as config

app = Flask('text-detection-service')
model = None


def load_model():
    global model
    model = CRAFT()
    model = DataParallelModel(model)

    model_path = os.getcwd() + '/model/iter_0_ICDAR2013_82.01.pkl'

    if config.use_cuda:
        model = model.cuda()
        saved_model = torch.load(model_path)
    else:
        saved_model = torch.load(model_path, map_location='cpu')

    if 'state_dict' in saved_model.keys():
        model.load_state_dict(saved_model['state_dict'])
    else:
        model.load_state_dict(saved_model)


@app.route('/detect', methods=['POST'])
def detect_text():

    image_files = { }
    for file_name, file in request.files.items():
        image_files[file_name] = file.read()

    return flask.jsonify(synthesize.main(image_files, model))


if __name__ == '__main__':
    load_model()
    app.run(host='0.0.0.0', port=80)

    # Testing block for text-detection functionality (synthesize.py)
    # input_image_path = os.getcwd() + '/client/input_images'
    # input_files = { }

    # for name in os.listdir(input_image_path):
    #     image = open(input_image_path + '/' + name, 'rb').read()
    #     input_files[name] = image
    #     break
    
    # word_locations = synthesize.main(input_files, model)

    # for word_boxes in word_locations.values():
    #     for i,box in enumerate(word_boxes):
    #         print(i)
    #         for coordinates in box:
    #             print(coordinates)
    #         print('################################')



