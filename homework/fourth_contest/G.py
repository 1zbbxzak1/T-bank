import sys


def calculate_time(st_dt, end_dt):
    return end_dt[0] * 60 * 60 + end_dt[1] * 60 + end_dt[2] - st_dt[0] * 60 * 60 - st_dt[1] * 60 - st_dt[2]


def fill_segments(n):
    segments = []
    for num_request in range(n):
        (start_hour, start_minute, start_second,
         end_hour, end_minute, end_second) = map(int, sys.stdin.readline().split())

        diff = calculate_time((start_hour, start_minute, start_second), (end_hour, end_minute, end_second))

        if diff == 0:
            segments.append(((0, 0, 0), 1))
        elif diff < 0:
            segments.append(((0, 0, 0), 1))
            segments.append(((start_hour, start_minute, start_second), 1))
            segments.append(((end_hour, end_minute, end_second), -1))
        else:
            segments.append(((start_hour, start_minute, start_second), 1))
            segments.append(((end_hour, end_minute, end_second), -1))

    return segments


def find_common_work_time(count_checkout):
    segments = fill_segments(count_checkout)
    segments.sort()

    common_total_work_time = 0
    cur_depth = 0
    start_work_all_checkout = 0
    len_segments = len(segments)
    for num_date in range(len_segments):
        cur_depth += segments[num_date][1]
        if cur_depth == count_checkout:
            start_work_all_checkout = segments[num_date][0]

        if start_work_all_checkout != 0 and cur_depth < count_checkout:
            common_total_work_time += calculate_time(start_work_all_checkout, segments[num_date][0])
            start_work_all_checkout = 0

    if cur_depth == count_checkout:
        common_total_work_time += calculate_time(start_work_all_checkout, (24, 0, 0))

    return common_total_work_time


n = int(sys.stdin.readline())
print(find_common_work_time(n))