import sys


class SegmentTree:
    def __init__(self, n):
        self.size = n
        self.tree = [(0, 0)] * (4 * self.size)

    def update(self, idx, left, right, l, r, val):
        if l > r:
            return
        if l == left and r == right:
            self.tree[idx] = (self.tree[idx][0], self.tree[idx][1] + val)
        else:
            mid = (left + right) // 2
            self.update(2 * idx, left, mid, l, min(r, mid), val)
            self.update(2 * idx + 1, mid + 1, right, max(l, mid + 1), r, val)
            left_min, left_count = self.tree[2 * idx]
            right_min, right_count = self.tree[2 * idx + 1]
            self.tree[idx] = (min(left_min + left_count, right_min + right_count), self.tree[idx][1])

    def get_min(self, idx, left, right, l, r):
        if l > r:
            return float('inf')
        tree_min, count = self.tree[idx]
        if l == left and r == right:
            return tree_min + count
        mid = (left + right) // 2
        return min(
            self.get_min(2 * idx, left, mid, l, min(r, mid)),
            self.get_min(2 * idx + 1, mid + 1, right, max(l, mid + 1), r)
        ) + count


n, m = map(int, sys.stdin.readline().split())
segment_tree = SegmentTree(n)

for _ in range(m):
    operation, *args = map(int, sys.stdin.readline().split())
    if operation == 1:
        segment_tree.update(1, 0, n - 1, args[0], args[1] - 1, args[2])
    elif operation == 2:
        min_val = segment_tree.get_min(1, 0, n - 1, args[0], args[1] - 1)
        print(min_val)
