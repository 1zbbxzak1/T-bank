n = int(input())
heights = list(map(int, input().split()))

total_sum = 0.0
for i in range(1, n):
    total_sum += (heights[i] + heights[i - 1]) / 2.0

result = total_sum / float(n - 1)
print(result)