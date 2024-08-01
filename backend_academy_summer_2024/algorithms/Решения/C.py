# import sys
#
# n, k, a, m = map(int, sys.stdin.readline().split())
#
#
# def lcg(e):
#     return (a * e + 11) % m
#
#
# def generator(seed):
#     count_coins = 0
#     sum_in_machine = 0
#     total_candies = 0
#     sum_to_purchase = 3 * k
#     while True:
#         seed = lcg(seed)
#         coin = (abs(seed % 3 - 1) * 5 + abs(seed % 3) * 2) % 8
#         count_coins += 1
#         sum_in_machine += coin
#         if sum_in_machine >= sum_to_purchase:
#             bought_candies = sum_in_machine // 3
#             total_candies += bought_candies
#             sum_in_machine -= bought_candies * 3
#         if total_candies >= n:
#             break
#     print(count_coins)
#
#
# generator(0)
#
# # n - сколько нужно купить
# # k - кол-во конфет для покупки в автомате
# # 3 рубля - одна конфета

import sys


def lcg(e, a, m):
    return (a * e + 11) % m


def generate_sequence(seed, a, m):
    while True:
        seed = lcg(seed, a, m)
        coin = (abs(seed % 3 - 1) * 5 + abs(seed % 3) * 2) % 8
        yield coin


n, k, a, m = map(int, sys.stdin.readline().split())

coin_generator = generate_sequence(0, a, m)
count_coins = 0
sum_in_machine = 0
total_candies = 0
required_candies = n
min_candies = k
cost_per_candy = 3

while total_candies < required_candies:
    coin = next(coin_generator)
    count_coins += 1
    sum_in_machine += coin

    while sum_in_machine >= cost_per_candy * min_candies:
        candies = sum_in_machine // cost_per_candy
        candies_to_buy = max(candies, min_candies)
        total_candies += candies_to_buy
        sum_in_machine -= candies_to_buy * cost_per_candy
        if total_candies >= required_candies:
            break

print(count_coins)