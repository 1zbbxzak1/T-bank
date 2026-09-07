import sys


def find_value(n, k):
    left = 0
    right = n**2 + 1

    while right - left > 1:
        mid = (left + right) // 2

        idx = 1
        current_multiplier = n
        count = 0

        while idx <= n and current_multiplier > 0:
            if idx * current_multiplier < mid:
                count += current_multiplier
                idx += 1
            else:
                current_multiplier -= 1

        if count < k:
            left = mid
        else:
            right = mid

    return left


n, k = map(int, sys.stdin.readline().split())
print(find_value(n, k))
