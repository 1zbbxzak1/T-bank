import sys
from collections import deque


def algorithm_dijkstra(k):
    distance = [1e9 for _ in range(k + 2)]
    distance[1] = 0
    queue = deque([(0, 1)])

    while queue:
        dist, node = queue.popleft()
        for neighbour in graph[node]:
            v, w = neighbour
            if distance[v] > dist + w:
                distance[v] = dist + w
                queue.append((distance[v], v))

    print(1 + distance[0])


def min_sum_digits(k):
    for i in range(1, k + 1):
        graph[(i % k)].append(((i + 1) % k, 1))
        graph[(i % k)].append(((10 * i) % k, 0))

    algorithm_dijkstra(k)


k = int(sys.stdin.readline())
graph = [[] for _ in range(k + 2)]
min_sum_digits(k)
