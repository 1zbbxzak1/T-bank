import sys

n = int(sys.stdin.readline().strip())
attack_power = list(map(int, sys.stdin.readline().split()))
defense_power = list(map(int, sys.stdin.readline().split()))

left = [-1] + list(range(n - 1))
right = list(range(1, n)) + [-1]

defeated = [-1] * n
alive = set(range(n))

for k in range(n):
    defeated_this_round = set()
    for i in alive:
        total_attack = 0
        if left[i] != -1:
            total_attack += attack_power[left[i]]
        if right[i] != -1:
            total_attack += attack_power[right[i]]
        if total_attack > defense_power[i]:
            defeated_this_round.add(i)
    alive.clear()

    print(len(defeated_this_round), end=" ")

    for player_defeated in defeated_this_round:
        if left[player_defeated] != -1:
            right[left[player_defeated]] = right[player_defeated]
            if left[player_defeated] not in defeated_this_round:
                alive.add(left[player_defeated])
        if right[player_defeated] != -1:
            left[right[player_defeated]] = left[player_defeated]
            if right[player_defeated] not in defeated_this_round:
                alive.add(right[player_defeated])
