import hashlib
import os

from PIL import Image
from google_images_download import google_images_download


def iswindows():
    return os.name == 'nt'


SCRIPT_DIR = os.path.dirname(__file__)
BASE_DIR = os.path.join(SCRIPT_DIR, 'data')
DOWNLOAD_DIR = os.path.join(BASE_DIR, 'downloaded_images')
CHROMEDRIVER_LOCATION = os.path.join(SCRIPT_DIR, ('tmp/chromedriver' + ('.exe' if iswindows() else '')))

IMAGE_LIMIT = 1500  # How many images should we download?
ERROR_TOLERANCE = (IMAGE_LIMIT // 2)  # What to do if we miss a few?
DRY_RUN = False  # Should we just check, sort, and verify W/O downloading?

DELAY = 0  # How long do we wait between downloading images?


def hash_file(filepath: str) -> None:
    file = open(filepath, 'rb')
    data = file.read()
    file.close()

    return hashlib.md5(data).hexdigest()


def get_extension_maybe(filepath: str) -> str:
    try:
        img = Image.open(filepath)
        img.verify()
        itype = img.format
        return itype
    except Exception as e:
        return "UNKNOWN"


def remove_duplicate_files(path: str) -> int:
    """Note: This is susceptible to MD5 hash collisions.

    Only to be used if you don't necessarily care about 100% correctness."""
    hashes = {}
    dupes = 0

    for filepath in os.listdir(path):
        image_path = os.path.join(path, filepath)
        image_hash = hash_file(image_path)

        if image_hash in hashes:
            os.remove(image_path)
            dupes += 1

        else:
            # Record it.
            hashes[image_hash] = image_path

            # Give it the hash as a name.
            name = image_hash

            name += "." + get_extension_maybe(image_path)

            new_image_path = os.path.join(path, name)
            try:
                if image_path != new_image_path:
                    os.rename(image_path, new_image_path)
            except FileExistsError as e:  # This means we've already got a file that's got a hash as a name.
                del hashes[image_hash]  # Delete entry so we can remove the file we initially tried to rename.
            except FileNotFoundError as e:
                pass

    return dupes


def sort_file(path: str) -> None:
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
    except Exception as e:
        return False
    return True


if __name__ == '__main__':

    if not os.path.isfile(CHROMEDRIVER_LOCATION):
        print(CHROMEDRIVER_LOCATION)
        print("Above path doesn't exist.")
        print('Hey you! Download `chromedriver` and put it in `tmp/`!')
        print('http://chromedriver.chromium.org/downloads')

        temp_path = os.path.join(SCRIPT_DIR, 'tmp')

        if not os.path.exists(temp_path):
            os.mkdir(temp_path)

        exit(1)

    if DRY_RUN:
        print(f"Warning: Not downloading files. DRY_RUN is true.")

    if not os.path.exists(DOWNLOAD_DIR):
        os.mkdir(DOWNLOAD_DIR)

    # Sort the file
    sort_file('data/keywords.txt')

    f = open('data/keywords.txt', 'r')
    items = f.readlines()
    f.close()

    for item in items:
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

            if not DRY_RUN:  # actually download
                paths = response.download(args)
                print(paths)

        else:
            print(f'SKIP( {item:20s} )[{filenum:^5d}/{IMAGE_LIMIT:^5d}] ± {ERROR_TOLERANCE}')

        # Verify they're not corrupted/garbage image files
        for filename in os.listdir(itemdir):

            filepath = os.path.join(itemdir, filename)

            if not valid_picture_file(filepath):
                print(f'\t[BAD]: {filepath[-60:]}')

                try:
                    os.remove(filepath)
                except Exception as e:
                    continue

        # Remove duplicate files, and also give them MD5 hashes as names.
        remove_duplicate_files(itemdir)
