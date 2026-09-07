def rk(s):
    n = len(s)
    s += s
    offset = 0
    minimum = s

    for i in range(1, n):
        if s[i:i+n] < minimum:
            minimum = s[i:i+n]
            offset = i
    return minimum[:n], offset


s = input()
result, offset = rk(s)
print(result)