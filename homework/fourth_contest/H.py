import sys


def fill_segments(count_exercises):
    segments = []
    for num_ex in range(count_exercises):
        start, period = map(int, sys.stdin.readline().split())
        # Располагаю элементыв segemnt.append по следующему принципу:
        # 0) Элемент для сортировки, где находится точка на отрезке.
        # 1) Индикатор: 1 - начало отрезка, -1 - конец отрезка.
        # 2) Номер упражнения. Счет начинаем с 1.
        # 3) Tuple храним в точке информацию об отрезке, к которому она принадлежит(начало и конец).
        # То есть точка начало и точка конец знают, где находятся точки начала и конца отрезка, к кооторому они приналлежат
        segments.append((start, 1, str(num_ex + 1), (start, start + period)))
        segments.append((start + period, -1, str(num_ex + 1), (start, start + period)))

    return segments


def find_optimal_strategy(count_exercises):
    segments = fill_segments(count_exercises)
    segments.sort()

    cur_position = 0
    optimal_strategy = []
    for i in range(count_exercises * 2):
        item = segments[i]
        if item[1] == -1 and item[3][0] >= cur_position:
            optimal_strategy.append(item[2])
            cur_position = item[0]

    return optimal_strategy


count_exercises, weight_exercise = map(int, sys.stdin.readline().split())
optimal_strategy = find_optimal_strategy(count_exercises)

print(len(optimal_strategy) * weight_exercise)
print(len(optimal_strategy))
print(' '.join(optimal_strategy))