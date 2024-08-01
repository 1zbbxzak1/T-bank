def sift_up(idx):
    while idx > 0 and heap[idx] < heap[(idx - 1) // 2]:  # Change '>' to '<'
        heap[idx], heap[(idx - 1) // 2] = heap[(idx - 1) // 2], heap[idx]
        idx = (idx - 1) // 2


def sift_down(idx):
    while 2 * idx + 1 < len(heap):
        left = 2 * idx + 1
        right = 2 * idx + 2
        j = left
        if right < len(heap) and heap[right] < heap[left]:  # Change '>' to '<'
            j = right
        if heap[idx] <= heap[j]:  # Change '>=' to '<='
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


def heap_sort(element):
    global heap
    heap = []
    sorted_elements = []
    for el in element:
        insert(el)
    while heap:
        sorted_elements.append(extract())
    return sorted_elements


n = int(input())
element = list(map(int, input().split()))

sorted_element = heap_sort(element)
print(" ".join(map(str, sorted_element)), end="")
