import sys

calendar = [0] * 29


def get_num_day(day):
    if day == 'MON':
        return 1
    elif day == 'TUE':
        return 2
    elif day == 'WED':
        return 3
    elif day == 'THU':
        return 4
    elif day == 'FRI':
        return 5
    elif day == 'SAT':
        return 6
    elif day == 'SUN':
        return 7
    else:
        raise Exception()


for num_week in range(4):
    days_week = sys.stdin.readline().split()
    for day in days_week:
        num_day = get_num_day(day)
        date = 7 * num_week + num_day
        calendar[date] = 1

best_start_day = 0
best_end_day = 0
start_day = -1
period = 0
for cur_day in range(1, 29):
    if calendar[cur_day] == 0:
        period += 1
        if start_day == -1:
            start_day = cur_day
        if cur_day == 28 and period > best_end_day - best_start_day + 1:
            best_start_day = start_day
            best_end_day = cur_day
    else:
        if period > best_end_day - best_start_day + 1:
            best_start_day = start_day
            best_end_day = cur_day - 1
        period = 0
        start_day = -1

print(best_start_day, best_end_day)


import sys

calendar = [0] * 29


def get_num_day(day):
    days = {'MON': 1, 'TUE': 2, 'WED': 3, 'THU': 4, 'FRI': 5, 'SAT': 6, 'SUN': 7}
    return days[day]


for num_week in range(4):
    days_week = sys.stdin.readline().strip().split()
    for day in days_week:
        if day:
            num_day = get_num_day(day)
            date = 7 * num_week + num_day
            calendar[date] = 1

best_start_day = -1
best_end_day = -1
start_day = -1
period = 0

for cur_day in range(1, 29):
    if calendar[cur_day] == 0:
        period += 1
        if start_day == -1:
            start_day = cur_day
        if cur_day == 28 and period > best_end_day - best_start_day + 1:
            best_start_day = start_day
            best_end_day = cur_day
    else:
        if period > best_end_day - best_start_day + 1:
            best_start_day = start_day
            best_end_day = cur_day - 1
        period = 0
        start_day = -1

if best_start_day == -1:
    best_start_day = 0
    best_end_day = 0

print(best_start_day, best_end_day)
