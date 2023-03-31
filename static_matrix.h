#pragma once

#include <cstdint>
#include <array>
#include <vector>
#include <iostream>
#include <initializer_list>

namespace linalg {
    template<std::size_t N, std::size_t M>
    class Matrix;

    template<std::size_t N>
    using Vector = Matrix<N, 1>;

    template<std::size_t N, std::size_t M>
    class Matrix {
    private:
        std::array<std::array<double, M>, N> data;
    public:
        Matrix() = default;

        Matrix(std::initializer_list<std::initializer_list<double>> list) {
            if (list.size() != N) {
                throw std::invalid_argument("Row count in init list must be equal to your matrix row count");
            }
            for (std::size_t i = 0; i < N; i++) {
                if ((list.begin() + i)->size() != M) {
                    throw std::invalid_argument("Column count in init list must be equal to your matrix column count");
                }
                for (std::size_t j = 0; j < M; j++) {
                    data[i][j] = *((list.begin() + i)->begin() + j);
                }
            }
        }

        ~Matrix() = default;

    public:
        std::array<double, M> &operator[](std::size_t index) {
            if (index >= N) {
                throw std::out_of_range("Matrix: Index out of range exception");
            }
            return data[index];
        }

        const std::array<double, M> &operator[](std::size_t index) const {
            if (index >= N) {
                throw std::out_of_range("Matrix: Index out of range exception");
            }
            return data[index];
        }


        template<std::size_t N1, std::size_t M1>
        friend Matrix<N, M> &operator*=(const Matrix<N, M> &lhs, const Matrix<N, M> &rhs);

        template<std::size_t N1, std::size_t M1>
        friend std::ostream &operator<<(std::ostream &out, const Matrix<N1, M1> &matrix);

    private:
        [[nodiscard]] std::vector<std::vector<double>>
        subMatrix(std::size_t row, std::size_t col, std::size_t n) const {
            std::vector<std::vector<double>> result(n, std::vector<double>(n));
            std::size_t curRow = 0, curCol = 0;
            for (std::size_t i = 0; i < N; i++) {
                for (std::size_t j = 0; j < M; j++) {
                    if (i != row && j != col) {
                        result[curRow][curCol++] = data[i][j];
                        if (curCol == N - 1) {
                            curRow++;
                            curCol = 0;
                        }
                    }
                }
            }
            return result;
        }


        double determinantHelper(std::vector<std::vector<double>> &a, std::size_t n) const {
            double determinant = 0;
            if (n == 1) {
                return a[0][0];
            }
            if (n == 2) {
                return a[0][0] * a[1][1] - a[0][1] * a[1][0];
            }
            double sign = 1;
            for (std::size_t i = 0; i < n; i++) {
                auto sm = subMatrix(0, i, n);
                determinant += sign * a[0][i] * determinantHelper(sm, n - 1);
                sign = -sign;
            }
            return determinant;
        }

        [[nodiscard]] std::vector<std::vector<double>> getAdj() const {
            std::vector<std::vector<double>> result(N, std::vector<double>(M));
            double sign = 1;
            for (std::size_t i = 0; i < N; i++) {
                for (std::size_t j = 0; j < M; j++) {
                    auto t = subMatrix(i, j, N);
                    sign = ((i + j) % 2 == 0) ? 1 : -1;
                    result[j][i] = sign * determinantHelper(t, N - 1);
                }
            }
            return result;
        }

        [[nodiscard]] std::size_t lcm(std::size_t i, std::size_t j) const {
            while (j != 0) {
                std::size_t k = j;
                j = i % j;
                i = k;
            }
            return i;
        }

    public:
        Matrix<M, N> transpose() const {
            Matrix<M, N> result;
            for (std::size_t i = 0; i < N; i++) {
                for (std::size_t j = 0; j < M; j++) {
                    result[j][i] = data[i][j];
                }
            }
            return result;
        }

        [[nodiscard]] double determinant() const {
            if (N != M) {
                throw std::invalid_argument("can't find determinant of non-square matrix");
            }
            std::vector<std::vector<double>> a(N);
            for (std::size_t i = 0; i < N; i++) {
                a[i] = std::vector<double>(data[i].begin(), data[i].end());
            }
            return determinantHelper(a, N);
        }

        Matrix<N, M> inverse() const {
            if (M != N) {
                throw std::invalid_argument("can't find inverse matrix of non-square matrix");
            }
            double det = determinant();
            if (det == 0) {
                throw std::invalid_argument("can't find inverse matrix because determinant = 0");
            }
            auto adj = getAdj();
            Matrix<N, M> result;
            for (std::size_t i = 0; i < N; i++) {
                for (std::size_t j = 0; j < M; j++) {
                    result[i][j] = adj[i][j] / det;
                }
            }
            return result;
        }

