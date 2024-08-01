import random
import sys
import time


class TreeNode:
    def __init__(self, key):
        self.key = key
        self.prior = random.random()
        self.left = None
        self.right = None
        self.parent = None


class Treap:
    def __init__(self):
        self.head = None
        self.nodes = {}

    def next(self, element):
        cur_node = self.head
        prev_result = None
        while cur_node:
            if cur_node.key >= element:
                prev_result = cur_node.key
                cur_node = cur_node.left
            else:
                cur_node = cur_node.right
        return prev_result if prev_result is not None else -1

    def insert(self, key):
        new_node = TreeNode(key)
        if not self.head:
            self.head = new_node
            return

        # Вставка как в обычное бинарное дерево поиска
        current = self.head
        while current:
            if key < current.key:
                if not current.left:
                    current.left = new_node
                    new_node.parent = current
                    break
                current = current.left
            else:
                if not current.right:
                    current.right = new_node
                    new_node.parent = current
                    break
                current = current.right

        # Выполнение ребалансировки (по приоритетам)
        self._rebalance(new_node)

    def _rebalance(self, node):
        while node.parent and node.parent.prior < node.prior:
            if node.parent.left == node:
                self._rotate_right(node.parent)
            else:
                self._rotate_left(node.parent)

    def _rotate_left(self, node):
        right_child = node.right
        node.right = right_child.left
        if right_child.left:
            right_child.left.parent = node
        right_child.parent = node.parent
        if not node.parent:
            self.head = right_child
        elif node == node.parent.left:
            node.parent.left = right_child
        else:
            node.parent.right = right_child
        right_child.left = node
        node.parent = right_child

    def _rotate_right(self, node):
        left_child = node.left
        node.left = left_child.right
        if left_child.right:
            left_child.right.parent = node
        left_child.parent = node.parent
        if not node.parent:
            self.head = left_child
        elif node == node.parent.left:
            node.parent.left = left_child
        else:
            node.parent.right = left_child
        left_child.right = node
        node.parent = left_child


tree = Treap()
prev_result = [None, None]
result = []

n = int(sys.stdin.readline())

for _ in range(n):
    request, element = map(str, sys.stdin.readline().split())
    if request == '+':
        if prev_result[0] == '?':
            tree.add((int(element) + prev_result[-1]) % 1000000000)
        else:
            tree.add(int(element))

        prev_result = ['+', 0]

    elif request == '?':
        res = tree.next(int(element))
        prev_result = ['?', res]
        result.append(res)

sys.stdout.write('\n'.join(map(str, result)) + '\n')



# tree = Treap()
# prev_result = [None, None]
# result = []
# n = 300000
#
# # Создаем тестовые данные
# test_input = []
# for _ in range(n):
#     if random.random() < 0.5:  # Случайным образом выбираем операцию добавления или поиска
#         operation = "+"
#         value = str(random.randint(0, 10 ** 9))  # Случайное число для добавления
#     else:
#         operation = "?"
#         value = str(random.randint(0, 10 ** 9))  # Случайное число для поиска
#     test_input.append(operation + " " + value)
#
#
# t1 = time.time()
# # Выполняем тест
# for line in test_input:
#     req, *element = line.split()
#
#     if req == '+':
#         if prev_result[0] == '?':
#             tree.add((int(element[0]) + prev_result[-1]) % 1000000000)
#         else:
#             tree.add(int(element[0]))
#
#         prev_result = ['+', 0]
#
#     elif req == '?':
#         res = tree.next(int(element[0]))
#         prev_result = ['?', res]
#         result.append(res)
#
# sys.stdout.write('\n'.join(map(str, result)) + '\n')
#
# t2 = time.time()
# print(t2 - t1)
#
