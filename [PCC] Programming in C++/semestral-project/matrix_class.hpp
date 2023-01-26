#ifndef DETERMINANT_MATRIX_CLASS_HPP
#define DETERMINANT_MATRIX_CLASS_HPP

#include <sstream>
#include <random>
#include <iostream>
#include <vector>
#include <mutex>
#include <fstream>
#include <thread>

class matrix_class {
    size_t dimension = 0;
    std::vector<std::vector<long double>> matrix;
    long double determinant = 1;
    int sign = 1;

    void check_pivots(const size_t& row);

    void calculate_determinant(std::mutex& m, size_t start, size_t end);

public:

    explicit matrix_class(const std::vector<std::vector<long double>>& m);

    matrix_class() = default;

    ~matrix_class() = default;

    void run(bool th_m);

    void matrix_from_file(std::fstream &file);

    void random_matrix(size_t d);

    long double get_det() const;

};

#endif