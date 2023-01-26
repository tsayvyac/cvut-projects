cmake_minimum_required(VERSION 3.25)
project(Determinant)

set(CMAKE_CXX_STANDARD 14)
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -pthread")

add_executable(Determinant main.cpp matrix_class.cpp matrix_class.hpp)
