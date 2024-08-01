import sys


class SegmentTree:
    def __init__(self, arr):
        self.arr = arr
        self.size = len(arr)
        self.tree = [0] * (4 * self.size)
        self.build(1, 0, n - 1)

    def build(self, idx, left, right):
        if left == right:
            self.tree[idx] = self.arr[left]
        else:
            mid = (left + right) // 2
            self.build(2 * idx, left, mid)
            self.build(2 * idx + 1, mid + 1, right)
            self.tree[idx] = max(self.tree[2 * idx], self.tree[idx * 2 + 1])

    def update(self, idx, left, right, pos, val):
        if left == right:
            self.tree[idx] = val
        else:
            mid = (left + right) // 2
            if pos <= mid:
                self.update(2 * idx, left, mid, pos, val)
            else:
                self.update(2 * idx + 1, mid + 1, right, pos, val)
            self.tree[idx] = max(self.tree[2 * idx], self.tree[2 * idx + 1])

    def get_j(self, idx, left, right, qleft, qright, x):
        if qleft > qright:
            return sys.maxsize

        if left == right:
            if self.tree[idx] >= x:
                return left
            else:
                return sys.maxsize

        mid = (left + right) // 2

        if left < qleft:
            return min(
                self.get_j(2 * idx, left, mid, qleft, min(qright, mid), x),
                self.get_j(2 * idx + 1, mid + 1, right, max(qleft, mid + 1), qright, x)
            )

        if self.tree[2 * idx] >= x:
            return self.get_j(2 * idx, left, mid, qleft, min(qright, mid), x)
        else:
            return self.get_j(2 * idx + 1, mid + 1, right, max(qleft, mid + 1), qright, x)


n, m = map(int, sys.stdin.readline().split())
a = list(map(int, sys.stdin.readline().split()))

tree = SegmentTree(a)

for _ in range(m):
    operation, first, second = map(int, sys.stdin.readline().split())
    if operation == 1:
        tree.update(1, 0, n - 1, first, second)
    elif operation == 2:
        res = tree.get_j(1, 0, n - 1, second, n - 1, first)
        if res != sys.maxsize:
            print(res)
        else:
            print(-1)