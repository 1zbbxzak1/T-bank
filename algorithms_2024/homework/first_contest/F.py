def merge(left, right):
    merged = []
    inv_count = 0
    i = j = 0

    while i < len(left) and j < len(right):
        if left[i] <= right[j]:
            merged.append(left[i])
            i += 1
        else:
            merged.append(right[j])
            j += 1
            inv_count += len(left) - i

    merged.extend(left[i:])
    merged.extend(right[j:])

    return merged, inv_count


def merge_sort(arr):
    if len(arr) <= 1:
        return arr, 0

    mid = len(arr) // 2
    left_half, left_count = merge_sort(arr[:mid])
    right_half, right_count = merge_sort(arr[mid:])

    merged_arr, merge_count = merge(left_half, right_half)
    total_count = left_count + right_count + merge_count

    return merged_arr, total_count


n = int(input())
arr = list(map(int, input().split()))

if len(arr) == n:
    sorted_arr, inversions = merge_sort(arr)
    print(inversions)
    print(*sorted_arr)
