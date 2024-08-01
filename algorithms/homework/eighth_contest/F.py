class SegmentTree:
    def __init__(self, n):
        self.tree = [0] * (n + 1)
        self.size = n

    def get(self, i):
        ans = 0
        while i:
            ans += self.tree[i]
            i -= i & -i
        return ans

    def update(self, i):
        while i <= self.size:
            self.tree[i] += 1
            i += i & -i


n = int(input())
arr = []
idx = [0] * (n + 1)

inputs = list(map(int, input().split()))
for i, x in enumerate(inputs, 1):
    arr.append((x, i))

arr.sort(reverse=True)

for i in range(1, n + 1):
    idx[arr[i - 1][1]] = i

result = 0
segment_tree = SegmentTree(n)

for i in range(1, n + 1):
    temp = segment_tree.get(idx[i])
    result += temp * (n - i - idx[i] + temp + 1)
    segment_tree.update(idx[i])

print(result)
