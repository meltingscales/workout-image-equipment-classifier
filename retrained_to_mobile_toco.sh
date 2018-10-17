#!/bin/bash

IMAGE_SIZE=299

sudo pipenv run toco --graph_def_file=retrained_model/output_graph.pb --output_file=retrained_model/optimized_graph.lite --input_format=TENSORFLOW_GRAPHDEF --output_format=TFLITE --input_shape=1,${IMAGE_SIZE},${IMAGE_SIZE},3 --input_array=Placeholder --output_array=final_result --inference_type=FLOAT --input_data_type=FLOAT