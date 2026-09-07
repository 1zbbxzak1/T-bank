import sys

# Алгоритм Манакера
def get_count_palindromes(line):
    string = '#' + '#'.join(line) + '#'
    length = len(string)

    palindromes = [0] * length

    start, end = 0, -1

    for i in range(length):
        if i < end:
            count = min(palindromes[start + end - i], end - i + 1)
        else:
            count = 1

        while (i - count >= 0) and (i + count < length) and (string[i - count] == string[i + count]):
            count += 1

        palindromes[i] = count

        if i + count - 1 > end:
            start, end = i - count + 1, i + count - 1

    return sum([count // 2 for count in palindromes])


line = sys.stdin.readline().strip()
print(get_count_palindromes(line))
