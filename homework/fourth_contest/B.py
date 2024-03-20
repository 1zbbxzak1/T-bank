def get_prefix_sum(matrix):
    n = len(matrix)
    m = len(matrix[0])
    prefix_sum = [[0] * (m + 1) for _ in range(n + 1)]

    for i in range(1, n + 1):
        for j in range(1, m + 1):
            prefix_sum[i][j] = prefix_sum[i - 1][j] + prefix_sum[i][j - 1] - prefix_sum[i - 1][j - 1] + matrix[i - 1][j - 1]

    return prefix_sum


def solution(y1, x1, y2, x2, prefix_sum):
    sum = prefix_sum[y2][x2] - prefix_sum[y1 - 1][x2] - prefix_sum[y2][x1 - 1] + prefix_sum[y1 - 1][x1 - 1]
    return sum


n, m, k = map(int, input().split())
matrix = []

for _ in range(n):
    row = list(map(int, input().split()))[:m]
    matrix.append(row)

prefix_sum = get_prefix_sum(matrix)
results = []

for i in range(k):
    y1, x1, y2, x2 = map(int, input().split())
    res = solution(y1, x1, y2, x2, prefix_sum)
    results.append(res)

for result in results:
    print(result)
