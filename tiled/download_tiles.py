#!/usr/bin/env python
from io import BytesIO
from os import chdir
from pathlib import Path

import requests
from PIL import Image

# https://c.tile.openstreetmap.org/19/260634/168465.png
# https://b.tile.openstreetmap.org/19/260657/168470.png

# to find these go to:
# https://tools.geofabrik.de/map/?type=Geofabrik_Standard&grid=1
# https://mc.bbbike.org/mc
# josm

# for finer control of area:
# download excess
# crop to desired size in gnu image
# size should be power of 2 for tiles
# can view offset in gnu image
# X: -2096
# Y: -1100
# then re-split for sprites with imagemagick:
# convert map.png -crop 5x4@ map-%d.png
# https://imagemagick.org/Usage/crop/#crop_spaced

ZOOM = 19
START_X = 260632
START_Y = 168460
# END_X = 260657
# END_Y = 168470
TILES_X = 25
TILES_Y = 15
TILE_SIZE = 256
URL_FORMAT = "https://tile.openstreetmap.org/{zoom}/{x}/{y}.png"
FILE_NAME_FORMAT = "tile_{x},{y}.png"
HEADERS = {"User-Agent": "team28/heslingtonhustle/0.0.1"}
DIRECTORY = Path("dl/")
OUT_FILE = "out.png"
IMAGE_FORMATS = ("PNG",)

DIRECTORY.mkdir(exist_ok=True)
chdir(DIRECTORY)

# x_tiles = END_X - START_X
# y_tiles = END_Y - START_Y
x_tiles = TILES_X
y_tiles = TILES_Y
out_image = Image.new("RGB", (TILE_SIZE * x_tiles, TILE_SIZE * y_tiles))
for y in range(y_tiles):
    for x in range(x_tiles):
        absolute_x = START_X + x
        absolute_y = START_Y + y

        file_name = Path(FILE_NAME_FORMAT.format(x=absolute_x, y=absolute_y))
        if file_name.is_file():
            print(file_name)
            tile_image = Image.open(file_name, formats=IMAGE_FORMATS)
        else:
            url = URL_FORMAT.format(zoom=ZOOM, x=absolute_x, y=absolute_y)
            print(url)
            response = requests.get(url, headers=HEADERS)
            response.raise_for_status()
            with BytesIO(response.content) as fp:
                tile_image = Image.open(fp, formats=IMAGE_FORMATS)
                tile_image.load()
                tile_image.save(file_name)
            out_image.paste(tile_image, (TILE_SIZE * x, TILE_SIZE * y))

out_image.save(OUT_FILE)
