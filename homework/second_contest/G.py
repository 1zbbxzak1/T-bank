import sys
from collections import deque

left_queue = deque()
right_queue = deque()
n = int(sys.stdin.readline())

for i in range(n):
    request, *args = map(str, sys.stdin.readline().split())

    if len(left_queue) > len(right_queue):
        right_queue.appendleft(left_queue.pop())

    if request == "+":
        goblin = args[0]
        left_queue.appendleft(goblin)

    elif request == "*":
        goblin = args[0]
        left_queue.append(goblin)

    elif request == "-":
        print(right_queue.pop())
