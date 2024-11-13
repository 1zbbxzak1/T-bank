def max_mushrooms(n, mushrooms):
    dp = [[0] * 3 for _ in range(n)]

    for j in range(3):
        if mushrooms[-1][j] == "C":
            dp[-1][j] = 1

    for i in range(n - 2, -1, -1):
        for j in range(3):
            if mushrooms[i][j] == "W":
                continue

            for k in range(j - 1, j + 2):
                if 0 <= k < 3 and mushrooms[i + 1][k] != 'W':
                    has_mushroom = 0
                    if mushrooms[i][j] == "C":
                        has_mushroom = 1

                    dp[i][j] = max(dp[i][j], dp[i + 1][k] + has_mushroom)

    return max(dp[0])


n = int(input())
mushrooms = [input() for i in range(n)]
print(max_mushrooms(n, mushrooms))