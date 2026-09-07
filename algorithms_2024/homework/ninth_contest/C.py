class Node:
    def __init__(self, left, right):
        self.number = 0
        self.segments = 0
        self.set = False
        self.up = False
        self.left = left
        self.right = right


def build(tree, idx, left, right):
    tree[idx] = Node(left, right)
    if left != right:
        tm = (left + right) // 2
        build(tree, idx * 2, left, tm)
        build(tree, idx * 2 + 1, tm + 1, right)


def push(tree, idx):
    if not tree[idx].up:
        return
    tree[idx].number = tree[idx].right - tree[idx].left + 1 if tree[idx].set else 0
    tree[idx].segments = 1 if tree[idx].set else 0
    tree[idx].up = False
    if tree[idx].left == tree[idx].right:
        return
    tree[idx * 2].set = tree[idx * 2 + 1].set = tree[idx].set
    tree[idx * 2].up = tree[idx * 2 + 1].up = True


def update(tree, idx, val, qleft, qright):
    if tree[idx].right < qleft or tree[idx].left > qright:
        return
    if tree[idx].right <= qright and tree[idx].left >= qleft:
        push(tree, idx)
        tree[idx].set = val
        tree[idx].up = True
        return
    push(tree, idx)
    update(tree, idx * 2, val, qleft, qright)
    update(tree, idx * 2 + 1, val, qleft, qright)
    current = idx * 2
    while True:
        push(tree, current)
        if tree[current].left == tree[current].right:
            break
        current = current * 2 + 1
    left = tree[current].number == 1
    current = idx * 2 + 1
    while True:
        push(tree, current)
        if tree[current].left == tree[current].right:
            break
        current = current * 2
    right = tree[current].number == 1
    tree[idx].number = tree[idx * 2].number + tree[idx * 2 + 1].number
    tree[idx].segments = tree[idx * 2].segments + tree[idx * 2 + 1].segments
    if left and right:
        tree[idx].segments -= 1


segment_tree = [Node(0, 0) for _ in range(1000100 * 4)]
build(segment_tree, 1, 0, 1000100)

n = int(input())

for _ in range(n):
    color, x, l = input().split()
    x, l = int(x), int(l)
    if l > 0:
        l -= 1
    else:
        l += 1
    update(segment_tree, 1, (color == 'B'), x + 500000, x + l + 500000)
    print(segment_tree[1].segments, segment_tree[1].number)
