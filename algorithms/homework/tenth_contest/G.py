import sys
import heapq


def dijkstra(edges, start, a, b, c):
    n = len(edges)
    dist = [float('inf')] * n
    dist[start] = 0
    queue = [(0, start)]
    while queue:
        cost, node = heapq.heappop(queue)
        if cost > dist[node]:
            continue
        for neighbor, weight in edges[node]:
            qwe = cost + weight
            if qwe < dist[neighbor]:
                dist[neighbor] = qwe
                heapq.heappush(queue, (qwe, neighbor))
    if dist[a] >= float('inf') or dist[b] >= float('inf') or dist[c] >= float('inf'):
        return False
    else:
        global result
        result = min(result, dist[a] + dist[b] + dist[c])
        return True


n, m = map(int, sys.stdin.readline().split())
edges = [[] for _ in range(n)]
for _ in range(m):
    u, v, w = map(int, sys.stdin.readline().split())
    edges[u - 1].append((v - 1, w))
    edges[v - 1].append((u - 1, w))

a, b, c = map(int, sys.stdin.readline().split())
a -= 1
b -= 1
c -= 1
result = sys.maxsize
if not dijkstra(edges, a, a, b, c):
    print(-1)
else:
    dijkstra(edges, b, a, b, c)
    dijkstra(edges, c, a, b, c)
    print(result)
