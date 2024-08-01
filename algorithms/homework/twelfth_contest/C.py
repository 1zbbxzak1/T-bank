import sys


def sieve_of_eratosthenes(limit):
    is_prime = [True] * (limit + 1)
    is_prime[0] = is_prime[1] = False
    p = 2
    while p * p <= limit:
        if is_prime[p] == True:
            for i in range(p * p, limit + 1, p):
                is_prime[i] = False
        p += 1
    primes = [p for p in range(limit + 1) if is_prime[p]]
    return primes


def find_goldbach_pair(n, primes):
    prime_set = set(primes)
    for p in primes:
        if (n - p) in prime_set:
            return p, n - p
    return None, None


n = int(sys.stdin.readline().strip())
primes = sieve_of_eratosthenes(n)
p, q = find_goldbach_pair(n, primes)
print(p, q)
