import os

from PIL import Image
from google_images_download import google_images_download

BASE_DIR = os.path.join(os.path.dirname(__file__), 'data')
DOWNLOAD_DIR = os.path.join(BASE_DIR, 'downloaded_images')

IMAGE_LIMIT = 100  # How many images should we download?
ERROR_TOLERANCE = (IMAGE_LIMIT // 5)  # Threshold for ignoring the fact we have missing/crappy/broken images

DELAY = 1  # How long do we wait in seconds between downloading images?


def number_of_files(path: str) -> int:
    return sum([len(files) for r, d, files in os.walk(path)])


def valid_picture_file(path: str, valid_formats=frozenset(['JPEG', 'JPG', 'PNG', 'GIF'])) -> bool:
    try:
        img = Image.open(path)
        img.verify()
        itype = img.format

        # If it's not in the list of formats we allow for
        if itype.upper() not in [format.upper() for format in valid_formats]:
            return False

    except(IOError, SyntaxError) as e:
        return False

    return True


if __name__ == '__main__':
    if not os.path.exists(DOWNLOAD_DIR):
        os.mkdir(DOWNLOAD_DIR)

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
                'format': 'jpg',
            }

            itemdir = os.path.join(DOWNLOAD_DIR, item)
            filenum = number_of_files(itemdir)

            if filenum + ERROR_TOLERANCE < IMAGE_LIMIT:
                print(f" DL ( {item:20s} )[{filenum:^5d}<{IMAGE_LIMIT:^5d}] ± {ERROR_TOLERANCE}")
                paths = response.download(args)

            else:
                print(f"SKIP( {item:20s} )[{filenum:^5d}/{IMAGE_LIMIT:^5d}] ± {ERROR_TOLERANCE}")

            # Verify they're not corrupted/garbage image files
            for filename in os.listdir(itemdir):

                filepath = os.path.join(itemdir, filename)

                if not valid_picture_file(filepath):
                    print(f'\t[BAD]: {filepath[-60:]}')
                    os.remove(filepath)
