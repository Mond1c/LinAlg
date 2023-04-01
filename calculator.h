#pragma once
#include "dynamic_matrix.h"

namespace linalg {
    class Calculator {
    public:
        static Matrix solveSystemOfEquations(const Matrix& A, const Matrix& B);
    };
}
