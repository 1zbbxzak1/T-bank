import sys


class Vertex:
    def __init__(self, parent):
        self.parent = parent
        self.size = 1


def find(vertex, x):
    if vertex[x].parent != x:
        vertex[x].parent = find(vertex, vertex[x].parent)
    return vertex[x].parent


def union(vertex, x, y):
    x = find(vertex, x)
    y = find(vertex, y)
    if x == y:
        return
    if vertex[x].size > vertex[y].size:
        x, y = y, x
    vertex[x].parent = y
    vertex[y].size += vertex[x].size


def kruskal(vertex, edge):
    edge.sort(key=lambda x: x[2])
    cost = 0
    for start, end, weight in edge:
        if find(vertex, start) != find(vertex, end):
            cost += weight
            union(vertex, start, end)
    return cost


n, m = map(int, sys.stdin.readline().split())

vertexes = [Vertex(i) for i in range(n + 1)]

edges = []
for _ in range(m):
    start, end, weight = map(int, sys.stdin.readline().split())
    edges.append((start, end, weight))

print(kruskal(vertexes, edges))
