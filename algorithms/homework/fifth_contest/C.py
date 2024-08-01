import sys
import time

k = 561510899
mod = 10 ** 9 + 123


def calculate_pow(max_len):
    k_pow = [1] * (max_len + 1)
    for i in range(1, max_len + 1):
        k_pow[i] = k_pow[i - 1] * k % mod

    return k_pow


def calculate_pref_hash(string, len_string, k_pow):
    pref_hash = [0] * (len_string + 1)
    for i in range(0, len_string):
        pref_hash[i + 1] = (pref_hash[i] + ord(string[i]) * k_pow[i]) % mod

    return pref_hash


def calculate_hash(k_pow, pref_hash, pos, len_str, max_len=0):
    hash = pref_hash[pos + len_str] - pref_hash[pos]
    if hash < 0:
        hash += mod
    if max_len != 0:
        hash = hash * k_pow[max_len - (pos + len_str - 1)] % mod

    return hash


def binary_search(a, val):
    low = 0
    high = len(a) - 1
    mid = len(a) // 2

    while a[mid] != val and low <= high:
        if val > a[mid]:
            low = mid + 1
        else:
            high = mid - 1
        mid = (low + high) // 2

    if low > high:
        return False
    else:
        return True

string = sys.stdin.readline().strip()
substring = sys.stdin.readline().strip()

t1 = time.time()

len_string = len(string)
len_substring = len(substring)
max_len = max(len_string, len_substring * 2)

k_pow = calculate_pow(max_len)
pref_hash_str = calculate_pref_hash(string, len_string, k_pow)
pref_hash_substr = calculate_pref_hash(substring + substring, len_substring * 2, k_pow)

hashes = []
for i in range(len_substring):
    hashes.append(calculate_hash(k_pow, pref_hash_substr, i, len_substring, max_len))

hashes.sort()

answer = 0
i = 0
while i + len_substring <= len_string:
    answer += binary_search(hashes, calculate_hash(k_pow, pref_hash_str, i, len_substring, max_len))
    i += 1

print(answer)