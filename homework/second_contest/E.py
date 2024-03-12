N = int(input())
way = list(map(int, input().split()))

find_railway = 1
global_pull_railway = 0
stack_way = []
operations = []
pre_count_stack = 0


for index, value in enumerate(way):
    if value == find_railway:
        operations.append(f'1 {index + 1 - pre_count_stack}')
        stack_way.extend(way[pre_count_stack:index + 1])
        pre_count_stack = index + 1

        index_previous_element = -2
        find_railway += 1
        local_pull_railway = 1
        while find_railway <= N and index_previous_element >= -len(stack_way):
            if stack_way[index_previous_element] == find_railway:
                find_railway += 1
                local_pull_railway += 1
                index_previous_element -= 1
            else:
                break

        global_pull_railway += local_pull_railway
        operations.append(f'2 {local_pull_railway}')

        while local_pull_railway > 0:
            stack_way.pop()
            local_pull_railway -= 1

if find_railway < N:
    print('0')
else:
    len_stack_operations = len(operations)

    print(len_stack_operations)
    for i in range(len_stack_operations):
        print(operations[i])