﻿import os

from PIL import Image
from google_images_download import google_images_download


def iswindows():
    return os.name == 'nt'


SCRIPT_DIR = os.path.dirname(__file__)
BASE_DIR = os.path.join(SCRIPT_DIR, 'data')
DOWNLOAD_DIR = os.path.join(BASE_DIR, 'downloaded_images')
CHROMEDRIVER_LOCATION = os.path.join(SCRIPT_DIR, ('tmp/chromedriver' + ('.exe' if iswindows() else '')))

if not os.path.isfile(CHROMEDRIVER_LOCATION):
    print(CHROMEDRIVER_LOCATION)
    print("Above path doesn't exist.")
    print('Hey you! Download `chromedriver` and put it in `tmp/`!')
    print('http://chromedriver.chromium.org/downloads')
    exit(1)

IMAGE_LIMIT = 1000  # How many images should we download?
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
            single_item = sorted_stuff[i]

            f.write(single_item)

            if i + 1 < len(sorted_stuff):
                f.write('\n')


def number_of_files(path: str) -> int:
    return sum([len(files) for r, d, files in os.walk(path)])


def valid_picture_file(path: str, valid_formats=frozenset(['JPEG', 'JPG', 'PNG', 'GIF', 'BMP'])) -> bool:
    try:
        img = Image.open(path)
        img.verify()
        itype = img.format
        # If it's not in the list of formats we allow for
        if itype.upper() not in [good_format.upper() for good_format in valid_formats]:
            return False
    except(IOError, SyntaxError) as e:
        print(e)
        return False
    return True


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
                'chromedriver': CHROMEDRIVER_LOCATION,
            }

            filenum = number_of_files(os.path.join(DOWNLOAD_DIR, item))
            itemdir = os.path.join(DOWNLOAD_DIR, item)

            if filenum + ERROR_TOLERANCE < IMAGE_LIMIT:
                print(f' DL ( {item:20s} )[{filenum:^5d}<{IMAGE_LIMIT:^5d}] ± {ERROR_TOLERANCE}')
                paths = response.download(args)
                print(paths)
            else:
                print(f'SKIP( {item:20s} )[{filenum:^5d}/{IMAGE_LIMIT:^5d}] ± {ERROR_TOLERANCE}')

            # Verify they're not corrupted/garbage image files
            for filename in os.listdir(itemdir):

                filepath = os.path.join(itemdir, filename)

                if not valid_picture_file(filepath):
                    print(f'\t[BAD]: {filepath[-60:]}')
                    os.remove(filepath)
