def palindromes(arr: str) -> str:
    freq = {}
    for letter in arr:
        freq[letter] = freq.get(letter, 0) + 1

    alphabet = sorted(freq.items(), key=lambda item: item[0])

    palindrome = []
    mid = ''
    for letter, count in alphabet:
        palindrome.extend([letter] * (count // 2))
        if count % 2 == 1 and not mid:
            mid = letter

    return ''.join(palindrome + [mid] + palindrome[::-1])


N = int(input())
arr = str(input())[:N]

print(palindromes(arr))