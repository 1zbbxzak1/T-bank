def sift_up(idx):
    while idx > 0 and heap[idx] > heap[(idx - 1) // 2]:
        heap[idx], heap[(idx - 1) // 2] = heap[(idx - 1) // 2], heap[idx]
        idx = (idx - 1) // 2


def sift_down(idx):
    while 2 * idx + 1 < len(heap):
        left = 2 * idx + 1
        right = 2 * idx + 2
        j = left
        if right < len(heap) and heap[right] > heap[left]:
            j = right
        if heap[idx] >= heap[j]:
            break
        heap[idx], heap[j] = heap[j], heap[idx]
        idx = j


def insert(element):
    heap.append(element)
    sift_up(len(heap) - 1)


def extract():
    extract_element = heap[0]
    heap[0] = heap[-1]
    heap.pop()
    sift_down(0)
    return extract_element


heap = []
n = int(input())
for _ in range(n):
    request, *element = map(int, input().split())
    if request:
        print(extract())
    else:
        insert(*element)
