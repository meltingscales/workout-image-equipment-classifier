FOR %%X IN ( data/real_images/* ) DO (

	pipenv run python classify_image.py^
	--image_file=data/real_images/%%X

)
PAUSE