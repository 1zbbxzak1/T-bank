import sys
sys.setrecursionlimit(10**9)


class Node:
    def __init__(self, val):
        self.value = val
        self.children = []


def height_diameter(node, depth):
    if not node:
        return (depth, 0)

    max_height = depth
    max_diameter = 0
    max_child_heights = [0, 0]

    for child in node.children:
        child_height, child_diameter = height_diameter(child, depth + 1)
        max_height = max(max_height, child_height)
        max_diameter = max(max_diameter, child_diameter)

        if child_height > max_child_heights[0]:
            max_child_heights[1] = max_child_heights[0]
            max_child_heights[0] = child_height
        elif child_height > max_child_heights[1]:
            max_child_heights[1] = child_height

    max_diameter = max(max_diameter, max_child_heights[0] + max_child_heights[1] - 2 * depth)

    return max_height, max_diameter


n = int(input())
parent_values = list(map(int, sys.stdin.readline().split()))

nodes = [Node(i) for i in range(n)]
root = nodes[0]

for i in range(1, n):
    parent = parent_values[i - 1]
    nodes[parent].children.append(nodes[i])

height, diameter = height_diameter(root, 0)

depths = [0] * n


def find_depths(node, depth):
    depths[node.value] = depth
    for child in node.children:
        find_depths(child, depth + 1)


find_depths(root, 0)

print(height, diameter)
print(" ".join(map(str, depths)))
