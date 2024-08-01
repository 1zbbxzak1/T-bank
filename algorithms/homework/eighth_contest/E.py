MOD = 10 ** 9 + 7


class FenwickTree:
    def __init__(self, size):
        self.tree = [(0, 0) for _ in range(size)]
        self.size = size

    def update(self, idx, node):
        while idx < self.size:
            if self.tree[idx][0] < node[0]:
                self.tree[idx] = node
            elif self.tree[idx][0] == node[0]:
                self.tree[idx] = (self.tree[idx][0], (self.tree[idx][1] + node[1]) % MOD)
            idx |= idx + 1

    def query(self, idx):
        max_arr = (0, 1)
        while idx >= 0:
            if max_arr[0] < self.tree[idx][0]:
                max_arr = self.tree[idx]
            elif max_arr[0] == self.tree[idx][0]:
                max_arr = (max_arr[0], (max_arr[1] + self.tree[idx][1]) % MOD)
            idx = (idx & (idx + 1)) - 1
        return max_arr


def find_count_subarray(nums):
    sorted_nums = sorted(set(nums))
    num_idx = {num: idx for idx, num in enumerate(sorted_nums)}

    tree = FenwickTree(len(sorted_nums) + 1)

    for num in nums:
        idx = num_idx[num]
        node = tree.query(idx - 1)
        tree.update(idx, (node[0] + 1, node[1]))

    return tree.query(len(sorted_nums))[1]


n = int(input())
arr = list(map(int, input().split()))
print(find_count_subarray(arr))
