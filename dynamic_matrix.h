#pragma once
#pragma once

#include <cstdint>
#include <array>
#include <vector>
#include <iostream>
#include <cmath>
#include <initializer_list>

namespace linalg {
    class Matrix {
    private:
        std::vector<std::vector<double>> data;
        std::size_t N;
        std::size_t M;
    public:
        Matrix() = default;

        Matrix(std::size_t N, std::size_t M) : N(N), M(M), data(std::vector<std::vector<double>>(N, std::vector<double>(M))) {}

        Matrix(std::initializer_list<std::initializer_list<double>> list) {
            data = std::vector<std::vector<double>>(list.size());
            N = list.size();
            M = list.begin()->size();
            for (std::size_t i = 0; i < N; i++) {
                data[i] = std::vector<double>(list.begin()->size());
                for (std::size_t j = 0; j < M; j++) {
                    data[i][j] = *((list.begin() + i)->begin() + j);
                }
            }
        }

        ~Matrix() = default;

    public:
        std::vector<double> &operator[](std::size_t index) {
            if (index >= N) {
                throw std::out_of_range("Matrix: Index out of range exception");
            }
            return data[index];
        }

        const std::vector<double> &operator[](std::size_t index) const {
            if (index >= N) {
                throw std::out_of_range("Matrix: Index out of range exception");
            }
            return data[index];
        }


        friend Matrix &operator*=(Matrix &lhs, const Matrix &rhs);

        friend std::ostream &operator<<(std::ostream &out, const Matrix &matrix);
        friend std::istream &operator>>(std::istream& in, Matrix &matrix);

    private:

        double determinant(std::vector<std::vector<double>>& a, std::size_t n) const {
            double det = 1, total = 1;

            for (std::size_t i = 0; i < n; i++) {
                std::size_t index = i;
                while (index < N && a[index][i] == 0) {
                    index++;
                }
                if (index == n) {
                    continue;
                }
                if (index != i) {
                    for (std::size_t j = 0; j < n; j++) {
                        std::swap(a[index][j], a[i][j]);
                    }
                    det = det * ((index - i) % 2 == 0 ? 1 : -1);
                }

                std::vector<double> temp = std::vector<double>(a[i].begin(), a[i].end());

                for (std::size_t j = i + 1; j < n; j++) {
                    double num1 = temp[i];
                    double num2 = a[j][i];
                    for (std::size_t k = 0; k < n; k++) {
                        a[j][k] = (num1 * a[j][k]) - (num2 * temp[k]);
                    }
                    total *= num1;
                }
            }
            for (std::size_t i = 0; i < n; i++) {
                det = det * a[i][i];
            }
            return det / total;
        }

        static std::vector<std::vector<double>> getCofactor(std::vector<std::vector<double>>& a, std::size_t p, std::size_t q, std::size_t n) {
            std::size_t i = 0, j = 0;
            std::vector<std::vector<double>> ans(n, std::vector<double>(n));
            for (std::size_t row = 0; row < n; row++) {
                for (std::size_t col = 0; col < n; col++) {
                    if (row != p && col != q) {
                        ans[i][j++] = a[row][col];
                        if (j == n - 1) {
                            i++;
                            j = 0;
                        }
                    }
                }
            }
            return ans;
        }

        [[nodiscard]] std::vector<std::vector<double>> getAdj(std::vector<std::vector<double>>& a, std::size_t n) const {
            std::vector<std::vector<double>> ans(n, std::vector<double>(n));
            if (n == 1) {
                return ans;
            }
            double sign = 1;
            for (std::size_t i = 0; i < n; i++) {
                for (std::size_t j = 0; j < n; j++) {
                    auto temp = getCofactor(a, i, j, n);
                    sign = ((i + j) % 2 == 0) ? 1 : -1;
                    ans[j][i] = (sign) * determinant(temp, n - 1);
                }
            }
            return ans;
        }

    public:
        [[nodiscard]] std::size_t getN() const {
            return N;
        }

        [[nodiscard]] std::size_t getM() const {
            return M;
        }

        [[nodiscard]] Matrix transpose() const {
            Matrix result(M, N);
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
            std::vector<std::vector<double>> a(data.begin(), data.end());
            return determinant(a, N);
        }

