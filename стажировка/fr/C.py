import sys


def print_dir(dir, idx):
    keys = sorted(dir.keys())
    for key in keys:
        print('  ' * idx + key)
        print_dir(dir[key], idx + 1)


count_path = int(sys.stdin.readline())
dir = {}

for num_path in range(count_path):
    path = sys.stdin.readline().strip().split('/')

    curr_dir = dir
    for num_dir in range(len(path)):
        if path[num_dir] not in curr_dir:
            curr_dir[path[num_dir]] = {}
        curr_dir = curr_dir[path[num_dir]]


print_dir(dir, 0)