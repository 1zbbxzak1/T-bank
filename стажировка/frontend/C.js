function readPaths(count, currentCount) {
    if (currentCount >= count) {
        printDir(dir, 0);
        rl.close();
        return;
    }

    rl.question('',(path) => {
        addPath(path);
        readPaths(count, currentCount + 1);
    });
}

function addPath(path) {
    let currDir = dir;
    const dirList = path.trim().split('/');

    for (let i = 0; i < dirList.length; i++) {
        const nameDir = dirList[i];
        if (!(nameDir in currDir)) {
            currDir[nameDir] = {};
        }
        currDir = currDir[nameDir];
    }
}

function printDir(dir, enclosure) {
    const sortedKeys = Object.keys(dir).sort();
    sortedKeys.forEach(key => {
        console.log('  '.repeat(enclosure) + key);
        printDir(dir[key], enclosure + 1);
    });
}


const readline = require('readline');

const rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout
});

let n;
let dir = {};

rl.question('', (count) => {
    n = parseInt(count);

    readPaths(n, 0);
});