import sys

MOD = 10 ** 9 + 7


def calculate_d_and_p(n, a):
    d = [1] * n
    p = [-1] * n

    for i in range(1, n):
        for j in range(i):
            if a[j] < a[i] and d[j] + 1 > d[i]:
                d[i] = d[j] + 1
                p[i] = j

    return d, p


def find_longest_subsequence(a, d, p):
    max_length = max(d)
    print(max_length)

    current = d.index(max_length)
    ans = []
    while current != -1:
        ans.append(a[current])
        current = p[current]

    return ans[::-1]


n = int(sys.stdin.readline().strip())
a = list(map(int, sys.stdin.readline().split()))

d, p = calculate_d_and_p(n, a)
longest_subsequence = find_longest_subsequence(a, d, p)

print(*longest_subsequence)
