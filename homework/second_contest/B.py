from collections import deque

class Deque:
    def __init__(self):
        self.deq = deque()
        self.result = []

    def get_min(self, n, k, arr):
        for element in range(n):
            while self.deq and self.deq[0] <= element - k:
                self.deq.popleft()

            while self.deq and arr[self.deq[-1]] >= arr[element]:
                self.deq.pop()

            self.deq.append(element)

            if element >= k - 1:
                self.result.append(arr[self.deq[0]])

        return self.result


n, k = map(int, input().split())
arr = list(map(int, input().split()))

deq = Deque()
result = deq.get_min(n, k, arr)
print(*result)
