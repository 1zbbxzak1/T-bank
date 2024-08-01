def arithmetic_progression(a, k, n):
    return (2*a + k*(n - 1)) * n // 2


def horizontal_difference(y):
    top = arithmetic_progression(1, 1, m * (y - 1))
    total = arithmetic_progression(1, 1, m * n)
    bottom = total - top
    return top - bottom


def vertical_difference(x):
    left = n * arithmetic_progression(0, 1, x - 1) + (x - 1) * arithmetic_progression(1, m, n)
    total = arithmetic_progression(1, 1, m * n)
    right = total - left
    return left - right


def binary_search(left, right, position):
    mid = (left + right) // 2
    diff_mid = position(mid)
    if right - left <= 1:
        diff_left = position(left)
        diff_right = position(right)
        if abs(diff_left) > abs(diff_right):
            return diff_right, right
        else:
            return diff_left, left

    if diff_mid > 0:
        return binary_search(left, mid, position)
    else:
        return binary_search(mid, right, position)


def find_orientation(n, m):
    n, index_h = binary_search(1, n, horizontal_difference)
    m, index_v = binary_search(1, m, vertical_difference)

    if abs(n) < abs(m):
        print(f'H {str(int(index_h))}')
    else:
        print(f'V {str(int(index_v))}')


t = int(input())
for _ in range(t):
    n, m = map(int, input().split())
    find_orientation(n, m)
