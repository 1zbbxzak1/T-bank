import sys


def preprocess(arr):
    prefix_sum = [0] * len(arr)
    prefix_xor = [0] * len(arr)
    prefix_sum[0] = arr[0]
    prefix_xor[0] = arr[0]

    for i in range(1, len(arr)):
        prefix_sum[i] = prefix_sum[i - 1] + arr[i]
        prefix_xor[i] = prefix_xor[i - 1] ^ arr[i]

    return prefix_sum, prefix_xor


def sum_segment(prefix_sum, l, r):
    if l == 0:
        return prefix_sum[r]
    else:
        return prefix_sum[r] - prefix_sum[l - 1]


def xor_segment(prefix_xor, l, r):
    if l == 0:
        return prefix_xor[r]
    else:
        return prefix_xor[r] ^ prefix_xor[l - 1]


n = int(sys.stdin.readline())
arr = [0] + list(map(int, sys.stdin.readline().split()))
prefix_sum, prefix_xor = preprocess(arr)

m = int(sys.stdin.readline())
queries = []
for _ in range(m):
    queries.append(list(map(int, sys.stdin.readline().split())))

for query in queries:
    if query[0] == 1:
        print(sum_segment(prefix_sum, query[1], query[2]))
    elif query[0] == 2:
        print(xor_segment(prefix_xor, query[1], query[2]))

# import random
#
# n = 5 * 10**5
# arr = [random.randint(1, 1000) for _ in range(n)]
#
# m = 5 * 10**5
# test_cases = []
# for _ in range(m):
#     l = random.randint(1, n)
#     r = random.randint(l, n)
#     query_type = random.randint(1, 2)
#     test_cases.append((query_type, l, r))
#
# t1 = time.time()
# print("Тесты для sum_segment:")
# for i, (query_type, l, r) in enumerate(test_cases):
#     result = sum(arr[l:r + 1])
#     print(f"Тест {i + 1}: Ожидаемый результат = {result}, Полученный результат = {sum_segment(arr, l, r)}")
# t2 = time.time()
#
# t3 = time.time()
# print("\nТесты для xor_segment:")
# for i, (query_type, l, r) in enumerate(test_cases):
#     result = arr[l]
#     for j in range(l + 1, r + 1):
#         result ^= arr[j]
#     print(f"Тест {i + 1}: Ожидаемый результат = {result}, Полученный результат = {xor_segment(arr, l, r)}")
#
# t4 = time.time()
#
# print(t2 - t1)
# print(t4 - t3)
