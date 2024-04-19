import sys


def bfs():
    # Инициализация очереди с кортежем (координаты, состояние)
    queue = [(start_pos, "K")]
    distance = 0

    # Пока очередь не пуста
    while queue:
        distance += 1
        for _ in range(len(queue)):
            # Извлекаем первый элемент из очереди
            position, state = queue.pop(0)
            cur_i, cur_j = position

            # Перебираем возможные ходы из текущего состояния
            for di, dj in possible_moves[state]:
                i, j = cur_i + di, cur_j + dj
                # Проверяем выход за границы доски и посещали ли уже эту клетку
                if i < 0 or i >= board_size or j < 0 or j >= board_size or (i, j) in visited[state]:
                    continue
                # Если достигли конечной точки, возвращаем расстояние
                if (i, j) == end_pos:
                    return distance
                # Если на клетке есть препятствие, добавляем новое состояние в очередь
                elif board[i][j] not in (".", "S"):
                    queue.append(((i, j), board[i][j]))
                    visited[board[i][j]].add((i, j))
                # Иначе добавляем текущее состояние в очередь
                else:
                    queue.append(((i, j), state))
                    visited[state].add((i, j))

    return -1


# Чтение размера доски
board_size = int(sys.stdin.readline())
board = []
start_pos = (-1, -1)
end_pos = (-1, -1)

# Заполнение доски и поиск стартовой и конечной позиции
for i in range(board_size):
    row = sys.stdin.readline().strip()
    if "S" in row:
        start_pos = (i, row.index("S"))
    elif "F" in row:
        end_pos = (i, row.index("F"))
    board.append(row)

# Начальные значения для поиска
queue = [(start_pos, "K")]
possible_moves = {
    "K": {
        (-2, -1), (-1, -2), (1, -2), (2, -1),
        (2, 1), (1, 2), (-1, 2), (-2, 1)
    },
    "G": {
        (-1, -1), (0, -1), (1, -1), (1, 0),
        (1, 1), (0, 1), (-1, 1), (-1, 0)
    }

}
visited = {
    "K": {start_pos},
    "G": set()
}

# Вывод результата
print(bfs())
