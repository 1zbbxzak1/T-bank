import sys


def can_complete_in_time(time, n, comp):
    total_sub = 0
    for t, b, y in comp:
        cycles = time // (b * t + y)
        rem_time = time % (b * t + y)
        sub_full = cycles * b
        sub_rem = min(b, rem_time // t)
        total_sub += sub_full + sub_rem

        if total_sub >= n:
            return True
    return total_sub >= n


def min_time_to_complete(n, comp):
    left, right = 0, 10 ** 25
    best_t = right

    while left <= right:
        middle = (left + right) // 2
        if can_complete_in_time(middle, n, comp):
            best_t = middle
            right = middle - 1
        else:
            left = middle + 1

    return best_t


def distribute_submissions(n, time, comp):
    rem_sub = n

    for i, (t, b, y) in enumerate(comp):
        cycles = time // (b * t + y)
        rem_time = time % (b * t + y)
        sub_full = cycles * b
        sub_rem = min(b, rem_time // t)
        total_sub = sub_full + sub_rem

        sub = min(total_sub, rem_sub)
        rem_sub -= sub
        print(sub, end=" ")


N, M = map(int, sys.stdin.readline().strip().split())
computers = []

for _ in range(M):
    T, B, Y = map(int, sys.stdin.readline().strip().split())
    computers.append((T, B, Y))

best_time = min_time_to_complete(N, computers)
print(best_time)
distribute_submissions(N, best_time, computers)
