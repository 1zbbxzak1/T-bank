import sys

MOD = 10 ** 9 + 7
X = 31


def precompute_hashes(p, t):
    length_p = len(p)
    length_t = len(t)
    max_length = max(length_p, length_t) + 1
    xk = [1] * max_length
    hp = [ord(p[0])] * length_p
    ht = [ord(t[0])] * length_t

    for i in range(1, max_length):
        xk[i] = (xk[i - 1] * X) % MOD

    for j in range(1, length_p):
        hp[j] = (xk[j] * ord(p[j]) + hp[j - 1]) % MOD

    for k in range(1, length_t):
        ht[k] = (xk[k] * ord(t[k]) + ht[k - 1]) % MOD

    return xk, hp, ht


def hash_substring(h, left, right):
    ans = h[right]
    if left > 0:
        ans -= h[left - 1]

    return ans % MOD


def get_count_and_index_occurrences(p, t):
    count = 0
    result = []
    length_p = len(p)
    length_t = len(t)

    xk, hp, ht = precompute_hashes(p, t)

    for i in range(length_t - length_p + 1):
        idx = 0
        while t[i + idx] == p[idx]:
            if (idx == 0 and length_p == 1) or (idx == length_p - 2):
                result.append(str(i + 1))
                count += 1
                break
            idx += 1

        else:
            l_idx = i
            i += idx

            hash_p = hash_substring(hp, idx + 1, length_p - 1) * xk[i + 1] % MOD
            hash_t = hash_substring(ht, i + 1, i + length_p - idx - 1) * xk[idx + 1] % MOD
            if hash_p == hash_t:
                result.append(str(l_idx + 1))
                count += 1

    return count, result


p = sys.stdin.readline().strip()
t = sys.stdin.readline().strip()

count, result = get_count_and_index_occurrences(p, t)

print(count)
print(' '.join(result))
