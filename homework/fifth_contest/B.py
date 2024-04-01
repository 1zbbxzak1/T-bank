def prefix(s):
    n = len(s)
    pref = [0] * n
    pref[0] = 0

    for i in range(1, n):
        j = pref[i - 1]
        while j > 0 and s[i] != s[j]:
            j = pref[j - 1]
        if s[i] == s[j]:
            j += 1
        pref[i] = j

    return pref


def kmp(t, s):
    p_size = len(s)
    t_size = len(t)
    pref = prefix(s + "#" + t)
    result = []

    for i in range(p_size, p_size + 1 + t_size):
        if pref[i] == p_size:
            result.append(i - p_size * 2)

    return result


t = input().strip()
q = int(input())

for i in range(q):
    s = input().strip()

    res = kmp(t, s)

    print(len(res), *res)
