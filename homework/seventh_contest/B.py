def count_patterns(n):
    counts = [1, 3] + [0] * (n - 1)

    for i in range(2, n + 1):
        counts[i] = 2 * (counts[i - 1] + counts[i - 2])

    return counts[n]


n = int(input())
print(count_patterns(n))
