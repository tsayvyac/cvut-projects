#include <iostream>
#include <chrono>
#include <cstring>
#include "matrix_class.hpp"

#define COLOR_RED   "\x1B[91m"
#define COLOR_GREEN "\x1B[92m"
#define RESET "\x1B[m" // reset terminal colors

template <typename TimePoint>
std::chrono::milliseconds to_ms(TimePoint tp) {
    return std::chrono::duration_cast<std::chrono::milliseconds>(tp);
}

void print_help() {
    std::cout << '\n' << "Hello! This is program for calculate the determinant of square matrix." << "\n\n";
    std::cout << '\n';
    std::cout << "Argument list: " << "\n\n";
    std::cout << COLOR_GREEN  << "                            -t multithreading mode, -s sequence mode." << RESET << '\n';
    std::cout << "-f [name] [-t | -s]    |    Reads a matrix from a .txt file. Where [name] is name of file." << '\n';
    std::cout << "                            .txt file with the matrix must be placed in the /matrices folder!" << '\n';
    std::cout << "-r [dim]  [-t | -s]    |    Generate a random matrix with dimension [dim] and calculate the determinant." << '\n';
    std::cout << "                            (will be contain doubles with 2 digits after decimal point)" << '\n';
    std::cout << "                            The generated matrix will be saved in the /matrices folder named generated.txt." << '\n';
    std::cout << '\n';
}

matrix_class read_matrix(const std::string& filename) {
    matrix_class v;
    std::fstream file;
    file.open("../matrices/" + filename + ".txt");
    v.matrix_from_file(file);
    return v;
}

matrix_class random_matrix(size_t d) {
    matrix_class v;
    v.random_matrix(d);
    return v;
}

int main(int argc, char *argv[]) {
    system("clear");
    if (argc == 1 || strcmp(argv[1], "-h") == 0 || strcmp(argv[1], "-help") == 0) {
        print_help();
        return 0;
    }

    if (argc != 4) {
        std::cout << COLOR_RED << "Please, select a running mode!" << '\n';
        return 1;
    }

    bool th_m;
    matrix_class m;

    if (strcmp(argv[1], "-f") == 0) {
        m = read_matrix(argv[2]);
    } else if (strcmp(argv[1], "-r") == 0) {
        try {
            int d = std::stoi(argv[2]);
            if (d < 0) {
                std::cout << COLOR_RED << "This argument must be positive! Your input is: " << argv[2] <<'\n';
                return 1;
            }
            m = random_matrix(d);
        } catch (const std::invalid_argument& ex) {
            std::cout << COLOR_RED << "This argument must be a number! Your input is: " << argv[2] << '\n';
            return 1;
        }
    } else {
        system("clear");
        std::cout << COLOR_RED << "Argument " << argv[1] << " does not exit!" << '\n';
        return 1;
    }

    if (strcmp(argv[3], "-t") == 0) {
        th_m = true;
    } else if (strcmp(argv[3], "-s") == 0) {
        th_m = false;
    } else {
        std::cout << COLOR_RED << "You must specify running mode! [-t | -s]" << '\n';
        return 1;
    }

    std::cout << "Calculating the determinant can take several seconds..." << '\n';

    auto start = std::chrono::high_resolution_clock::now();
    m.run(th_m);
    std::cout << '\n' << "Det = " << m.get_det() << '\n';
    auto end = std::chrono::high_resolution_clock::now();

    std::cout << "Needed " << COLOR_GREEN << to_ms(end - start).count() << " ms " << RESET "to finish." << "\n\n";
    return 0;
}
