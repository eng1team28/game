#!/usr/bin/env python
from io import BytesIO

import requests
from PIL import Image

# https://c.tile.openstreetmap.org/19/260634/168465.png
# https://b.tile.openstreetmap.org/19/260657/168470.png

# to find these go to:
# https://tools.geofabrik.de/map/?type=Geofabrik_Standard&grid=1
# https://mc.bbbike.org/mc
# josm

ZOOM = 19
START_X = 260632
START_Y = 168460
# END_X = 260657
# END_Y = 168470
TILES_X = 25
TILES_Y = 15
TILE_SIZE = 256
URL_FORMAT = "https://tile.openstreetmap.org/{zoom}/{x}/{y}.png"
HEADERS = {"User-Agent": "team28/heslingtonhustle/0.0.1"}

# x_tiles = END_X - START_X
# y_tiles = END_Y - START_Y
x_tiles = TILES_X
y_tiles = TILES_Y
out_image = Image.new("RGB", (TILE_SIZE * x_tiles, TILE_SIZE * y_tiles))
for y in range(y_tiles):
    for x in range(x_tiles):
        url = URL_FORMAT.format(zoom=ZOOM, x=START_X + x, y=START_Y + y)
        print(url)
        response = requests.get(url, headers=HEADERS)
        response.raise_for_status()
        with BytesIO(response.content) as fp:
            dl_image = Image.open(fp)
            out_image.paste(dl_image, (TILE_SIZE * x, TILE_SIZE * y))

out_image.save("out.png")
