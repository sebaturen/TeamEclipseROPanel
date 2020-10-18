# Extract skill description from data.grf

FILE = 'items'
listItemsSee = []

with open(FILE) as fp:
    for line in fp:
        info = line.split("#")
        if (len(info) > 2 and info[0] not in listItemsSee):
            print("{}={}".format(info[0], info[1]))
            listItemsSee.append(info[0])