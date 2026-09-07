def delete_balloons(n: int, color_balloons: list) -> int:
    stack = []
    result = 0
    i = 0

    while i < n:
        color = color_balloons[i]

        if not stack or stack[-1][0] != color:
            stack.append([color, 1])
        else:
            stack[-1][1] += 1

        if stack[-1][1] >= 3:
            j = i + 1
            while j < n and color_balloons[j] == color:
                stack[-1][1] += 1
                j += 1

            result += stack[-1][1]
            while stack and stack[-1][1] >= 3:
                stack.pop()

            i = j - 1

        i += 1

    return result


n = int(input())
color_balloons = list(map(int, input().split()))

print(delete_balloons(n, color_balloons))

# 5
# 1 3 3 3 2
# вывод: 3

# 10
# 3 3 2 1 1 1 2 2 3 3
# вывод: 10

# 1
# 0
# Вывод: 0

# 3
# 1 1 1
# Вывод: 3

# 4
# 1 1 2 1
# Вывод: 0

# 6
# 2 2 9 9 9 2
# Вывод: 6

# 10
# 1 1 2 2 9 9 9 2 3 1
# Вывод: 6
