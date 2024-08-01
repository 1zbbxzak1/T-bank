def floyd_warshall(graph, n):
    dist = [[float('inf')] * (n + 1) for _ in range(n + 1)]

    for u, v, w in graph:
        dist[u][v] = w
        dist[v][u] = w

    for i in range(1, n + 1):
        dist[i][i] = 0

    for k in range(1, n + 1):
        for i in range(1, n + 1):
            for j in range(1, n + 1):
                dist[i][j] = min(dist[i][j], dist[i][k] + dist[k][j])

    return dist


def find_optimal_city(graph, n):
    dist = floyd_warshall(graph, n)

    answer = [0] * ( n + 1)
    answer[0] = float('inf')

    city = -1
    max_min_dist = float('inf')
    for i in range(1, n + 1):
        max_val = max(dist[i][1:])
        if max_val < max_min_dist:
            city = i
            max_min_dist = max_val

    return city


N, M = map(int, input().split())
graph = [tuple(map(int, input().split())) for _ in range(M)]

result = find_optimal_city(graph, N)
print(result)
