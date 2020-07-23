# Process al BMP to Transparent PNG.
import os
from PIL import Image

inputdir = '/Users/sebastianturen/Downloads/guilds'
outputdir = '/Users/sebastianturen/Downloads/guilds/r'

for fichier in os.listdir(inputdir):
    if fichier.endswith(".bmp"):
        img = Image.open(fichier)
        img = img.convert("RGBA")
        datas = img.getdata()
        # remove RO Background
        newData = []
        for item in datas:
            if item[0] == 255 and item[1] == 0 and item[2] == 255:
                newData.append((255, 255, 255, 0))
            else:
                newData.append(item)
        # save new image
        img.putdata(newData)
        img.save("{}/{}.png".format(outputdir, os.path.splitext(fichier)[0]), "PNG")
