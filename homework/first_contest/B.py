def binary_search(question):
    left = 0
    right = n - 1

    while left < right:
        mid = (left + right) // 2

        if arr[mid] < question:
            left = mid + 1
        else:
            right = mid

    if left > 0 and arr[left] != question and abs(question - arr[left - 1]) <= abs(question - arr[left]):
        return arr[left - 1]
    else:
        return arr[left]


n, k = map(int, input().split())
arr = list(map(int, input().split()))
questions = list(map(int, input().split()))

for i in questions:
    print(binary_search(i))
