class Stack:
    def __init__(self):
        self.stack = []
        self.min_stack = []

    def push(self, item):
        self.stack.append(item)

        if not self.min_stack or item <= self.min_stack[-1]:
            self.min_stack.append(item)

    def pop(self):
        if self.stack:
            popped_item = self.stack.pop()
            if popped_item == self.min_stack[-1]:
                self.min_stack.pop()

    def get_min_item(self):
        return self.min_stack[-1]


s = Stack()
n = int(input())
for i in range(n):
    op_type, *args = map(int, input().split())
    if op_type == 1:
        element = args[0]
        s.push(element)
    elif op_type == 2:
        s.pop()
    else:
        print(s.get_min_item())
