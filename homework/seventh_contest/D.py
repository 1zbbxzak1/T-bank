import sys

sys.setrecursionlimit(10 ** 9)


def find_count_path(s, k):
    global dp

    if 0 <= s < n and 0 <= k < m:
        if dp[s][k] == -1:
            dp[s][k] = find_count_path(s + 1, k - 2) + find_count_path(s - 1, k - 2) + find_count_path(s - 2,
                                                                                                       k + 1) + find_count_path(
                s - 2, k - 1)

        return dp[s][k]
    return 0


n, m = map(int, sys.stdin.readline().split())

dp = []
for i in range(n):
    dp.append([-1] * m)

dp[0][0] = 1

find_count_path(n - 1, m - 1)

print(dp[-1][-1])
