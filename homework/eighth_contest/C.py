import sys


class SegmentTree:
    def __init__(self, arr):
        self.arr = arr
        self.size = len(arr)
        self.tree = [0] * (4 * self.size)
        self.build(0, 0, self.size - 1)

    def build(self, idx, left, right):
        if left == right:
            self.tree[idx] = self.arr[left]
        else:
            mid = (left + right) // 2
            self.build(2 * idx + 1, left, mid)
            self.build(2 * idx + 2, mid + 1, right)
            self.tree[idx] = self.tree[2 * idx + 1] + self.tree[2 * idx + 2]

    def update(self, idx, left, right, pos):
        if left == right:
            self.tree[idx] = not bool(self.tree[idx])
        else:
            mid = (left + right) // 2
            if pos <= mid:
                self.update(2 * idx + 1, left, mid, pos)
            else:
                self.update(2 * idx + 2, mid + 1, right, pos)
            self.tree[idx] = self.tree[2 * idx + 1] + self.tree[2 * idx + 2]

    def get_k(self, idx, left, right, k):
        if left == right:
            return left

        mid = (left + right) // 2
        if k <= self.tree[2 * idx + 1]:
            return self.get_k(2 * idx + 1, left, mid, k)
        else:
            return self.get_k(2 * idx + 2, mid + 1, right, k - self.tree[2 * idx + 1])


n, m = map(int, sys.stdin.readline().split())
a = list(map(int, sys.stdin.readline().split()))

segment_tree = SegmentTree(a)

for _ in range(m):
    operation, first = map(int, sys.stdin.readline().split())
    if operation == 1:
        segment_tree.update(0, 0, n - 1, first)
    elif operation == 2:
        print(segment_tree.get_k(0, 0, n - 1, first + 1))
