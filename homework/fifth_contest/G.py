import sys
import time


def calculate_hash(hash_sum, num):
    mod_1 = 2 ** 64
    mod_2 = 10 ** 9 + 7

    hash_sum = ((hash_sum[0] + (num * 13 * 23) % mod_1) % mod_1,
                (hash_sum[1] * (num * 11 * 31) % mod_2) % mod_2)

    return hash_sum


def find_max_common_subarray_length(n, a, m, b):
    max_length = 0
    hash_a = set()

    for i in range(n):
        current_hash = (0, 1)
        for j in range(i, n):
            current_hash = calculate_hash(current_hash, a[j])
            hash_a.add(current_hash)

    for i in range(m):
        current_hash = (0, 1)
        for j in range(i, m):
            current_hash = calculate_hash(current_hash, b[j])
            if current_hash in hash_a:
                max_length = max(max_length, j - i + 1)

    return max_length


n = int(sys.stdin.readline().strip())
a = list(map(int, sys.stdin.readline().split()))

m = int(sys.stdin.readline().strip())
b = list(map(int, sys.stdin.readline().split()))

print(find_max_common_subarray_length(n, a, m, b))