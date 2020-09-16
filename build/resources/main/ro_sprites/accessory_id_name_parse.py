acc_ids = "acc_ids"
acc_name = "acc_men"

acc_ids_name = "acc_ids_name"

acc_ids_f = open(acc_ids, "r")
acc_dic = {}
for x in acc_ids_f:
    x = x.rstrip("\n")
    inf = x.rsplit("=")
    acc_dic[inf[0]] = inf[1]

acc_name_f = open(acc_name, "r")
for x in acc_name_f:
    x = x.rstrip("\n")
    inf = x.rsplit("=")
    print("{}={}".format(acc_dic[inf[0]], inf[1]))