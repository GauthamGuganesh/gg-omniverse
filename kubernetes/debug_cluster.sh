#!/bin/bash
curl -v -H 'Content-Type:application/json' -d @input.json -X POST http://document-ocr.com/score
