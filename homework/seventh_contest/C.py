import sys
from collections import deque


def max_coins(n, k, coins):
    dp = [-float('inf')] * n
    dp[0] = 0

    deq = deque([0])

    prev = [-1] * n

    for i in range(1, n):
        while deq and deq[0] < i - k:
            deq.popleft()

        if i < n - 1:
            dp[i] = dp[deq[0]] + coins[i - 1]
        else:
            dp[i] = dp[deq[0]]

        prev[i] = deq[0]

        while deq and dp[deq[-1]] <= dp[i]:
            deq.pop()
        deq.append(i)

    path = []
    current = n - 1
    while current != -1:
        path.append(current + 1)
        current = prev[current]
    path.reverse()

    return dp[n - 1], len(path) - 1, path


n, k = map(int, sys.stdin.readline().split())
coins = list(map(int, sys.stdin.readline().split()))

max_coins_value, jumps, path = max_coins(n, k, coins)

print(max_coins_value)
print(jumps)
print(" ".join(map(str, path)))
