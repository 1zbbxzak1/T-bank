#include <bits/stdc++.h>
#define int long long
using namespace std;

const int MAX_N = 1e5 + 9;
const int BITS = 22;

struct SegmentTreeNode {
    int lazy[BITS + 1], len;
    int bitsCount[BITS + 1];

    SegmentTreeNode operator+(const SegmentTreeNode &other) const {
        SegmentTreeNode result{};
        for (int i = 1; i <= BITS; ++i) {
            result.lazy[i] = 0;
            result.bitsCount[i] = bitsCount[i] + other.bitsCount[i];
        }
        return result;
    }
};

SegmentTreeNode tree[4 * MAX_N];
int arr[MAX_N];
int power[BITS + 1];
int queryResult[BITS + 1];

int readInt() {
    int x = 0, f = 1;
    char ch = getchar();
    while (ch < '0' || ch > '9') {
        if (ch == '-') f = -1;
        ch = getchar();
    }
    while (ch >= '0' && ch <= '9') {
        x = (x << 1) + (x << 3) + (ch ^ 48);
        ch = getchar();
    }
    return x * f;
}

void updateNode(int p) {
    int len = tree[p].len;
    tree[p] = tree[p * 2] + tree[p * 2 + 1];
    tree[p].len = len;
}

void pushDownLazy(int p) {
    for (int i = 1; i <= BITS; ++i) {
        if (tree[p].lazy[i] == 0) continue;
        tree[p * 2].bitsCount[i] = tree[p * 2].len - tree[p * 2].bitsCount[i];
        tree[p * 2 + 1].bitsCount[i] = tree[p * 2 + 1].len - tree[p * 2 + 1].bitsCount[i];
    }
    for (int i = 1; i <= BITS; ++i) {
        tree[p * 2].lazy[i] ^= tree[p].lazy[i];
        tree[p * 2 + 1].lazy[i] ^= tree[p].lazy[i];
        tree[p].lazy[i] = 0;
    }
}

void buildSegmentTree(int p, int l, int r) {
    tree[p].len = r - l + 1;
    if (l == r) {
        for (int i = 1; i <= BITS; ++i) {
            tree[p].bitsCount[i] = (arr[l] >> (i - 1)) & 1;
        }
        return;
    }
    int mid = (l + r) / 2;
    buildSegmentTree(p * 2, l, mid);
    buildSegmentTree(p * 2 + 1, mid + 1, r);
    updateNode(p);
}

void applyXor(int rt, int l, int r, int L, int R, int k) {
    if (l >= L && r <= R) {
        for (int i = 1; i <= BITS; ++i) {
            int b = (k >> (i - 1)) & 1;
            tree[rt].lazy[i] ^= b;
            if (b == 1) tree[rt].bitsCount[i] = tree[rt].len - tree[rt].bitsCount[i];
        }
        return;
    }
    pushDownLazy(rt);
    int mid = (l + r) / 2;
    if (mid >= L) applyXor(rt * 2, l, mid, L, R, k);
    if (mid < R) applyXor(rt * 2 + 1, mid + 1, r, L, R, k);
    updateNode(rt);
}

void querySum(int rt, int l, int r, int L, int R) {
    if (l >= L && r <= R) {
        for (int i = 1; i <= BITS; ++i) queryResult[i] += tree[rt].bitsCount[i];
        return;
    }
    pushDownLazy(rt);
    int mid = (l + r) / 2;
    if (mid >= L) querySum(rt * 2, l, mid, L, R);
    if (mid < R) querySum(rt * 2 + 1, mid + 1, r, L, R);
    updateNode(rt);
}

signed main() {
    memset(tree, 0, sizeof(tree));
    power[1] = 1;
    for (int i = 2; i <= BITS + 1; ++i) power[i] = power[i - 1] * 2;

    int n = readInt();
    for (int i = 1; i <= n; ++i) arr[i] = readInt();
    buildSegmentTree(1, 1, n);

    int m = readInt();
    for (int i = 1; i <= m; ++i) {
        int tag = readInt(), l = readInt(), r = readInt();
        if (tag == 1) {
            fill(queryResult, queryResult + BITS + 1, 0);
            querySum(1, 1, n, l, r);
            long long resultSum = 0;
            for (int j = 1; j <= BITS; ++j) resultSum += queryResult[j] * power[j];
            printf("%lld\n", resultSum);
        } else {
            int k = readInt();
            applyXor(1, 1, n, l, r, k);
        }
    }
    return 0;
}