import sys


def has_cycle(graph, visited):
    stack = []

    while stack or 0 in visited[1:]:
        if not stack:
            v = visited.index(0, 1)
            stack.append(v)
            visited[v] = 1

        v = stack[-1]
        found = False

        for u in graph[v]:
            if visited[u] == 0:
                stack.append(u)
                visited[u] = 1
                found = True
                break
            elif visited[u] == 1:
                return True

        if not found:
            visited[stack.pop()] = 2

    return False


n, m = map(int, sys.stdin.readline().split())
graph = {i: [] for i in range(1, n + 1)}
visited = [0] * (n + 1)

components = []

for i in range(m):
    a, b = map(int, sys.stdin.readline().split())
    graph[a].append(b)


print(int(has_cycle(graph, visited)))
