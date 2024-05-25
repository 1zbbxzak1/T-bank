import sys
from collections import deque

n, k = map(int, sys.stdin.readline().split())
money = [0] + list(map(int, sys.stdin.readline().split())) + [0]
dp = [0] * (n + 1)
predecessors = [0] * (n + 1)
deque = deque()

deque.append(1)

for i in range(2, n + 1):
    while deque and deque[0] < i - k:
        deque.popleft()

    dp[i] = dp[deque[0]] + money[i]
    predecessors[i] = deque[0]

    while deque and dp[i] >= dp[deque[-1]]:
        deque.pop()
    deque.append(i)

print(dp[n])

i = n
result = []
while i != 0:
    result.append(i)
    i = predecessors[i]

print(len(result) - 1)
for q in range(len(result) - 1, -1, -1):
    print(result[q], end=" ")
