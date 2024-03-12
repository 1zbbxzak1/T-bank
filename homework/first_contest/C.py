import sys


def query(x):
    print(x)
    sys.stdout.flush()
    return input()


def binary_search(n: int):
    if n == 1:
        print(f'! {n}')
        return

    low = 1
    high = n

    previousSkip: int = -1
    while low <= high:
        mid = (low + high) // 2

        response = query(mid)

        if response == '<':
            high = mid - 1
            previousSkip = mid
            if previousSkip == 2:
                break
        else:
            if mid == previousSkip - 1:
                break
            low = mid + 1

    mid = (low + high) // 2
    print('!', mid if mid else 1)
    sys.stdout.flush()


n = int(input())
binary_search(n)
