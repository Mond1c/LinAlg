cmake_minimum_required(VERSION 3.20)
project(LinAlg)

set(CMAKE_CXX_STANDARD 20)


add_executable(linalg_tests static_matrix.h dynamic_matrix.h calculator.h calculator.cpp)


