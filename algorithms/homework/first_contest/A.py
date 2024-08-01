def binary_search(question):
    left = 0
    right = n - 1

    while left <= right:
        mid = (left + right) // 2
        if question == arr[mid]:
            return "YES"
        elif question < arr[mid]:
            right = mid - 1
        else:
            left = mid + 1
    return "NO"


n, k = map(int, input().split())
arr = list(map(int, input().split()))
questions = list(map(int, input().split()))

for i in questions:
    print(binary_search(i))
