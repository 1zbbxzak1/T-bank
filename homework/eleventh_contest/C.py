import sys


def max_subpalindromes(a, b):
    if len(a) >= len(b):
        return a
    return b


line = sys.stdin.readline().strip()
length = len(line)
dp = [["" for _ in range(length)] for _ in range(length)]

for i in range(length):
    dp[i][i] = line[i]

for i in range(1, length):
    for j in range(i, length):
        g = j - i
        if line[g] != line[j]:
            dp[g][j] = max_subpalindromes(dp[g][j - 1], dp[g + 1][j])
        else:
            dp[g][j] = max_subpalindromes(dp[g][j - 1], max_subpalindromes(dp[g + 1][j], line[g] + dp[g + 1][j - 1] + line[j]))

print(len(dp[0][length - 1]))
print(dp[0][length - 1])
