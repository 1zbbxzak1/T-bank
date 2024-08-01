import heapq
import sys


def dijkstra(graph, start):
    n = len(graph)
    distance = [float('inf')] * n
    distance[start] = 0
    visited = [False] * n
    pq = [(0, start)]

    while pq:
        dist, node = heapq.heappop(pq)
        if visited[node]:
            continue
        visited[node] = True
        for neighbor, weight in graph[node]:
            if not visited[neighbor]:
                new_dist = dist + weight
                if new_dist < distance[neighbor]:
                    distance[neighbor] = new_dist
                    heapq.heappush(pq, (new_dist, neighbor))

    return distance


n, m = map(int, sys.stdin.readline().split())
graph = [[] for _ in range(n)]

for _ in range(m):
    start, end, weight = map(int, sys.stdin.readline().split())
    graph[start - 1].append((end - 1, weight))
    graph[end - 1].append((start - 1, weight))

distances = dijkstra(graph, 0)
for distance in distances:
    print(distance, end=' ')
