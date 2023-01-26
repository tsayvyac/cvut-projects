#include <iomanip>
#include <mutex>
#include "matrix_class.hpp"

matrix_class::matrix_class(const std::vector<std::vector<long double>> &m) :
    matrix(m),
    dimension(m.size()),
    determinant(1),
    sign(1)
    {};

void matrix_class::run(bool th_m) {
    std::mutex mut;
    if (th_m) {
        std::vector<std::thread> threads;
        size_t num_th = std::thread::hardware_concurrency();
        size_t block_size = dimension / num_th;
        size_t mod = dimension % num_th;
        for (size_t i = 0; i < num_th; i++) {
            threads.emplace_back(&matrix_class::calculate_determinant, this, std::ref(mut),
                                 i * block_size + std::min(i, mod), (i + 1) * block_size + std::min(i + 1, mod));
        }
        for (auto& th : threads) {
            th.join();
        }
    } else {
        calculate_determinant(std::ref(mut) ,0, dimension);
    }
    system("clear");
}

void matrix_class::calculate_determinant(std::mutex& m, size_t start, size_t end) {
    std::lock_guard<std::mutex> lock(m);
    for (size_t pivot_row = start; pivot_row < end; pivot_row++) {
        if (matrix[pivot_row][pivot_row] == 0) {
            check_pivots(pivot_row);
        }

        determinant *= matrix[pivot_row][pivot_row];
        for (size_t row = pivot_row + 1; row < dimension; row++) {
            long double scale = matrix[row][pivot_row] / matrix[pivot_row][pivot_row];
            for (size_t col = 0; col < dimension; col++) {
                matrix[row][col] -= scale * matrix[pivot_row][col];
            }
        }
    }
    determinant *= sign;
}

void matrix_class::check_pivots(const size_t& n) {
    for (size_t i = n + 1; i < dimension; i++) {
        if (matrix[i][n] != 0) {
            std::swap(matrix[n], matrix[i]);
            sign *= -1;
            break;
        }
    }
}

long double matrix_class::get_det() const {
    return determinant;
}

void matrix_class::matrix_from_file(std::fstream &file) {
    size_t for_check = 0;
    long double value = 0;
    std::string line;
    std::vector<long double> values;
    if (file.is_open()) {
        while (std::getline(file, line)) {
            std::stringstream ss(line);
            while (ss >> value) {
                values.push_back(value);
            }
            matrix.push_back(values);
            for_check += values.size();
            values.clear();
        }
        dimension = matrix.size();

        if (for_check / dimension != dimension) {
            throw std::invalid_argument("A non-square matrix is given!");
        }
    } else {
        throw std::invalid_argument("Unable to open this file!");
    }
}

void matrix_class::random_matrix(size_t dim) {
    std::ofstream file;
    static std::mt19937 mt{ std::random_device{}() };
    static std::uniform_real_distribution<> dist(-20, 20);
    std::vector<long double> values;
    double val = 0;
    dimension = dim;

    file.open("../matrices/generated.txt");
    for (size_t i = 0; i < dim; i++) {
        for (size_t j = 0; j < dim; j++) {
            val = ceil(dist(mt) * 100) / 100; // round double to 2 decimal places
            file << val << " ";
            values.push_back(val);
        }
        if (i != dim - 1) {
            file << "\r\n";
        }
        matrix.push_back(values);
        values.clear();
    }
    file.close();
}
