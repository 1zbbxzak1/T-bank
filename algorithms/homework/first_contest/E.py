def formula(a, b, c, d, x):
    return a * (x ** 3) + b * (x ** 2) + c * x + d


def find_answer(a, b, c, d):
    eps = 1e-4

    high = 1
    while formula(a, b, c, d, high) * formula(a, b, c, d, -high) >= 0:
        high *= 2
    low = -high

    f = formula(a, b, c, d, low)

    while high - low > eps:
        mid = (low + high) / 2
        temp = formula(a, b, c, d, mid)

        if temp == 0:
            return mid

        if f * temp < 0:
            high = mid
        else:
            low = mid
            f = temp

    return (low + high) / 2


a, b, c, d = map(float, input().split())
print(find_answer(a, b, c, d))
