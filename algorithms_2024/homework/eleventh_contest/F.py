def sort_coins_descending(coins):
    for i in range(len(coins) - 1):
        max_index = i
        for j in range(i + 1, len(coins)):
            if coins[j] > coins[max_index]:
                max_index = j
        coins[i], coins[max_index] = coins[max_index], coins[i]


def select_coins(coins, selected, final_selection, coin_index, current_sum, end_index_in_selection, target_sum):
    global has_exact_sum, has_sum_more_than_target, has_sum_less_than_target, selection_size
    if current_sum == target_sum:
        if end_index_in_selection < selection_size:
            selection_size = end_index_in_selection
            final_selection[:selection_size + 1] = selected[:selection_size + 1]
        has_exact_sum = True
        return
    if coin_index == len(coins) or current_sum > target_sum:
        if current_sum > target_sum:
            has_sum_more_than_target = True
        return
    if current_sum != 0 and current_sum < target_sum:
        has_sum_less_than_target = True
    for i in range(2, -1, -1):
        current_sum += coins[coin_index] * i
        for j in range(1, i + 1):
            end_index_in_selection += 1
            selected[end_index_in_selection] = coins[coin_index]
        coin_index += 1
        select_coins(coins, selected, final_selection, coin_index, current_sum, end_index_in_selection, target_sum)
        coin_index -= 1
        current_sum -= coins[coin_index] * i
        end_index_in_selection -= i


target_sum, num_types = map(int, input().split())
coin_types = list(map(int, input().split()))
sort_coins_descending(coin_types)

selected_coins = [0] * (num_types * 2)
final_selection = [0] * (num_types * 2)
coin_index = 0
end_index_in_selection = -1
current_sum = 0
selection_size = num_types * 2

has_exact_sum = False
has_sum_more_than_target = False
has_sum_less_than_target = False

select_coins(coin_types, selected_coins, final_selection, coin_index, current_sum, end_index_in_selection, target_sum)

if has_exact_sum:
    print(selection_size + 1)
    print(*final_selection[:selection_size + 1])
elif has_sum_more_than_target:
    print(0)
else:
    print(-1)
