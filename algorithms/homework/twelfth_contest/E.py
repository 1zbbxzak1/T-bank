import sys

MOD = 10 ** 9 + 7


def mod_i(a, p):
    return pow(a, p - 2, p)


def compute_factorials_and_inverses(n, mod):
    fact = [1] * (n + 1)
    i_fact = [1] * (n + 1)

    for i in range(2, n + 1):
        fact[i] = fact[i - 1] * i % mod

    i_fact[n] = mod_i(fact[n], mod)
    for i in range(n - 1, 0, -1):
        i_fact[i] = i_fact[i + 1] * (i + 1) % mod

    return fact, i_fact


def binomial_coefficient(n, k, mod):
    if k > n or k < 0:
        return 0
    fact, i_fact = compute_factorials_and_inverses(n, mod)
    return fact[n] * i_fact[k] % mod * i_fact[n - k] % mod


n, k = map(int, sys.stdin.readline().split())
print(binomial_coefficient(n, k, MOD))
