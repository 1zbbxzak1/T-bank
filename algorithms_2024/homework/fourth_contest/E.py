import sys


def is_valid( n, k, arr, mid):
    count = 0
    curr_sum = 0
    for i in range(n):
        if arr[i] > mid:
            return False
        if curr_sum + arr[i] > mid:
            count += 1
            curr_sum = arr[i]
        else:
            curr_sum += arr[i]

    count += 1
    return count <= k


def find_value(n, k, arr):
    left = 1
    right = sum(arr)
    result = 0
    while left <= right:
        mid = (left + right) // 2
        if is_valid(n, k, arr, mid):
            result = mid
            right = mid - 1
        else:
            left = mid + 1
    return result


n, k = map(int, sys.stdin.readline().split())
arr = list(map(int, sys.stdin.readline().split()))
print(find_value(n, k, arr))
