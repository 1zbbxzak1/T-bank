import sys


def last_nonzero_digit(n):
    result = 1
    for i in range(1, n + 1):
        curr = i
        while curr % 2 == 0:
            curr //= 2
        while curr % 5 == 0:
            curr //= 5
        result = (result * curr) % 10
    return result


def last_nonzero(n):
    if n == 0:
        return 1

    count_two = 0
    count_five = 0
    for i in range(1, n + 1):
        curr = i
        while curr % 2 == 0:
            count_two += 1
            curr //= 2
        curr = i
        while curr % 5 == 0:
            count_five += 1
            curr //= 5

    excess_two = count_two - count_five

    last_digit = last_nonzero_digit(n)

    for _ in range(excess_two):
        last_digit = (last_digit * 2) % 10

    return last_digit


N = int(sys.stdin.readline().strip())
print(last_nonzero(N))
