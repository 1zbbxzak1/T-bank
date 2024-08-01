from collections import deque


def get_moves(x, y, N):
    moves = []
    possible_moves = [(x + 2, y + 1), (x + 2, y - 1), (x - 2, y + 1), (x - 2, y - 1),
                      (x + 1, y + 2), (x + 1, y - 2), (x - 1, y + 2), (x - 1, y - 2)]
    for move in possible_moves:
        if 1 <= move[0] <= N and 1 <= move[1] <= N:
            moves.append(move)
    return moves


def find_shortest_path(N, start, target):
    visited = set()
    queue = deque([(start, [start])])

    while queue:
        current, path = queue.popleft()
        if current == target:
            return len(path) - 1, path
        visited.add(current)
        for move in get_moves(*current, N):
            if move not in visited:
                queue.append((move, path + [move]))
    return -1, None


N = int(input())
x1, y1 = map(int, input().split())
x2, y2 = map(int, input().split())

steps, path = find_shortest_path(N, (x1, y1), (x2, y2))

print(steps)
for pos in path:
    print(*pos)
