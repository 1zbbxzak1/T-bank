function rotateMatrix(matrix) {
    const n = matrix.length;
    const m = matrix[0].length;

    const rotatedMatrix = [];
    for (let j = 0; j < m; j++) {
        rotatedMatrix.push([]);
        for (let i = n - 1; i >= 0; i--) {
            rotatedMatrix[j].push(matrix[i][j]);
        }
    }

    return rotatedMatrix;
}

function printMatrix(matrix) {
    for (let i = 0; i < matrix.length; i++) {
        console.log(matrix[i].join(' '));
    }
}


const readline = require('readline');

const rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout
});

let n, m;
let matrix = [];

rl.question('', (nmInput) => {
    const [nStr, mStr] = nmInput.split(' ');
    n = parseInt(nStr);
    m = parseInt(mStr);

    let linesRead = 0;
    rl.on('line', (line) => {
        const row = line.split(' ').map(num => parseInt(num));
        matrix.push(row);
        linesRead++;

        if (linesRead === n) {
            rl.close();
            printMatrix(rotateMatrix(matrix));
        }
    });
});