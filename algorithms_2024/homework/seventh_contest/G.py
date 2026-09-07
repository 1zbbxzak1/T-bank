import sys

sys.setrecursionlimit(10 ** 9)


def delete_brackets(dp, prev, s, i, j):
    if dp[i][j] == j - i + 1:
        pass

    elif dp[i][j] == 0:
        for k in range(i, j + 1):
            print(s[k], end='')

    elif prev[i][j] == -1:
        print(s[i], end='')
        delete_brackets(dp, prev, s, i + 1, j - 1)
        print(s[j], end='')

    else:
        delete_brackets(dp, prev, s, i, prev[i][j])
        delete_brackets(dp, prev, s, prev[i][j] + 1, j)


s = sys.stdin.readline().strip()
n = len(s)

dp = [[0] * n for _ in range(n)]
prev = [[0] * n for _ in range(n)]

for i in range(n):
    dp[i][i] = 1

for j in range(n):
    for i in range(j - 1, -1, -1):
        current_cost = (
            dp[i + 1][j - 1] if ((s[i] == '(' and s[j] == ')') or
                                 (s[i] == '[' and s[j] == ']') or
                                 (s[i] == '{' and s[j] == '}')) else sys.maxsize
        )

        partition = -1

        for k in range(i, j):
            if dp[i][k] + dp[k + 1][j] < current_cost:
                current_cost = dp[i][k] + dp[k + 1][j]
                partition = k

        dp[i][j] = current_cost
        prev[i][j] = partition

delete_brackets(dp, prev, s, 0, n - 1)
