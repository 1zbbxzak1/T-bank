import sys

sys.setrecursionlimit(10**9)

def dfs(v, visited, component):
    visited.add(v)
    component.append(v)
    for w in graph[v]:
        if w not in visited:
            dfs(w, visited, component)


n, m = map(int, sys.stdin.readline().split())
graph = {i: [] for i in range(1, n + 1)}
visited = set()
components = []

for i in range(m):
    a, b = map(int, sys.stdin.readline().split())
    graph[a].append(b)
    graph[b].append(a)

for j in range(1, n + 1):
    if j not in visited:
        stack_two = []
        dfs(j, visited, stack_two)
        components.append(stack_two)

print(len(components))

for stack_two in components:
    print(len(stack_two))
    print(*sorted(stack_two))

