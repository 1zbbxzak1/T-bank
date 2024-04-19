def rotate_matrix(matrix):
    n = len(matrix)
    m = len(matrix[0])

    rotated_matrix = []
    for j in range(m):
        rotated_matrix.append([])
        for i in range(n - 1, -1, -1):
            rotated_matrix[j].append(matrix[i][j])

    return rotated_matrix


def print_matrix(matrix):
    for row in matrix:
        print(' '.join(map(str, row)))


n, m = map(int, input().split())
matrix = []

for _ in range(n):
    row = list(map(int, input().split()))
    matrix.append(row)

print_matrix(rotate_matrix(matrix))
