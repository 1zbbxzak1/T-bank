import sys
from collections import deque


def bfs(x, y, n, m, grid, visited):
    deq = deque([(x, y)])
    ship_cells = []
    while deq:
        cx, cy = deq.popleft()
        if (cx, cy) in visited:
            continue
        visited.add((cx, cy))
        ship_cells.append((cx, cy))
        for dx, dy in [(-1, 0), (1, 0), (0, -1), (0, 1)]:
            nx, ny = cx + dx, cy + dy
            if 0 <= nx < n and 0 <= ny < m and (nx, ny) not in visited:
                if grid[nx][ny] in {'#', 'X'}:
                    deq.append((nx, ny))
    return ship_cells


def count_ships(n, m, grid):
    visited = set()
    intact = 0
    damaged = 0
    destroyed = 0

    for i in range(n):
        for j in range(m):
            if (i, j) not in visited and grid[i][j] in {'#', 'X'}:
                ship_cells = bfs(i, j, n, m, grid, visited)
                if all(grid[x][y] == '#' for x, y in ship_cells):
                    intact += 1
                elif all(grid[x][y] == 'X' for x, y in ship_cells):
                    destroyed += 1
                else:
                    damaged += 1

    return intact, damaged, destroyed


N, M = map(int, sys.stdin.readline().split())
grid = [list(sys.stdin.readline().strip()) for _ in range(N)]

intact_count, damaged_count, destroyed_count = count_ships(N, M, grid)

print(intact_count, damaged_count, destroyed_count)
