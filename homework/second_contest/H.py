import sys
from collections import deque

n = int(sys.stdin.readline())
arr = list(map(int, sys.stdin.readline().split()))

# Стек рекордов
prev_left = [-1] * n
prev_right = [-1] * n

stack = deque()
for i in range(n):
    while stack and arr[stack[-1]] >= arr[i]:
        stack.pop()
    if stack:
        prev_left[i] = stack[-1]
    stack.append(i)

stack = deque()
for i in range(n - 1, -1, -1):
    while stack and arr[stack[-1]] >= arr[i]:
        stack.pop()
    if stack:
        prev_right[i] = stack[-1]
    stack.append(i)

# Предварительное вычисление суммы подмассивов
sum_arr = [0] * (n + 1)
for i in range(n):
    sum_arr[i + 1] = sum_arr[i] + arr[i]

# Находим максимальную сумму
max_sum = 0
for i in range(n):
    if prev_left[i] != -1:
        left = prev_left[i] + 1
    else:
        left = 0

    if prev_right[i] != -1:
        right = prev_right[i] - 1
    else:
        right = n - 1

    max_sum = max(max_sum, arr[i] * (sum_arr[right + 1] - sum_arr[left]))

print(max_sum)