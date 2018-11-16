#!/bin/bash

for filename in ./data/real_images/*; do

	pipenv run python classify_image.py\
	--image_file=data/real_images/$filename
	
done