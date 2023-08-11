# Calculating the determinant of a square matrix

## Task

Implement a program to calculate the determinant of a square matrix using the Gaussian elimination method. It is expected that
this program will be able to handle 1000x1000 matrices in reasonable time. The application receives as input a square 
matrix and outputs the value of its determinant.

## Description

The calculator for calculating the determinant of a square matrix can take as input a matrix from a .txt file that will be placed in the /matrices folder, or
generate a matrix with random values of the specified dimension. The generated matrix is then stored in the /matrices folder with
named "generated". The /matrices folder already contains several matrices for testing.

## Implementation

First, this program converts the matrix to an upper triangular matrix using the Gaussian elimination method, and then calculates
the determinant by multiplying the main diagonal. The complexity of the algorithm is O(n^2). Multi-threaded implementation: the program splits the rows
matrix between threads (the amount depends on the hardware). Then these threads modify the original matrix into an upper triangular matrix.
And the determinant is calculated. The work crew model is used.

## Execution

The program is run from the command line.
Program settings parameters:

- -s - single-thread mode;
- -t - multi-thread mode;
- -f [name] (-s | -t) - gets a matrix from a .txt file named [name];
- -r [dim] (-s | -t) - generates a matrix of dimension [dim], and then saves the matrix in a .txt file named "generated";
- if you just type ./Determinant at the command line, the program will list the parameters it supports.

Examples of execution:

```bash
./Determinant
```

```bash
./Determinant -f matrix50-1 -s
```

```bash
./Determinant -r 20 -t
```
