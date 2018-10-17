pipenv run python retrain.py --image_dir data\downloaded_images\ --logdir %~d0%\tmp\retrain_logs

copy "%~d0\tmp\output_graph.pb"   "%~dp0%retrained_model\output_graph.pb"
copy "%~d0\tmp\output_labels.txt" "%~dp0%retrained_model\output_labels.txt"

PAUSE