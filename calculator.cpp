#include "calculator.h"

using namespace linalg;

std::vector<std::string> Calculator::solveSystemOfEquations(const linalg::Matrix &A, const linalg::Matrix &B) {
    if (A.getN() != B.getN()) {
        throw std::invalid_argument("Count of rows of the matrix B must be equal to count of rows of the matrix A");
    } else if (B.getM() != 1) {
        throw std::invalid_argument("Count of columns of the matrix B must be equals 1");
    }
    Matrix C(A.getN(), A.getM() + B.getM());
    for (std::size_t i = 0; i < A.getN(); i++) {
        for (std::size_t j = 0; j < A.getM(); j++) {
            C[i][j] = A[i][j];
        }
    }
    for (std::size_t i = 0; i < A.getN(); i++) {
        C[i][A.getM()] = B[i][0];
    }
    C = C.triangle();

    std::vector<std::string> ans;
    for (std::size_t i = C.getN() - 1; i >= 0; i--) {
        std::string str;
        for (std::size_t j = 0; j < C.getM() - 1; j++) {
            if (C[i][j] != 0) {
                str += std::to_string(C[i][j]) + " * x" + std::to_string(j + 1)) + " +";
            }
        }
        if (!str.empty()) {
            str.pop_back();
            str += "= " + std::to_string(C[i][C.getM() - 1]);
            ans.push_back(str);
        }
    }
    return ans;
}
