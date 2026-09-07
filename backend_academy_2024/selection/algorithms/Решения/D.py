import sys

# Читаем количество позиций и количество путей
num_positions, num_paths = map(int, sys.stdin.readline().split())

# Инициализируем таблицу dp
dp = [[] for _ in range(num_paths)]
for i in range(num_paths):
    dp[i] = [sys.maxsize] * num_positions

# Заполняем таблицу dp для каждого пути
for path_index in range(num_paths):
    start, end = map(int, sys.stdin.readline().split())
    for pos in range(start-1, end):
        dp[path_index][pos] = 0
    cost = 0
    for pos in range(start-2, -1, -1):
        cost += 1
        dp[path_index][pos] = cost
    cost = 0
    for pos in range(end, num_positions):
        cost += 1
        dp[path_index][pos] = cost

# Находим минимальную стоимость
min_total_cost = sys.maxsize
for position in range(num_positions):
    total_cost = 0
    for path_index in range(num_paths):
        total_cost += dp[path_index][position]
    min_total_cost = min(min_total_cost, total_cost)

print(min_total_cost)
