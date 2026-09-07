import sys

n = int(sys.stdin.readline())
socks = list(map(int, sys.stdin.readline().split()))
result = True

for i in range(n):
    total_sock = socks[i] % 2 == 0
    if total_sock != result:
        result = False
    else:
        result = True

if result:
    print('YES')
else:
    print('NO')