import sys


class SegmentTree:
    def __init__(self, arr):
        self.arr = arr
        self.size = len(arr)
        self.tree = [0] * (4 * self.size)
        self.build(0, 0, self.size - 1)

    def build(self, idx, left, right):
        if left == right:
            self.tree[idx] = (self.arr[left], 1)
        else:
            mid = (left + right) // 2
            self.build(2 * idx + 1, left, mid)
            self.build(2 * idx + 2, mid + 1, right)
            left_min, left_count = self.tree[2 * idx + 1]
            right_min, right_count = self.tree[2 * idx + 2]
            if left_min < right_min:
                self.tree[idx] = (left_min, left_count)
            elif left_min > right_min:
                self.tree[idx] = (right_min, right_count)
            else:
                self.tree[idx] = (left_min, left_count + right_count)

    def update(self, idx, left, right, pos, val):
        if left == right:
            self.tree[idx] = (val, 1)
        else:
            mid = (left + right) // 2
            if pos <= mid:
                self.update(2 * idx + 1, left, mid, pos, val)
            else:
                self.update(2 * idx + 2, mid + 1, right, pos, val)
            left_min, left_count = self.tree[2 * idx + 1]
            right_min, right_count = self.tree[2 * idx + 2]
            if left_min < right_min:
                self.tree[idx] = (left_min, left_count)
            elif left_min > right_min:
                self.tree[idx] = (right_min, right_count)
            else:
                self.tree[idx] = (left_min, left_count + right_count)

    def get_min_and_count(self, idx, left, right, l, r):
        if r < left or right < l:
            return (sys.maxsize, 0)
        if l <= left and right <= r:
            return self.tree[idx]
        mid = (left + right) // 2
        left_result = self.get_min_and_count(2 * idx + 1, left, mid, l, r)
        right_result = self.get_min_and_count(2 * idx + 2, mid + 1, right, l, r)
        left_min, left_count = left_result
        right_min, right_count = right_result
        if left_min < right_min:
            return left_result
        elif left_min > right_min:
            return right_result
        else:
            return left_min, left_count + right_count


n, m = map(int, sys.stdin.readline().split())
a = list(map(int, sys.stdin.readline().split()))

segment_tree = SegmentTree(a)

for _ in range(m):
    operation, first, second = map(int, sys.stdin.readline().split())
    if operation == 1:
        segment_tree.update(0, 0, n - 1, first, second)
    elif operation == 2:
        min_val, count_min = segment_tree.get_min_and_count(0, 0, n - 1, first, second - 1)
        print(min_val, count_min)
