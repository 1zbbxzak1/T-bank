def formula(x):
    return x ** 2 + (x + 1) ** 0.5


def binary_search(number):
    eps = 10 ** (-6)
    low = -1
    high = number
    mid = (low + high) / 2

    while abs(formula(mid) - number) > eps:
        if formula(mid) >= number:
            high = mid
        else:
            low = mid
        mid = (low + high) / 2

    print(mid)


n = float(input())
binary_search(n)
