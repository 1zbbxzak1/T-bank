from collections import defaultdict

graph = defaultdict(list)
transfer_mapping = {}

count_places, count_cars, length_path = map(int, input().split())

for _ in range(count_cars):
    start, num, finish = map(int, input().split())
    graph[start].append((finish, num))
    transfer_mapping[(start, num)] = finish

path = list(map(int, input().split()))
current_place = 1

for num_car in path:
    if (current_place, num_car) not in transfer_mapping:
        print(0)
        break

    current_place = transfer_mapping[(current_place, num_car)]

else:
    print(current_place)