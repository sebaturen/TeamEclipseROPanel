# Extract skill description from data.grf skilldesctable2.txt file

FILE = 'skilldesctable2.txt'

is_first = True
is_name = False
slug = ""
name = ""
detail = ""
with open(FILE) as fp:
    for line in fp:
        if is_first:
            slug = line[:-2]
            is_first = False
            is_name = True
        elif is_name:
            name = line[:-2]
            is_name = False
        elif line == "#\n":
            print("UPDATE skills_db SET description = \"{}\" WHERE slug = \"{}\";".format(
                detail,
                slug
                )
            )
            detail = ""
            slug = ""
            name = ""
            is_first = True
        else:
            detail = "{}{}".format(detail, line)