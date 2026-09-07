import sys


def fill_segments(n):
    segments = []
    for num_request in range(n):
        left, right = map(int, sys.stdin.readline().split())
        segments.append((left, 1))
        segments.append((right, -1))

    return segments


def find_shaded_length(n):
    segments = fill_segments(n)
    segments.sort()

    cur_depth = 0
    shaded_length = 0
    far_left = 0
    for i in range(n * 2):
        if cur_depth == 0:
            far_left = segments[i][0]

        cur_depth += segments[i][1]
        if cur_depth == 0:
            shaded_length += segments[i][0] - far_left

    return shaded_length


n = int(sys.stdin.readline())
print(find_shaded_length(n))