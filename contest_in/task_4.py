def generate_book_sequence(a, b):
    result_sequence = []
    prev = -1
    inter = -1

    for current_book in b:
        if current_book == prev + 1:
            inter = current_book
        elif current_book == inter + 1:
            result_sequence.append('...')
            inter = current_book
        else:
            if inter != -1:
                result_sequence.append(str(inter))
                inter = -1
            result_sequence.append(str(current_book))
            prev = current_book

    if inter != -1:
        result_sequence.append(str(inter))

    return ' '.join(result_sequence)


length = int(input())
book_numbers = sorted(set(map(int, input().split())))

result = generate_book_sequence(length, book_numbers)
print(result)
