def min_cost(n, costs):
    dp = [0] * n
    dp[0] = costs[0]
    for i in range(1, n):
        dp[i] += min(dp[i - 1], dp[i - 2]) + costs[i]

    return dp[-1]


n = int(input())
costs = list(map(int, input().split()))
print(min_cost(n, costs))