import heapq
import sys


def dijkstra(arr):
    finish = [False] * (n + 1)
    queue = [(0, -1)]
    while queue:
        current_cup = heapq.heappop(queue)
        max_t = -current_cup[0]
        u = -current_cup[1]
        finish[u] = True
        if max_t > 1440:
            return 0
        if u == n:
            return max_t
        for way in graph[u]:
            if way[2] >= arr and not finish[way[0]]:
                template = max_t + way[1]
                if template <= 1440:
                    heapq.heappush(queue, (-template, -way[0]))
    return 0


n, mid = map(int, sys.stdin.readline().split())
graph = [[] for _ in range(n + 1)]

if n == 1:
    print(10000000)
    exit()

q = set()

for _ in range(mid):
    u, w, time, cost = map(int, sys.stdin.readline().split())
    cups = (cost - 3000000) // 100
    if cups <= 0:
        continue
    graph[u].append((w, time, cups))
    graph[w].append((u, time, cups))
    q.add(cups)

un = sorted(q)
left = 0
right = len(un) - 1
max_c = 0

while left <= right:
    mid = (left + right) // 2
    min_t = dijkstra(un[mid])
    if min_t == 0:
        right = mid - 1
    else:
        left = mid + 1
        if un[mid] > max_c:
            max_c = un[mid]

print(max_c)
