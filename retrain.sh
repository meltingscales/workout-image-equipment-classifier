#!/bin/bash

pipenv run python retrain.py --image_dir data/downloaded_images/ --logdir /tmp/retrain_logs

cp /tmp/output_graph.pb   ./retrained_model/output_graph.pb
cp /tmp/output_labels.txt ./retrained_model/output_labels.txt