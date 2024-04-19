def find_max_fives(n, grades):
    count_fives = 0
    count_two_or_three = 0
    max_fives = -1
    left = 0

    for right in range(n):
        if grades[right] == 5:
            count_fives += 1
        elif grades[right] <= 3:
            count_two_or_three += 1

        while right - left + 1 > 7:
            if grades[left] <= 3:
                count_two_or_three -= 1
            elif grades[left] == 5:
                count_fives -= 1

            left += 1

        if count_two_or_three == 0 and right - left + 1 == 7:
            max_fives = max(count_fives, max_fives)

    return max_fives


n = int(input())
grades = list(map(int, input().split()))

print(find_max_fives(n, grades))