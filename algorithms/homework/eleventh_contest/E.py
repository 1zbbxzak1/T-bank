import sys

line = sys.stdin.readline().strip()
size = len(line)
packed = [["" for _ in range(size)] for _ in range(size)]

for length in range(1, size + 1):
    for length in range(size - length + 1):
        r = length + length - 1
        min_line = line[length:r + 1]
        if length > 4:
            for right in range(length, r):
                left = right + 1
                template = packed[length][right] + packed[left][r]
                if len(template) < len(min_line):
                    min_line = template
            for p in range(1, length):
                if length % p == 0:
                    sequence = all(line[i] == line[i - p] for i in range(length + p, r + 1))
                    if sequence:
                        template = f"{length // p}({packed[length][length + p - 1]})"
                        if len(template) < len(min_line):
                            min_line = template
        packed[length][r] = min_line

print(packed[0][size - 1])
