n = int(input())
arr = list(range(1, n + 1))

for i in range(n):
    arr[i], arr[i // 2] = arr[i // 2], arr[i]

print(*arr)
