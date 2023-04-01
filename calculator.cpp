#include "calculator.h"
using namespace linalg;

Matrix Calculator::solveSystemOfEquations(const linalg::Matrix& A, const linalg::Matrix& B) {
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
    return C;
}
