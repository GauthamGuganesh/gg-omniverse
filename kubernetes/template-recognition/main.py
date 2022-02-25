from flask import Flask, request

import os
import json
import numpy as np
import flask

from pprint import pprint

app = Flask('template-recognition-service')

def get_score(input_positions, word_statistics):
    input_x = input_positions[0]
    input_y = input_positions[1]

    xmean = word_statistics['meanx']
    ymean = word_statistics['meany']

    xsd = word_statistics['sdx']
    ysd = word_statistics['sdy']

#   Score calculation - Difference between input x,y and mean-x, mean-y of a word should be less than
#   2 * SD for that word.

    diff_x = abs(xmean - input_x)
    diff_y = abs(ymean - input_y)

    if (diff_x < (2 * xsd)) & (diff_y < (2 * ysd)):
        return 1
    else:
        return 0

@app.route('/score', methods=['POST'])
def get_template():
    word_positions_input = request.get_json()
    score_map = { }
    template_score = 0

    templates = json.load(open('template.json'))

    print('Input Words - ' + str([word_data['word'] for word_data in word_positions_input]))

    matched_words = []

    for template_name in templates.keys():
        template_info = templates[template_name]
        template_score = 0

    for word_data in word_positions_input:
        input_word = word_data['word']
        input_positions = word_data['positions']

        for word_statistics in template_info:
            template_word = word_statistics['word']

            if template_word.lower() == input_word.lower():
                template_score += get_score(input_positions, word_statistics)
                matched_words.append(template_word)

    score_map[template_name] = template_score

    print('\nTemplate words - ' + str([doc['word'] for doc in templates[max(score_map, key=score_map.get)]]))
    print('\nMatched words - ' + str([word for word in matched_words]))

    return flask.jsonify( { 'template_type' : max(score_map, key=score_map.get) } )


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=81)
