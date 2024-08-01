import sys


class Segment:
    def __init__(self, y=0, x1=0, x2=0, SegmentNum=0, type=False):
        self.y = y
        self.x1 = x1
        self.x2 = x2
        self.SegmentNum = SegmentNum
        self.type = type


def cmp(a, b):
    return (a.y == b.y and not a.type and b.type) or a.y < b.y


class Node:
    def __init__(self, maxx=0, pos=-1, mem=0):
        self.maxx = maxx
        self.pos = pos
        self.mem = mem

    def __add__(self, other):
        return Node(
            max(self.maxx, other.maxx),
            self.pos if self.maxx > other.maxx else other.pos
        )


def push(tree, v):
    tree[v].maxx += tree[v].mem
    if v < SIZE:
        tree[v * 2].mem += tree[v].mem
        tree[v * 2 + 1].mem += tree[v].mem
    tree[v].mem = 0


def addTree(tree, v, l, r, a, b, c):
    push(tree, v)

    if a > r or l > b:
        return

    if a <= l and r <= b:
        tree[v].mem += -1 if c else 1
        push(tree, v)
        return

    m = (l + r) // 2
    addTree(tree, v * 2, l, m, a, b, c)
    addTree(tree, v * 2 + 1, m + 1, r, a, b, c)
    tree[v] = tree[v * 2] + tree[v * 2 + 1]


# Main function
def main():
    global SIZE
    SIZE = 1

    while SIZE <= MAX_SIZE:
        SIZE <<= 1

    # Initialize segment tree
    tree = [Node() for _ in range(4 * MAX_SIZE)]
    for i in range(SIZE, len(tree)):
        tree[i].pos = i - SIZE + 1
        tree[i].maxx = 0
    for i in range(SIZE - 1, 0, -1):
        tree[i] = tree[i * 2] + tree[i * 2 + 1]

    n = int(input())
    border = [Segment() for _ in range(10 ** 5 + 9)]

    # Read input and preprocess segments
    for i in range(0, 2 * n, 2):
        x1, y1, x2, y2 = map(int, input().split())
        x1 += ADDITIONAL
        y1 += ADDITIONAL
        x2 += ADDITIONAL
        y2 += ADDITIONAL

        border[i] = Segment(y2, x1, x2, i, True)
        border[i + 1] = Segment(y1, x1, x2, i, False)

    # Sort segments based on y-coordinate
    border.sort(key=lambda x: (x.y, not x.type))

    res = -1
    resX = -1
    resY = -1

    # Process segments and update segment tree
    for i in range(2 * n):
        addTree(tree, 1, 1, SIZE, border[i].x1, border[i].x2, border[i].type)

        if tree[1].maxx > res:
            res = tree[1].maxx
            resX = tree[1].pos - ADDITIONAL
            resY = border[i].y - ADDITIONAL

    # Output result
    print(res)
    print(resX, resY)


# Constants and global variables
MAX_SIZE = 4 * 10 ** 5
ADDITIONAL = 2 * 10 ** 5
SIZE = 0

# Call main function
if __name__ == "__main__":
    main()
