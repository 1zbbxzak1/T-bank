import sys

MAX_N = 10 ** 5 + 5
MOD = 10 ** 9 + 7
X = 3


def precompute_hashes(s):
    n = len(s)
    xk = [1] * MAX_N
    h = [0] * MAX_N

    for i in range(1, MAX_N):
        xk[i] = (xk[i - 1] * X * 52) % MOD

    for i in range(n):
        h[i + 1] = (xk[i] * ord(s[i]) + h[i]) % MOD

    return h, xk


def hash_substring(h, xk, left, right):
    return (h[right + 1] - h[left]) * xk[length - left] % MOD


line = sys.stdin.readline().strip()
m = int(sys.stdin.readline().strip())
length = len(line)
h, xk = precompute_hashes(line)

for i in range(m):
    a, b, c, d = map(int, sys.stdin.readline().split())
    a, b, c, d = a - 1, b - 1, c - 1, d - 1

    if b - a == d - c:
        if a <= b < length and c <= d < length:
            if hash_substring(h, xk, a, b) == hash_substring(h, xk, c, d):
                print('Yes')
            else:
                print('No')
        else:
            print('No')
    else:
        print('No')
