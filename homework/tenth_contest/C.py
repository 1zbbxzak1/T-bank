class DSU:
    def __init__(self, size):
        self.parent = list(range(size))
        self.size = [1] * size

    def find(self, x):
        if self.parent[x] != x:
            self.parent[x] = self.find(self.parent[x])
        return self.parent[x]

    def union(self, x, y):
        x = self.find(x)
        y = self.find(y)
        if x == y:
            return
        if self.size[x] > self.size[y]:
            x, y = y, x
        self.parent[x] = y
        self.size[y] += self.size[x]


def process_edges(dsu, edges):
    result, edges_result = 0, []
    for e in edges:
        start, end, weight = e
        if dsu.find(start) != dsu.find(end):
            result += weight
            edges_result.append(e)
            dsu.union(start, end)

    return result, edges_result


n, m = map(int, input().split())
edges = []
dsu = DSU(n * m)

for i in range(n):
    row = list(map(int, input().split()))
    for j in range(m):
        current_val = m * i + j
        val = row[j]
        if val in {1, 3}:
            dsu.union(current_val, m * (i + 1) + j)
        if val in {2, 3}:
            dsu.union(current_val, m * i + (j + 1))

        if j != m - 1:
            edges.append((current_val, m * i + (j + 1), 2))
        if i != n - 1:
            edges.append((current_val, m * (i + 1) + j, 1))

edges.sort(key=lambda x: (x[2], x[0], x[1]))

result, edges_result = process_edges(dsu, edges)

print(len(edges_result), result)

for e in edges_result:
    start, end, size = e
    i = start // m
    j = start % m
    print(i + 1, j + 1, size)
