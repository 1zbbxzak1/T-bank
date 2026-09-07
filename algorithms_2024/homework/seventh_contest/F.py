def max_subsquare(matrix, n, m):
    max_side_length = 0
    max_squares = []

    # Поиск расстояний до ближайшего нуля справа и снизу
    nearest_zero_right = [[0] * m for _ in range(n)]
    nearest_zero_below = [[0] * m for _ in range(n)]

    for i in range(n):
        for j in range(m - 1, -1, -1):
            if matrix[i][j] == 0:
                nearest_zero_right[i][j] = j
            else:
                nearest_zero_right[i][j] = nearest_zero_right[i][j + 1] if j < m - 1 else m

    for j in range(m):
        for i in range(n - 1, -1, -1):
            if matrix[i][j] == 0:
                nearest_zero_below[i][j] = i
            else:
                nearest_zero_below[i][j] = nearest_zero_below[i + 1][j] if i < n - 1 else n

    dp = [[0] * m for _ in range(n)]

    for i in range(n):
        for j in range(m):
            if matrix[i][j] == 1:
                if i == 0 or j == 0:
                    dp[i][j] = 1
                else:
                    dp[i][j] = min(dp[i - 1][j], dp[i][j - 1], dp[i - 1][j - 1]) + 1

                if dp[i][j] > max_side_length:
                    max_side_length = dp[i][j]
                    max_squares = [(i - max_side_length + 1, j - max_side_length + 1)]
                elif dp[i][j] == max_side_length:
                    max_squares.append((i - max_side_length + 1, j - max_side_length + 1))

    return max_side_length, max_squares[-1]


n, m = map(int, input().split())
matrix = []

for _ in range(n):
    row = list(map(int, input().split()))
    matrix.append(row)

max_side_length, top_left_corner = max_subsquare(matrix, n, m)

print(max_side_length)
print(top_left_corner[0] + 1, top_left_corner[1] + 1)
