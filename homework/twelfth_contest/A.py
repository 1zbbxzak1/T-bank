import sys


def gcd(a, b):
    while b:
        a, b = b, a % b
    return a


N, K = map(int, sys.stdin.readline().split())
result = (N // gcd(N, K)) * K
print(result)
