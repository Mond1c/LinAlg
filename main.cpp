#include <iostream>
#include "dynamic_matrix.h"

enum class MODES : int {
    MATRIX_CALCULATION = 0,
    SCALAR_MATRIX_MUL = 1,
    FIND_MATRIX_DETERMINANT = 2,
    INVERSE_MATRIX = 3
};

int main() {
    int mode;
    std::cin >> mode;
    switch (mode) {
        case (int)MODES::MATRIX_CALCULATION: {
            int operation;
            std::cin >> operation;
            int n1, m1;
            std::cin >> n1 >> m1;
            linalg::Matrix A(n1, m1);
            std::cin >> A;
            std::cin >> n1 >> m1;
            linalg::Matrix B(n1, m1);
            std::cin >> B;
            switch (operation) {
                case 0: {
                    std::cout << A + B << std::endl;
                    break;
                }
                case 1: {
                    std::cout << A - B << std::endl;
                    break;
                }
                case 2: {
                    std::cout << A * B << std::endl;
                    break;
                }
            }
            break;
        }
        case (int)MODES::SCALAR_MATRIX_MUL: {
            int n, m;
            std::cin >> n >> m;
            linalg::Matrix A(n, m);
            std::cin >> A;
            double scalar;
            std::cin >> scalar;
            std::cout << A * scalar << std::endl;
            break;
        }
        case (int)MODES::FIND_MATRIX_DETERMINANT: {
            int n, m;
            std::cin >> n >> m;
            linalg::Matrix A(n, m);
            std::cin >> A;
            std::cout << A.determinant() << std::endl;
            break;
        }
        case (int)MODES::INVERSE_MATRIX: {
            int n, m;
            std::cin >> n >> m;
            linalg::Matrix A(n, m);
            std::cin >> A;
            std::cout << A.inverse() << std::endl;
            break;
        }
    }
    return 0;
}