import sys

class SegmentTree:
    def __init__(self, arr):
        self.arr = arr
        self.tree = [0] * (4 * len(arr))
        self.lazy_assign = [None] * (4 * len(arr))
        self.lazy_add = [0] * (4 * len(arr))
        self.build_tree(0, len(arr) - 1, 0)

    def build_tree(self, start, end, idx):
        if start == end:
            self.tree[idx] = self.arr[start]
            return
        mid = (start + end) // 2
        self.build_tree(start, mid, 2 * idx + 1)
        self.build_tree(mid + 1, end, 2 * idx + 2)
        self.tree[idx] = self.tree[2 * idx + 1] + self.tree[2 * idx + 2]

    def propagate_lazy(self, idx, start, end):
        if self.lazy_assign[idx] is not None:
            self.tree[idx] = (end - start + 1) * self.lazy_assign[idx]
            if start != end:
                self.lazy_assign[2 * idx + 1] = self.lazy_assign[idx]
                self.lazy_assign[2 * idx + 2] = self.lazy_assign[idx]
                self.lazy_add[2 * idx + 1] = 0
                self.lazy_add[2 * idx + 2] = 0
            self.lazy_assign[idx] = None
        if self.lazy_add[idx] != 0:
            self.tree[idx] += (end - start + 1) * self.lazy_add[idx]
            if start != end:
                self.lazy_add[2 * idx + 1] += self.lazy_add[idx]
                self.lazy_add[2 * idx + 2] += self.lazy_add[idx]
            self.lazy_add[idx] = 0

    def update_assign(self, idx, start, end, qs, qe, val):
        self.propagate_lazy(idx, start, end)
        if qs > end or qe < start:
            return
        if qs <= start and qe >= end:
            self.lazy_assign[idx] = val
            self.propagate_lazy(idx, start, end)
            return
        mid = (start + end) // 2
        self.update_assign(2 * idx + 1, start, mid, qs, qe, val)
        self.update_assign(2 * idx + 2, mid + 1, end, qs, qe, val)
        self.tree[idx] = self.tree[2 * idx + 1] + self.tree[2 * idx + 2]

    def update_add(self, idx, start, end, qs, qe, val):
        self.propagate_lazy(idx, start, end)
        if qs > end or qe < start:
            return
        if qs <= start and qe >= end:
            self.lazy_add[idx] += val
            self.propagate_lazy(idx, start, end)
            return
        mid = (start + end) // 2
        self.update_add(2 * idx + 1, start, mid, qs, qe, val)
        self.update_add(2 * idx + 2, mid + 1, end, qs, qe, val)
        self.tree[idx] = self.tree[2 * idx + 1] + self.tree[2 * idx + 2]

    def query(self, idx, start, end, qs, qe):
        self.propagate_lazy(idx, start, end)
        if qs > end or qe < start:
            return 0
        if qs <= start and qe >= end:
            return self.tree[idx]
        mid = (start + end) // 2
        return self.query(2 * idx + 1, start, mid, qs, qe) + self.query(2 * idx + 2, mid + 1, end, qs, qe)

    def update_range(self, start, end, val, type):
        if type == 1:
            self.update_assign(0, 0, len(self.arr) - 1, start, end - 1, val)
        elif type == 2:
            self.update_add(0, 0, len(self.arr) - 1, start, end - 1, val)

    def get_sum(self, start, end):
        return self.query(0, 0, len(self.arr) - 1, start, end - 1)


n, m = map(int, sys.stdin.readline().split())
segment_tree = SegmentTree([0] * n)

for _ in range(m):
    operation, *args = map(int, sys.stdin.readline().split())
    if operation == 1 or operation == 2:
        segment_tree.update_range(args[0], args[1], args[2], operation)
    elif operation == 3:
        get_sum = segment_tree.get_sum(args[0], args[1])
        print(get_sum)
