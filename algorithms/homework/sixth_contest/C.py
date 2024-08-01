def is_topological_sort(graph, permutation):
    if len(permutation) != len(graph):
        return False

    position = {vertex: index for index, vertex in enumerate(permutation)}

    for u in graph:
        for v in graph[u]:
            if position[u] > position[v]:
                return False

    return True


n, m = map(int, input().split())
graph = {i: [] for i in range(1, n + 1)}

for _ in range(m):
    u, v = map(int, input().split())
    graph[u].append(v)

permutation = list(map(int, input().split()))

if is_topological_sort(graph, permutation):
    print("YES")
else:
    print("NO")
