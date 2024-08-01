import sys
from collections import deque


def largest_rectangle_area(list_height):
    max_area = 0
    list_height = [0] + list_height + [0]
    stack = deque([0])

    for i in range(1, len(list_height)):
        while list_height[i] < list_height[stack[-1]]:
            middle = stack.pop()
            width = i - stack[-1] - 1
            height = list_height[middle]
            max_area = max(max_area, width * height)
        stack.append(i)

    return max_area


n = int(sys.stdin.readline().strip())
heights = list(map(int, sys.stdin.readline().split()))
print(largest_rectangle_area(heights))
