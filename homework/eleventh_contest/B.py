import sys


class TreeLCA:
    def __init__(self, n):
        self.n = n
        self.length = 0
        self.adj_list = [[] for _ in range(n + 1)]
        self.entry_time = [0] * (n + 1)
        self.exit_time = [0] * (n + 1)
        self.timer = 0
        self.dp = [[(0, float('inf')) for _ in range(20)] for _ in range(n + 1)]

    def dfs(self, current_node, parent=1, edge_cost=float('inf')):
        self.entry_time[current_node] = self.timer
        self.timer += 1
        self.dp[current_node][0] = (parent, edge_cost)
        for i in range(1, self.length + 1):
            self.dp[current_node][i] = (self.dp[self.dp[current_node][i - 1][0]][i - 1][0],
                                        min(self.dp[current_node][i - 1][1], self.dp[self.dp[current_node][i - 1][0]][i - 1][1]))
        for neighbor, cost in self.adj_list[current_node]:
            self.dfs(neighbor, current_node, cost)
        self.exit_time[current_node] = self.timer
        self.timer += 1

    def is_ancestor(self, x, y):
        return self.entry_time[x] <= self.entry_time[y] and self.exit_time[x] >= self.exit_time[y]

    def find_min_lca(self, x, y):
        result = float('inf')
        for i in range(self.length, -1, -1):
            if not self.is_ancestor(self.dp[x][i][0], y):
                result = min(result, self.dp[x][i][1])
                x = self.dp[x][i][0]
            if not self.is_ancestor(self.dp[y][i][0], x):
                result = min(result, self.dp[y][i][1])
                y = self.dp[y][i][0]

        if not self.is_ancestor(x, y):
            result = min(result, self.dp[x][0][1])
        if not self.is_ancestor(y, x):
            result = min(result, self.dp[y][0][1])

        return result


n = int(sys.stdin.readline().strip())
tree = TreeLCA(n)
tree.length = 1

while (1 << tree.length) <= n:
    tree.length += 1

for i in range(2, n + 1):
    parent, cost = map(int, sys.stdin.readline().split())
    tree.adj_list[parent + 1].append((i, cost))

tree.dfs(1)

m = int(input())
for _ in range(m):
    u, v = map(int, sys.stdin.readline().split())
    result = tree.find_min_lca(u + 1, v + 1)
    print(result)
