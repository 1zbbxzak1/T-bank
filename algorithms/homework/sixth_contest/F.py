import sys
from collections import deque


def read_reactions():
    num_reactions = int(sys.stdin.readline().strip())
    reactions = {}

    for _ in range(num_reactions):
        reactant, _, product = input().partition(" -> ")
        if reactant not in reactions:
            reactions[reactant] = [product]
        else:
            reactions[reactant].append(product)

    return reactions


def find_shortest_path(reactions, from_position, to_position):
    path_lengths = {from_position: 0}
    queue = deque([from_position])

    while queue:
        current = queue.popleft()

        if current == to_position:
            return path_lengths[current]

        for next_move in reactions.get(current, []):
            if next_move not in path_lengths:
                path_lengths[next_move] = path_lengths[current] + 1
                queue.append(next_move)

    return -1


reactions = read_reactions()
from_position = sys.stdin.readline().strip()
to_position = sys.stdin.readline().strip()

shortest_path_length = find_shortest_path(reactions, from_position, to_position)

if shortest_path_length != -1:
    print(shortest_path_length)
else:
    print(-1)
