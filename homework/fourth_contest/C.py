def is_valid(min_distance, positions, num_of_cows):
    cows_placed = 1  # Первая корова размещена на позиции positions[0]
    last_cow_position = positions[0]

    # Перебираем позиции коров в порядке их расположения
    for position in positions:
        if position - last_cow_position >= min_distance:
            cows_placed += 1
            last_cow_position = position

    # Проверяем, достаточно ли размещено коров
    return cows_placed >= num_of_cows


def get_maximum_min_distance(positions, num_of_cows):
    left = 0
    right = positions[-1] - positions[0] + 1

    while right - left != 1:
        mid = (left + right) // 2
        # Если можно разместить коров на расстоянии mid, увеличиваем min_distance
        if is_valid(mid, positions, num_of_cows):
            left = mid
        # Иначе уменьшаем min_distance
        else:
            right = mid

    return left


n, k = map(int, input().split())
cow_positions = list(map(int, input().split()))

print(get_maximum_min_distance(cow_positions, k))