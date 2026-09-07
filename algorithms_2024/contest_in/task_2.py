n = int(input())
str = input()

lines = str.split("#")

min_length = min(len(line) for line in lines)
max_length = max(len(line) for line in lines)

print(min_length, max_length)