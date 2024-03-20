class Node:
    def __init__(self, parent, value):
        self.parent = parent
        self.val = value


def find_first_common_node(node_a, node_b):
    visited_a = set()
    visited_b = set()

    while node_a or node_b:
        if node_a:
            if node_a.val in visited_b:
                return node_a.val
            visited_a.add(node_a.val)
            node_a = node_a.parent
        if node_b:
            if node_b.val in visited_a:
                return node_b.val
            visited_b.add(node_b.val)
            node_b = node_b.parent

    return 0


n = int(input())
list_injections = list(map(int, input().split()))
nodes = [None] * (n + 1)

for i, val in enumerate(list_injections):
    parent = nodes[val]
    nodes[i + 1] = Node(parent, i + 1)

count_requests = int(input())

for _ in range(count_requests):
    a, b = map(int, input().split())
    response = find_first_common_node(nodes[a], nodes[b])
    print(response)
