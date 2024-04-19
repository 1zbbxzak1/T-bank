import sys

n, k = map(int, sys.stdin.readline().split())
money = [0] * n
money[0] = 0
money[n - 1] = 0
money[1:n - 1] = map(int, sys.stdin.readline().split())

min_cost = [sys.maxsize] * n
predecessors = [0] * n
min_cost[0] = money[0]

max_val = (-sys.maxsize, -sys.maxsize)
pre_max_val = (-sys.maxsize, -sys.maxsize)
for i in range(1, n):

    if i == 1:
        max_val = (money[0], 0)
        last_min = 0
    else:
        arr = [(min_cost[i - 1], i - 1)]

        if i - k <= max_val[1] < i:
            arr.append(max_val)

        if i - k <= pre_max_val[1] < i:
            arr.append(pre_max_val)

        new_max_val = max(arr)

        arr.pop(arr.index(new_max_val))
        new_pre_max_val = max(arr)

        last_min = new_max_val[1]

        max_val = new_max_val
        pre_max_val = new_pre_max_val

    min_cost[i] = min_cost[last_min] + money[i]
    predecessors[i] = last_min

print(min_cost[-1])

current = n - 1
path = [current + 1]

while current != 0:
    current = predecessors[current]
    path.append(current + 1)

path.reverse()

print(len(path) - 1)
print(*path)
