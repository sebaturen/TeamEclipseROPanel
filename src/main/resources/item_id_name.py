# Extract skill description from data.grf

FILE = 'items'

with open(FILE) as fp:
    for line in fp:
        info = line.split("#")
        if (len(info) > 2):
            print("{}={}".format(info[0], info[1]))