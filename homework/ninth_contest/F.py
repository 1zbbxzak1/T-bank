import sys


class Door:
    def __init__(self, x, y, _id):
        self.x = x
        self.y = y
        self.id = _id


def closest_indices(arr, val):
    left = 0
    right = len(arr) - 1
    while left <= right:
        mid = (left + right) // 2
        if arr[mid] < val:
            left = mid + 1
        elif arr[mid] > val:
            right = mid - 1
        else:
            return mid
    return left


def get_sum(idx, fenwick_tree):
    s = 0
    while idx >= 0:
        s += fenwick_tree[idx]
        idx = (idx & (idx + 1)) - 1
    return s


def update_tree(idx, val, fenwick_tree):
    while idx < len(fenwick_tree):
        fenwick_tree[idx] += val
        idx |= idx + 1


def find_distances(doors):
    y_coords = sorted(set(door.y for door in doors))
    y_index = {y: i for i, y in enumerate(y_coords)}
    x_sorted_doors = sorted(doors, key=lambda door: door.x)

    dist = [0] * len(doors)
    fenwick_tree = [0] * len(y_coords)

    for door in x_sorted_doors:
        index = y_index[door.y]
        door_dist = door.x - get_sum(index, fenwick_tree)
        dist[door.id] = door_dist  # Assign to the correct index in dist list

        lower_bound = closest_indices(y_coords, door.y - door_dist)
        upper_bound = closest_indices(y_coords, door.y + door_dist)

        update_tree(lower_bound, 2 * door_dist, fenwick_tree)
        update_tree(upper_bound, -2 * door_dist, fenwick_tree)

    return dist



n = int(sys.stdin.readline().split()[2])
doors = []
for i in range(n):
    x, y = map(float, sys.stdin.readline().split())
    doors.append(Door(int(x * 2), int(y * 2), i))

distances = find_distances(doors)
for i, distance in enumerate(distances):
    sys.stdout.write(str(distance))
    if i != len(distances) - 1:
        sys.stdout.write(' ')
