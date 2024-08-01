import sys


def mod_exp(base, exp, mod):
    result = 1
    while exp > 0:
        if exp % 2 == 1:
            result = (result * base) % mod
        base = (base * base) % mod
        exp //= 2
    return result


def mod_inv(a, mod):
    return mod_exp(a, mod - 2, mod)


N, M, K, MOD = map(int, sys.stdin.readline().split())

nm = mod_exp(M, N, MOD)
k = mod_inv(K, MOD)

answer = (nm * k) % MOD
print(answer)
