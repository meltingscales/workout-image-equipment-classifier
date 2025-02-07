
FOR %%X IN ( data/real_images/* ) DO (

	pipenv run python label_image.py^
	--graph=./retrained_model/output_graph.pb^
	--labels=./retrained_model/output_labels.txt^
	--input_layer=Placeholder^
	--output_layer=final_result^
	--image=./data/real_images/%%X
)

PAUSE