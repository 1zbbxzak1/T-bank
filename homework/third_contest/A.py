import sys

n = int(sys.stdin.readline())
tree = map(int, sys.stdin.readline().split())


class Node:
    def __init__(self, parent, val):
        self.value = val
        self.parent = parent


node_height = 1
count_node_prev_levels = 0
deep_node = ['0'] * n
map_tree = {0: Node(None, 0)}
max_diameter = 0
map_diameter_nodes = [0] * n


def fill_deep_level(ind_end_elm: int, ind_start_elm: int):
    for i in range(ind_end_elm - ind_start_elm):
        deep_node[ind_start_elm + 1 + i] = str(node_height)


for index, value in enumerate(tree):
    # Определяем высоту дерева
    if value > count_node_prev_levels:
        fill_deep_level(index, count_node_prev_levels)  # Заполняем глубину для предыдущего уровня
        count_node_prev_levels = index
        node_height += 1

    map_tree[index + 1] = Node(map_tree[value], index + 1)

# Заполняем глубину для последнего уровня
fill_deep_level(n - 1, count_node_prev_levels)

# Поиск диаметра дерева
for val_node in range(n - 1, 0, -1):
    val_parent = map_tree[val_node].parent.value
    max_diameter = max(max_diameter, map_diameter_nodes[val_node] + 1 + map_diameter_nodes[val_parent])
    map_diameter_nodes[val_parent] = max(map_diameter_nodes[val_parent], map_diameter_nodes[val_node] + 1)

print(node_height, max_diameter)
print(' '.join(deep_node))
