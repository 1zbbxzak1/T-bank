function findMaxFives(n, grades) {
    let countFives = 0;
    let countTwoOrThree = 0;
    let maxFives = -1;
    let left = 0;

    for (let right = 0; right < n; right++) {
        if (grades[right] === 5) {
            countFives++;
        } else if (grades[right] <= 3) {
            countTwoOrThree++;
        }

        while (right - left + 1 > 7) {
            if (grades[left] <= 3) {
                countTwoOrThree--;
            } else if (grades[left] === 5) {
                countFives--;
            }

            left++;
        }

        if (countTwoOrThree === 0 && right - left + 1 === 7) {
            maxFives = Math.max(countFives, maxFives);
        }
    }

    return maxFives;
}


const readline= require('readline');

const rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout
});

let n;
let grades = [];

rl.question('', (answer) => {
    n = parseInt(answer);

    rl.question('', (answer) => {
        grades = answer.split(' ').map(Number);
        console.log(findMaxFives(n, grades));
        rl.close();
    });
});