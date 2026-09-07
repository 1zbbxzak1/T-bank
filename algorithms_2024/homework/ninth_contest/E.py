import sys


class SegmentTree:
    def __init__(self, dist):
        self.n = len(dist)
        self.min_v = [0] * (4 * self.n)
        self.min_len = [0] * (4 * self.n)
        self.add = [0] * (4 * self.n)
        self.build(1, 0, self.n, dist)

    def build(self, idx, left, right, dist):
        if left + 1 == right:
            self.min_len[idx] = dist[left]
        else:
            mid = (left + right) // 2
            self.build(2 * idx, left, mid, dist)
            self.build(2 * idx + 1, mid, right, dist)
            self.min_len[idx] = self.min_len[2 * idx] + self.min_len[2 * idx + 1]

    def push(self, idx):
        self.add[2 * idx] += self.add[idx]
        self.min_v[2 * idx] += self.add[idx]
        self.add[2 * idx + 1] += self.add[idx]
        self.min_v[2 * idx + 1] += self.add[idx]
        self.add[idx] = 0

    def update(self, idx, left, right, lx, rx, val):
        if right <= lx or rx <= left:
            return
        if lx <= left and right <= rx:
            self.add[idx] += val
            self.min_v[idx] += val
            return

        self.push(idx)
        mid = (left + right) // 2
        self.update(2 * idx, left, mid, lx, rx, val)
        self.update(2 * idx + 1, mid, right, lx, rx, val)

        if self.min_v[2 * idx] == self.min_v[2 * idx + 1]:
            self.min_v[idx] = self.min_v[2 * idx]
            self.min_len[idx] = self.min_len[2 * idx] + self.min_len[2 * idx + 1]
        elif self.min_v[2 * idx] < self.min_v[2 * idx + 1]:
            self.min_v[idx] = self.min_v[2 * idx]
            self.min_len[idx] = self.min_len[2 * idx]
        else:
            self.min_v[idx] = self.min_v[2 * idx + 1]
            self.min_len[idx] = self.min_len[2 * idx + 1]

    def update_range(self, lx, rx, val):
        self.update(1, 0, self.n, lx, rx, val)

    def query(self):
        return 0 if self.min_v[1] else self.min_len[1]


class Event:
    def __init__(self, x, y1, y2, event_type):
        self.x = x
        self.y1 = y1
        self.y2 = y2
        self.type = event_type


n = int(sys.stdin.readline().strip())

events = []
mat = set()

for _ in range(n):
    x1, y1, x2, y2 = map(int, sys.stdin.readline().split())
    if x1 > x2 or y1 > y2:
        x1, x2 = x2, x1
        y1, y2 = y2, y1
    x2 += 1
    y2 += 1
    mat.add(y1)
    mat.add(y2)
    events.append(Event(x1, y1, y2, 1))
    events.append(Event(x2, y1, y2, -1))

events.sort(key=lambda e: (e.x, -e.type))

sorted_mat = sorted(mat)
y_idx = {y: i for i, y in enumerate(sorted_mat)}

dist = [sorted_mat[i + 1] - sorted_mat[i] for i in range(len(sorted_mat) - 1)]
get_sum = sum(dist)

seg_tree = SegmentTree(dist)

result = 0
previous_x = events[0].x
for event in events:
    result += (get_sum - seg_tree.query()) * (event.x - previous_x)
    seg_tree.update_range(y_idx[event.y1], y_idx[event.y2], event.type)
    previous_x = event.x

print(result)
