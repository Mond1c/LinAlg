package org.linalg.expression.parts;

import org.linalg.expression.PartOfExpression;

import java.math.BigDecimal;

public class Tensor implements Type, PartOfExpression {
    private final BigDecimal[][][][][] data; // TODO: Need to add implementation for tensors with more than 5 indexes.
    private final int n;
    private final int m;
    private final int k;
    private final int l;
    private final int p;

    public Tensor(int n, int m, int k, int l, int p) {
        data = new BigDecimal[n][m][k][l][p];
        this.n = n;
        this.m = m;
        this.k = k;
        this.l = l;
        this.p = p;
    }

    public Tensor(BigDecimal[][][][][] data) {
        this.data = data;
        this.n = data.length;
        this.m = data[0].length;
        this.k = data[0][0].length;
        this.l = data[0][0][0].length;
        this.p = data[0][0][0][0].length;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Tensor tensor) || n != tensor.n || m != tensor.m || k != tensor.k || l != tensor.l || p != tensor.p) {
            return false;
        }
        for (int layer = 0; layer < n; layer++) {
            for (int i1 = 0; i1 < m; i1++) {
                for (int j1 = 0; j1 < k; j1++) {
                    for (int i2 = 0; i2 < l; i2++) {
                        for (int j2 = 0; j2 < p; j2++) {
                            if (!data[layer][i1][j1][i2][j2].equals(tensor.data[layer][i1][j1][i2][j2])) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public Tensor add(Tensor other) {
        Tensor answer = new Tensor(n, m, k, l, p);
        for (int layer = 0; layer < n; layer++) {
            for (int i1 = 0; i1 < m; i1++) {
                for (int j1 = 0; j1 < k; j1++) {
                    for (int i2 = 0; i2 < l; i2++) {
                        for (int j2 = 0; j2 < p; j2++) {
                            answer.data[layer][i1][j1][i2][j2] = data[layer][i1][j1][i2][j2].add(other.data[layer][i1][j1][i2][j2]);
                        }
                    }
                }
            }
        }
        return answer;
    }

    public Tensor subtract(Tensor other) {
        Tensor answer = new Tensor(n, m, k, l, p);
        for (int layer = 0; layer < n; layer++) {
            for (int i1 = 0; i1 < m; i1++) {
                for (int j1 = 0; j1 < k; j1++) {
                    for (int i2 = 0; i2 < l; i2++) {
                        for (int j2 = 0; j2 < p; j2++) {
                            answer.data[layer][i1][j1][i2][j2] = data[layer][i1][j1][i2][j2].subtract(other.data[layer][i1][j1][i2][j2]);
                        }
                    }
                }
            }
        }
        return answer;
    }

    public Tensor multiply(Const other) {
        Tensor answer = new Tensor(n, m, k, l, p);
        for (int layer = 0; layer < n; layer++) {
            for (int i1 = 0; i1 < m; i1++) {
                for (int j1 = 0; j1 < k; j1++) {
                    for (int i2 = 0; i2 < l; i2++) {
                        for (int j2 = 0; j2 < p; j2++) {
                            answer.data[layer][i1][j1][i2][j2] = data[layer][i1][j1][i2][j2].multiply(other.value());
                        }
                    }
                }
            }
        }
        return answer;
    }

    public Tensor multiply(Tensor other) { // TODO: What's the fuck is this? (I don't know how to do it for every tensors)
        throw new UnsupportedOperationException("This operation will be available in the future");
    }

    @Override
    public PartOfExpression add(Type other) {
        if (other instanceof Tensor tensor) {
            return add(tensor);
        }
        throw new IllegalArgumentException("You can add to the tensor only a tensor");
    }

    @Override
    public PartOfExpression subtract(Type other) {
        if (other instanceof Tensor tensor) {
            return subtract(tensor);
        }
        throw new IllegalArgumentException("You can subtract from the tensor only a tensor");
    }

    @Override
    public PartOfExpression multiply(Type other) {
        if (other instanceof Tensor tensor) {
            return multiply(tensor);
        } else if (other instanceof Const c) {
            return multiply(c);
        }
        throw new IllegalArgumentException("You can multiply the tensor only by a tensor");
    }

    @Override
    public PartOfExpression divide(Type other) {
        throw new UnsupportedOperationException("You can't divide a tensor");
    }

    @Override
    public PartOfExpression negate() {
        Tensor answer = new Tensor(n, m, k, l, p);
        for (int layer = 0; layer < n; layer++) {
            for (int i1 = 0; i1 < m; i1++) {
                for (int j1 = 0; j1 < k; j1++) {
                    for (int i2 = 0; i2 < l; i2++) {
                        for (int j2 = 0; j2 < p; j2++) {
                            answer.data[layer][i1][j1][i2][j2] = data[layer][i1][j1][i2][j2].negate();
                        }
                    }
                }
            }
        }
        return answer;
    }

    @Override
    public PartOfExpression pow(Const power) {
        throw new UnsupportedOperationException("Unsupported operation for tensor");
    }

    @Override
    public PartOfExpression evaluate(BigDecimal x) {
        return this;
    }

    @Override
    public PartOfExpression diff() {
        throw new UnsupportedOperationException("You can't diff this operation");
    }
}
