class Heap:
    def __init__(self):
        self.heap = []

    def sift_down(self, idx):
        left = 2 * idx
        right = 2 * idx + 1
        idx_max = left
        length = len(self.heap) - 1

        if left > length:
            return idx

        if (right <= length) and (self.heap[left] < self.heap[right]):
            idx_max = right
        if self.heap[idx] < self.heap[idx_max]:
            self.heap[idx], self.heap[idx_max] = self.heap[idx_max], self.heap[idx]
            return self.sift_down(idx_max)
        return idx
        # while 2 * idx + 1 < len(self.heap):
        #     left = 2 * idx + 1
        #     right = 2 * idx + 2
        #     j = left
        #     if right < len(self.heap) and self.heap[right] > self.heap[left]:
        #         j = right
        #     if self.heap[idx] >= self.heap[j]:
        #         break
        #     self.heap[idx], self.heap[j] = self.heap[j], self.heap[idx]
        #     idx = j

    def sift_up(self, idx):
        while idx > 0 and self.heap[idx] > self.heap[(idx - 1) // 2]:
            self.heap[idx], self.heap[(idx - 1) // 2] = self.heap[(idx - 1) // 2], self.heap[idx]
            idx = (idx - 1) // 2

    def insert(self, element):
        self.heap.append(element)
        self.sift_up(len(self.heap) - 1)

    def extract(self):
        result = self.heap[0]
        self.heap[0] = self.heap[-1]
        self.heap.pop()
        self.sift_down(0)
        return result


heap = Heap()
n = int(input())
for _ in range(n):
    request, *element = map(int, input().split())
    if request:
        print(heap.extract())
    else:
        heap.insert(*element)
