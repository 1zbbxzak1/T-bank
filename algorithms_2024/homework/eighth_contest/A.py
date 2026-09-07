# import sys
#
#
# def replace_element(element, replacement):
#     a[element] = replacement
#
#     for i in range(0, n + 1):
#         prefix_sum[i] = prefix_sum[i - 1] + a[i - 1]
#
#
# def find_count_of_nods(l, r):
#     return prefix_sum[r] - prefix_sum[l]
#
#
# n, m = map(int, sys.stdin.readline().split())
# a = list(map(int, sys.stdin.readline().split()))
#
# prefix_sum = [0] * (n + 1)
# for i in range(0, n + 1):
#     prefix_sum[i] = prefix_sum[i - 1] + a[i - 1]
#
# for _ in range(n):
#     operation, first, second = map(int, sys.stdin.readline().split())
#     if operation == 1:
#         replace_element(first, second)
#
#     if operation == 2:
#         print(find_count_of_nods(first, second))
#

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

    def update(self, idx, left, right, pos, val):
        if left == right:
            self.tree[idx] = val
        else:
            mid = (left + right) // 2
            if pos <= mid:
                self.update(2 * idx + 1, left, mid, pos, val)
            else:
                self.update(2 * idx + 2, mid + 1, right, pos, val)
            self.tree[idx] = self.tree[2 * idx + 1] + self.tree[2 * idx + 2]

    def query(self, idx, left, right, qleft, qright):
        if qleft > right or qright < left:
            return 0
        elif qleft <= left and qright >= right:
            return self.tree[idx]
        else:
            mid = (left + right) // 2
            left_sum = self.query(2 * idx + 1, left, mid, qleft, qright)
            right_sum = self.query(2 * idx + 2, mid + 1, right, qleft, qright)
            return left_sum + right_sum


n, m = map(int, sys.stdin.readline().split())
a = list(map(int, sys.stdin.readline().split()))

segment_tree = SegmentTree(a)

for _ in range(m):
    operation, first, second = map(int, sys.stdin.readline().split())
    if operation == 1:
        segment_tree.update(0, 0, n - 1, first, second)
    elif operation == 2:
        print(segment_tree.query(0, 0, n - 1, first, second - 1))
