const readline = require('readline');

const rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout
});

let n;
let str = [];

rl.question("", (input) => {
    n = parseInt(input);

    readStr(n, () => {
        let dp = new Array(n).fill().map(() => new Array(3).fill(0));

        for (let j = 0; j < 3; j++) {
            if (str[n - 1][j] === "C") {
                dp[n - 1][j] = 1;
            }
        }

        for (let i = n - 2; i >= 0; i--) {
            for (let j = 0; j < 3; j++) {
                if (str[i][j] === "W") {
                    continue;
                }

                for (let k = j - 1; k <= j + 1; k++) {
                    if (k >= 0 && k < 3 && str[i + 1][k] !== 'W') {
                        let has_mushroom = 0;
                        if (str[i][j] === "C") {
                            has_mushroom = 1;
                        }

                        dp[i][j] = Math.max(dp[i][j], dp[i + 1][k] + has_mushroom);
                    }
                }
            }
        }

        console.log(Math.max(...dp[0]));

        rl.close();
    });
});

function readStr(n, callback) {
    let count = 0;
    rl.on('line', (line) => {
        str.push(line.trim());
        count++;
        if (count === n) {
            callback();
            rl.removeAllListeners('line');
        }
    });
}