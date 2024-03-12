class Postfix:
    def __init__(self):
        self.stack = []

    def postfix_result(self, postfix_input):
        operations = {'+': lambda x, y: x + y,
                      '-': lambda x, y: x - y,
                      '*': lambda x, y: x * y}

        for char in postfix_input:
            if char.isdigit():
                self.stack.append(int(char))
            else:
                y = self.stack.pop()
                x = self.stack.pop()
                _operations = operations[char]
                self.stack.append(_operations(x, y))

        return self.stack[-1]


str = input().split()
postfix = Postfix()
print(postfix.postfix_result(str))
