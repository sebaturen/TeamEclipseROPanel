import re

FILE = 'idnum2itemdesctable.txt'

id = ""
detail = ""
with open(FILE) as fp:
    for line in fp:
        if line[:2] != '//':
            id_finder = re.search("^.[0-9]*#", line)
            if id_finder:
                if len(detail) > 0:
                    print("UPDATE items_db SET description = \"{}\" WHERE ro_id = \"{}\";".format(
                        detail,
                        id
                        )
                    )
                detail = ""
                id = line[id_finder.span(0)[0]:id_finder.span(0)[1]-1]
                if len(line)-1 != id_finder.span(0)[1]:
                    detail = line[id_finder.span(0)[1]:]
            elif len(line) > 2:
                detail = "{}{}".format(detail, line)
