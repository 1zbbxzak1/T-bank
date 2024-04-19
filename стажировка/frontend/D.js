const readline = require('readline');

const rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout
});

function rotateMatrix(n, matrix, direction) {
    const operations = [];

    function swap(x1, y1, x2, y2) {
        operations.push([x1, y1, x2, y2]);
        const temp = matrix[x1][y1];
        matrix[x1][y1] = matrix[x2][y2];
        matrix[x2][y2] = temp;
    }

    // Поворот по часовой стрелке
    function rotateClockwise() {
        // Транспонирование матрицы
        for (let i = 0; i < n; i++) {
            for (let j = i + 1; j < n; j++) {
                swap(i, j, j, i);
            }
        }
        // Зеркальное отражение по горизонтали
        for (let i = 0; i < n; i++) {
            for (let j = 0; j < n / 2; j++) {
                swap(i, j, i, n - j - 1);
            }
        }
    }

    // Поворот против часовой стрелки
    function rotateCounterClockwise() {
        // Транспонирование матрицы
        for (let i = 0; i < n; i++) {
            for (let j = i + 1; j < n; j++) {
                swap(i, j, j, i);
            }
        }
        // Зеркальное отражение по вертикали
        for (let i = 0; i < n / 2; i++) {
            for (let j = 0; j < n; j++) {
                swap(i, j, n - i - 1, j);
            }
        }
    }

    if (direction === 'R') {
        rotateClockwise();
    } else {
        rotateCounterClockwise();
    }

    console.log(operations.length - 3);
    for (let i = 0; i < operations.length - 3; i++) {
        console.log(operations[i].join(' '));
    }
}

let n;
let direction;
const matrix = [];

rl.question('', (firstLine) => {
    const [size, dir] = firstLine.trim().split(' ');
    n = parseInt(size);
    direction = dir;

    rl.on('line', (line) => {
        const row = line.trim().split(' ').map(Number);
        matrix.push(row);
        if (matrix.length === n) {
            rl.close();
        }
    });

    rl.on('close', () => {
        rotateMatrix(n, matrix, direction);
    });
});
