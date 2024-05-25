import sys
import math


def prime_factors(n):
    factors = []
    count = 0

    while n % 2 == 0:
        n //= 2
        count += 1
    if count > 0:
        factors.append((2, count))

    for i in range(3, int(math.sqrt(n)) + 1, 2):
        count = 0
        while n % i == 0:
            n //= i
            count += 1
        if count > 0:
            factors.append((i, count))

    if n > 2:
        factors.append((n, 1))

    return factors


def format_factors(factors):
    return '*'.join(f"{base}^{exp}" if exp > 1 else str(base) for base, exp in factors)


N = int(sys.stdin.readline().strip())
factors = prime_factors(N)
result = format_factors(factors)
print(result)