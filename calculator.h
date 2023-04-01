#pragma once
#include "dynamic_matrix.h"

namespace linalg {
    class Calculator {
    public:
        static std::vector<std::string>  solveSystemOfEquations(const Matrix& A, const Matrix& B);
    };
}
