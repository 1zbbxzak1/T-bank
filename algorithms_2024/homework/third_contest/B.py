import sys
sys.setrecursionlimit(10**9)


class Node:
    def __init__(self, left, right):
        self.left = left
        self.right = right


n, root = map(int, sys.stdin.readline().split())
map_tree = {-1: Node(-1, -1)}
map_height_node = [0] * n
success = True


# Находим высоту каждого node от корня
def calculate_height_node(val_node) -> int:
    global success
    node = map_tree[val_node]
    if node.left == -1 and node.right == -1:
        return 0
    height_left_node = calculate_height_node(node.left)
    height_right_node = calculate_height_node(node.right)
    if abs(height_left_node - height_right_node) > 1:
        success = False
    map_height_node[val_node] = max(height_left_node, height_right_node) + 1
    return map_height_node[val_node]


# Создаем граф
for val_node in range(n):
    left, right = map(int, sys.stdin.readline().split())
    if left != -1 and left > val_node or right != -1 and right < val_node:
        success = False
        continue
    map_tree[val_node] = Node(left, right)

# Если граф создан успешно, проверяем высоту всех node
if success:
    calculate_height_node(root)

print(int(success))