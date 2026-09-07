import sys
from collections import defaultdict

n = int(sys.stdin.readline().strip())
g = defaultdict(list)
entry_time = [0] * 200001
exit_time = [0] * 200001
timer = 0
dp = [[0] * 20 for _ in range(200001)]


def dfs(current_node, parent=1):
    global timer
    entry_time[current_node] = timer
    timer += 1
    dp[current_node][0] = parent

    for i in range(1, 20):
        dp[current_node][i] = dp[dp[current_node][i - 1]][i - 1]

    for to in g[current_node]:
        if to != parent:
            dfs(to, current_node)

    exit_time[current_node] = timer
    timer += 1


def is_ancestor(x, y):
    return entry_time[x] <= entry_time[y] and exit_time[x] >= exit_time[y]


def lca(x, y):
    if is_ancestor(x, y):
        return x

    if is_ancestor(y, x):
        return y

    for i in range(20 - 1, -1, -1):
        if not is_ancestor(dp[x][i], y):
            x = dp[x][i]

    return dp[x][0]


x_values = map(int, sys.stdin.readline().split())
for i, x in enumerate(x_values, 2):
    g[x + 1].append(i)

dfs(1)

m = int(sys.stdin.readline().strip())
for _ in range(m):
    u, v = map(int, sys.stdin.readline().split())
    res = lca(u + 1, v + 1)
    print(res - 1)
