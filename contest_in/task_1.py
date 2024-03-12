import math


def calculate_score(x, y):
    distance = math.sqrt(x ** 2 + y ** 2)

    if distance <= 0.1:
        return 3
    elif 0.1 < distance <= 0.8:
        return 2
    elif 0.8 < distance <= 1:
        return 1
    else:
        return 0


x1, y1 = map(float, input().split())
x2, y2 = map(float, input().split())
x3, y3 = map(float, input().split())

score = calculate_score(x1, y1) + calculate_score(x2, y2) + calculate_score(x3, y3)

print(score)
