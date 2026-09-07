def process_positions(n, positions):
    dictionary = {}
    length = 0
    N = n
    result = [1]

    for num in positions:
        dictionary[num] = True
        length += 1

        while N in dictionary:
            del dictionary[N]
            length -= 1
            N -= 1

        result.append(length + 1)

    return result


n = int(input())
positions = list(map(int, input().split()))
result = process_positions(n, positions)
print(*result)
