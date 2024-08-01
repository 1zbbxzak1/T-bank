def levenshtein_distance(s1, s2):
    dictionary = {}
    length_a = len(s1)
    length_b = len(s2)
    for i in range(-1, length_a + 1):
        dictionary[i, -1] = i + 1
    for j in range(-1, length_b + 1):
        dictionary[-1, j] = j + 1

    for i in range(length_a):
        for j in range(length_b):
            if s1[i] == s2[j]:
                z = 0
            else:
                z = 1

            dictionary[i, j] = min(
                dictionary[i - 1, j] + 1,
                dictionary[i, j - 1] + 1,
                dictionary[i - 1, j - 1] + z,
            )
            if i and j and s1[i] == s2[j - 1] and s1[i - 1] == s2[j]:
                dictionary[i, j] = min(dictionary[i, j], dictionary[i - 2, j - 2] + z)

    return dictionary[length_a - 1, length_b - 1]


a = str(input())
b = str(input())
print(levenshtein_distance(a, b))
