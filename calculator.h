#pragma once
#include "dynamic_matrix.h"
#include <climits>

namespace linalg {
double gcd(double a, double b) {
  if (std::abs(a) < 0.001 && std::abs(b) < 0.001) {
    return INT_MAX;
  }
  if (a < b) {
    return gcd(b, a);
  }
  if (std::abs(b) < 0.001) {
    return a;
  }
  return (gcd(b, a - std::floor(a / b) * b));
}

double find_gcd_in_vector(const std::vector<double>& numbers) {
  double result = numbers[0];
  for (double number : numbers) {
    result = gcd(number, result);
    if (result == 1.) {
      return 1.;
    }
  }
  return result;
}

class Calculator {
public:
  static std::vector<std::string> solveSystemOfEquations(const Matrix& A, const Matrix& B) {
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
    for (int i = C.getN() - 1; i >= 0; i--) {
      std::string str;
      double gcd_value = find_gcd_in_vector(C[i]);
      for (std::size_t j = 0; j < C.getM() - 1; j++) {
        if (C[i][j] != 0) {
          str += std::to_string(C[i][j] / gcd_value) + " * x" + std::to_string(j + 1) + " +";
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
};
} // namespace linalg