        [[nodiscard]] Matrix inverse() const {
            if (M != N) {
                throw std::invalid_argument("can't find inverse matrix of non-square matrix");
            }
            double det = determinant();
            if (det == 0) {
                throw std::invalid_argument("can't find inverse matrix because determinant = 0");
            }
            std::vector<std::vector<double>> a(data.begin(), data.end());
            auto adj = getAdj(a, N);
            Matrix result(N, M);
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


    Matrix operator+(const Matrix &lhs, const Matrix &rhs) {
        if (lhs.getN() != rhs.getN() || lhs.getM() != rhs.getM()) {
            throw std::invalid_argument("matrices must have similar size");
        }

        std::size_t N = lhs.getN();
        std::size_t M = lhs.getM();
        Matrix result(N, M);
        for (std::size_t i = 0; i < N; i++) {
            for (std::size_t j = 0; j < M; j++) {
                result[i][j] = lhs[i][j] + rhs[i][j];
            }
        }
        return result;
    }

    Matrix &operator+=(Matrix &lhs, const Matrix &rhs) {
        if (lhs.getN() != rhs.getN() || lhs.getM() != rhs.getM()) {
            throw std::invalid_argument("matrices must have similar size");
        }

        std::size_t N = lhs.getN();
        std::size_t M = lhs.getM();
        for (std::size_t i = 0; i < N; i++) {
            for (std::size_t j = 0; j < M; j++) {
                lhs[i][j] += rhs[i][j];
            }
        }
        return lhs;
    }

    Matrix operator-(const Matrix &lhs, const Matrix &rhs) {
        if (lhs.getN() != rhs.getN() || lhs.getM() != rhs.getM()) {
            throw std::invalid_argument("matrices must have similar size");
        }

        std::size_t N = lhs.getN();
        std::size_t M = lhs.getM();
        Matrix result(N, M);
        for (std::size_t i = 0; i < N; i++) {
            for (std::size_t j = 0; j < M; j++) {
                result[i][j] = lhs[i][j] - rhs[i][j];
            }
        }
        return result;
    }

    Matrix &operator-=(Matrix &lhs, const Matrix &rhs) {
        if (lhs.getN() != rhs.getN() || lhs.getM() != rhs.getM()) {
            throw std::invalid_argument("matrices must have similar size");
        }

        std::size_t N = lhs.getN();
        std::size_t M = lhs.getM();

        for (std::size_t i = 0; i < N; i++) {
            for (std::size_t j = 0; j < M; j++) {
                lhs[i][j] -= rhs[i][j];
            }
        }
        return lhs;
    }

    Matrix operator*(const Matrix &lhs, const Matrix &rhs) {
        if (lhs.getM() != rhs.getN()) {
            throw std::invalid_argument("you can multiply matrices with sizes m * n and n * k");
        }

        std::size_t N = lhs.getN();
        std::size_t M = lhs.getM();
        std::size_t K = rhs.getM();

        Matrix result(N, K);
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

    Matrix &operator*=(Matrix &lhs, const Matrix &rhs) {
        if (lhs.getM() != rhs.getN()) {
            throw std::invalid_argument("you can multiply matrices with sizes m * n and n * k");
        }

        std::size_t N = lhs.getN();
        std::size_t M = lhs.getM();

        std::vector<std::vector<double>> temp(N, std::vector<double>(M));
        for (std::size_t i = 0; i < N; i++) {
            for (std::size_t j = 0; j < M; j++) {
                temp[i][j] = 0;
                for (std::size_t k = 0; k < M; k++) {
                    temp[i][j] += lhs[i][k] * rhs[k][j];
                }
            }
        }
        lhs.data = temp;
        return lhs;
    }

    Matrix operator*(const Matrix &lhs, double scalar) {
        std::size_t N = lhs.getN();
        std::size_t M = lhs.getM();

        Matrix result(N, M);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                result[i][j] = lhs[i][j] * scalar;
            }
        }
        return result;
    }

    Matrix &operator*=(Matrix &lhs, double scalar) {\
        std::size_t N = lhs.getN();
        std::size_t M = lhs.getM();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                lhs[i][j] *= scalar;
            }
        }
        return lhs;
    }

    std::ostream &operator<<(std::ostream &out, const Matrix &matrix) {
        std::size_t max_length = 0;
        std::size_t N = matrix.getN();
        std::size_t M = matrix.getM();
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

    std::istream& operator>>(std::istream& in, Matrix &matrix) {
        std::size_t N = matrix.getN();
        std::size_t M = matrix.getM();

        for (std::size_t i = 0; i < N; i++) {
            for (std::size_t j = 0; j < M; j++) {
                in >> matrix[i][j];
            }
        }
        return in;
    }
}