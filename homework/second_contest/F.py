import sys
from collections import deque

queue = deque()
dictionary = {}
positions = 0

n = int(sys.stdin.readline())

for i in range(n):
    operation, *args = map(int, sys.stdin.readline().split())

    if operation == 1:
        id = args[0]
        dictionary[id] = len(queue) + positions
        queue.append(id)

    elif operation[0] == 2:
        queue.popleft()
        positions += 1

    elif operation[0] == 3:
        queue.pop()

    elif operation[0] == 4:
        q = args[0]
        print(dictionary[q] - positions)

    else:
        print(queue[0])
