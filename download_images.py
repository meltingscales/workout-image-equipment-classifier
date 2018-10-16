import sys
import os
from google_images_download import google_images_download

BASE_DIR = os.path.join(os.path.dirname(__file__), 'data')
DOWNLOAD_DIR = os.path.join(BASE_DIR, 'downloaded_images')
IMAGE_LIMIT = 5  # How many images should we download?

if not os.path.exists(DOWNLOAD_DIR):
    os.mkdir(DOWNLOAD_DIR)


def number_of_files(path: str) -> int:
    return sum([len(files) for r, d, files in os.walk(path)])


with open('data/keywords.txt', 'r') as file:
    for item in file.readlines():
        item = item.strip()

        response = google_images_download.googleimagesdownload()
        args = {
            'keywords': item,
            'limit': IMAGE_LIMIT,
            'print_urls': False,
            'print_paths': False,
            'output_directory': DOWNLOAD_DIR
        }

        filenum = number_of_files(os.path.join(DOWNLOAD_DIR, item))

        if filenum < IMAGE_LIMIT:
            paths = response.download(args)
            print(paths)
        else:
            print(f"SKIP( {item:20s} )[{filenum:^5d}/{IMAGE_LIMIT:^5d}]")