        [[nodiscard]] std::size_t rank() const {
            std::size_t rank = 0;
            std::vector<std::vector<double>> a(N);
            for (std::size_t i = 0; i < N; i++) {
                a[i] = std::vector<double>(data[i].begin(), data[i].end());
            }
            std::vector<bool> selected_row(N);
            for (std::size_t i = 0; i < M; i++) {
                std::size_t j;
                for (j = 0; j < N; j++) {
                    if (!selected_row[j] && std::abs(a[j][i]) > 1E-9) {
                        break;
                    }
                }

                if (j != N) {
                    rank++;
                    selected_row[j] = true;
                    for (std::size_t k = i + 1; k < M; k++) {
                        a[j][k] /= a[j][i];
                    }
                    for (std::size_t k = 0; k < N; k++) {
                        if (k != j && std::abs(a[k][i]) > 1E-9) {
                            for (std::size_t p = i + 1; p < M; p++) {
                                a[k][p] -= a[j][p] * a[k][i];
                            }
                        }
                    }
                }
            }
            return rank;
        }
    };


    template<std::size_t N, std::size_t M>
    Matrix<N, M> operator+(const Matrix<N, M> &lhs, const Matrix<N, M> &rhs) {
        Matrix<N, M> result;
        for (std::size_t i = 0; i < N; i++) {
            for (std::size_t j = 0; j < M; j++) {
                result[i][j] = lhs[i][j] + rhs[i][j];
            }
        }
        return result;
    }

    template<std::size_t N, std::size_t M>
    Matrix<N, M> &operator+=(Matrix<N, M> &lhs, const Matrix<N, M> &rhs) {
        for (std::size_t i = 0; i < N; i++) {
            for (std::size_t j = 0; j < M; j++) {
                lhs[i][j] += rhs[i][j];
            }
        }
        return lhs;
    }

    template<std::size_t N, std::size_t M>
    Matrix<N, M> operator-(const Matrix<N, M> &lhs, const Matrix<N, M> &rhs) {
        Matrix<N, M> result;
        for (std::size_t i = 0; i < N; i++) {
            for (std::size_t j = 0; j < M; j++) {
                result[i][j] = lhs[i][j] - rhs[i][j];
            }
        }
        return result;
    }

    template<std::size_t N, std::size_t M>
    Matrix<N, M> &operator-=(Matrix<N, M> &lhs, const Matrix<N, M> &rhs) {
        for (std::size_t i = 0; i < N; i++) {
            for (std::size_t j = 0; j < M; j++) {
                lhs[i][j] -= rhs[i][j];
            }
        }
        return lhs;
    }

    template<std::size_t N, std::size_t M, std::size_t K>
    Matrix<N, K> operator*(const Matrix<N, M> &lhs, const Matrix<M, K> &rhs) {
        Matrix<N, K> result;
        for (std::size_t i = 0; i < N; i++) {
            for (std::size_t j = 0; j < K; j++) {
                result[i][j] = 0;
                for (std::size_t k = 0; k < M; k++) {
                    result[i][j] += lhs[i][k] * rhs[k][j];
                }
            }
        }
        return result;
    }

    template<std::size_t N, std::size_t M>
    Matrix<N, M> &operator*=(const Matrix<N, M> &lhs, const Matrix<N, M> &rhs) {
        std::array<std::array<double, M>, M> temp;
        for (std::size_t i = 0; i < N; i++) {
            for (std::size_t j = 0; j < M; j++) {
                temp[i][j] = 0;
                for (std::size_t k = 0; k < M; k++) {
                    temp[i][j] += lhs[i][k] * rhs[k][j];
                }
            }
        }
        return lhs.data = temp;
    }

    template<std::size_t N, std::size_t M>
    Matrix<N, M> operator*(const Matrix<N, M> &lhs, double scalar) {
        Matrix<N, M> result;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                result[i][j] = lhs[i][j] * scalar;
            }
        }
        return result;
    }

    template<std::size_t N, std::size_t M>
    Matrix<N, M> &operator*=(const Matrix<N, M> &lhs, double scalar) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                lhs[i][j] *= scalar;
            }
        }
        return lhs;
    }

    template<std::size_t N, std::size_t M>
    std::ostream &operator<<(std::ostream &out, const Matrix<N, M> &matrix) {
        std::size_t max_length = 0;
        for (std::size_t i = 0; i < N; i++) {
            for (std::size_t j = 0; j < M; j++) {
                max_length = std::max(max_length, std::to_string(matrix.data[i][j]).size());
            }
        }
        for (std::size_t i = 0; i < N; i++) {
            for (std::size_t j = 0; j < M; j++) {
                if (j != 0) {
                    out << "| ";
                }
                out << std::to_string(matrix.data[i][j]);
                for (std::size_t k = 0; k <= max_length - std::to_string(matrix.data[i][j]).size(); k++) {
                    out << " ";
                }
            }
            if (i != N - 1) {
                out << std::endl;
            }
        }
        return out;
    }
}