import sys
import os
from google_images_download import google_images_download

BASE_DIR = os.path.join(os.path.dirname(__file__), 'data')
DOWNLOAD_DIR = os.path.join(BASE_DIR, 'downloaded_images')

IMAGE_LIMIT = 100  # How many images should we download?
ERROR_TOLERANCE = (IMAGE_LIMIT // 5)  # What to do if we miss a few?

DELAY = 1  # How long do we wait between downloading images?


def sort_file(path: str):
    sorted_stuff = []

    # read lines
    with open(path, 'r') as f:
        for line in f.readlines():
            line = line.strip()
            sorted_stuff.append(line)

    sorted_stuff = sorted(sorted_stuff)

    with open(path, 'w') as f:
        for i in range(0, len(sorted_stuff)):
            item = sorted_stuff[i]

            f.write(item)

            if i + 1 < len(sorted_stuff):
                f.write('\n')


def number_of_files(path: str) -> int:
    return sum([len(files) for r, d, files in os.walk(path)])


if __name__ == '__main__':
    if not os.path.exists(DOWNLOAD_DIR):
        os.mkdir(DOWNLOAD_DIR)

    sort_file('data/keywords.txt')

    with open('data/keywords.txt', 'r') as file:
        for item in file.readlines():
            item = item.strip()

            response = google_images_download.googleimagesdownload()
            args = {
                'keywords': item,
                'limit': IMAGE_LIMIT,
                'print_urls': False,
                'print_paths': False,
                'output_directory': DOWNLOAD_DIR,
                'delay': DELAY,
            }

            filenum = number_of_files(os.path.join(DOWNLOAD_DIR, item))

            if filenum + ERROR_TOLERANCE < IMAGE_LIMIT:
                print(f" DL ( {item:20s} )[{filenum:^5d}<{IMAGE_LIMIT:^5d}] ± {ERROR_TOLERANCE}")
                paths = response.download(args)
                print(paths)
            else:
                print(f"SKIP( {item:20s} )[{filenum:^5d}/{IMAGE_LIMIT:^5d}] ± {ERROR_TOLERANCE}")
