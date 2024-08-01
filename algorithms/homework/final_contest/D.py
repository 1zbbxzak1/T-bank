import sys

MOD = 10 ** 9 + 9


def eratosphen(n):
    primes = [True] * n
    primes[0] = primes[1] = False

    for cur in range(2, n):
        if primes[cur]:
            for multiple in range(cur * cur, n, cur):
                primes[multiple] = False

    return primes


def count_possible_passwords(n):
    primes_eratosphen = eratosphen(1000)
    primes = [i for i, is_prime in enumerate(primes_eratosphen) if is_prime and i >= 100]

    tr = {}
    for p in primes:
        s = str(p)
        pref = s[:2]
        if pref not in tr:
            tr[pref] = []
        tr[pref].append(s)

    prev_dp = {}
    for p in primes:
        prev_dp[str(p)] = 1

    for length in range(4, n + 1):
        dp = {}
        for pref in prev_dp:
            if pref[1:] in tr:
                for next_triplet in tr[pref[1:]]:
                    new_pref = next_triplet
                    dp[new_pref] = (dp.get(new_pref, 0) + prev_dp[pref]) % MOD
        prev_dp = dp

    result = sum(prev_dp.values()) % MOD
    return result


n = int(sys.stdin.readline().strip())
print(count_possible_passwords(n))
