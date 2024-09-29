import sys
from collections import deque, defaultdict


def solve(num_soldiers, num_comparisons, pairs):
    adjacency_list = defaultdict(list)
    in_degree_counts = [0] * (num_soldiers + 1)

    for A, B in pairs:
        if A != B:
            adjacency_list[A].append(B)
            in_degree_counts[B] += 1

    deq = deque()
    for i in range(1, num_soldiers + 1):
        if in_degree_counts[i] == 0:
            deq.append(i)

    sorted_order = []

    while deq:
        current_node = deq.popleft()
        sorted_order.append(current_node)

        for adjacent_node in adjacency_list[current_node]:
            in_degree_counts[adjacent_node] -= 1
            if in_degree_counts[adjacent_node] == 0:
                deq.append(adjacent_node)

    if len(sorted_order) != num_soldiers:
        print("No")
    else:
        print("Yes")
        print(" ".join(map(str, sorted_order)))


num_soldiers, num_comparisons = map(int, sys.stdin.readline().split())
pairs = []
for i in range(num_comparisons):
    a, b = map(int, sys.stdin.readline().split())
    pairs.append((a, b))

solve(num_soldiers, num_comparisons, pairs)