import sys


class Plenty:
    def __init__(self, val):
        self.val = val
        self.parent = val
        self.min_val = val
        self.max_val = val
        self.size = 1


def union(arr, x, y):
    x = get(arr, x)
    y = get(arr, y)
    if x == y:
        return
    if arr[x].size > arr[y].size:
        x, y = y, x

    arr[x].parent = y
    arr[y].min_val = min(arr[y].min_val, arr[x].min_val)
    arr[y].max_val = max(arr[y].max_val, arr[x].max_val)
    arr[y].size += arr[x].size


def get(arr, x):
    if arr[x].parent != x:
        arr[x].parent = get(arr, arr[x].parent)
    return arr[x].parent


n, m = map(int, sys.stdin.readline().split())
arr = [Plenty(i) for i in range(n + 1)]

for _ in range(m):
    operation, *args = map(str, sys.stdin.readline().split())
    if operation == "union":
        x, y = int(args[0]), int(args[1])
        union(arr, x, y)
    else:
        x = int(args[0])
        res = get(arr, x)
        print(arr[res].min_val, arr[res].max_val, arr[res].size)
