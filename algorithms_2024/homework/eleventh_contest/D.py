import sys

L, N = map(int, sys.stdin.readline().split())
cuts = [0] + list(map(int, sys.stdin.readline().split())) + [L]

dp = [[0] * (N + 2) for _ in range(N + 2)]

for length in range(2, N + 3):
    for i in range(N + 2 - length):
        j = i + length
        if length == 2:
            dp[i][j] = cuts[j] - cuts[i]
        else:
            dp[i][j] = float('inf')
            for k in range(i + 1, j):
                dp[i][j] = min(dp[i][j], dp[i][k] + dp[k][j] + cuts[j] - cuts[i])

print(dp[0][N + 1])
