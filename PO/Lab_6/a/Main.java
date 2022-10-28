
import mpi.*;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws MPIException {
        MPI.Init(args);
        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();
        int n = 0;
        int m = 0;
        int[][] A = null;
        int[][] B = null;
        
        if (rank == 0) {
            Scanner in = new Scanner(System.in);
            System.out.println("Enter the number of rows in the first matrix");
            n = in.nextInt();
            System.out.println("Enter the number of columns in the first matrix");
            m = in.nextInt();
            A = new int[n][m];
            B = new int[m][n];
            Random rand = new Random();
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    A[i][j] = rand.nextInt(10);
                }
            }
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    B[i][j] = rand.nextInt(10);
                }
            }
        }
        MPI.COMM_WORLD.Bcast(A, 0, n * m, MPI.INT, 0);
        MPI.COMM_WORLD.Bcast(B, 0, n * m, MPI.INT, 0);
        MPI.COMM_WORLD.Bcast(n, 0, 1, MPI.INT, 0);
        MPI.COMM_WORLD.Bcast(m, 0, 1, MPI.INT, 0);
        int[][] C = new int[n][n];
        
        long start = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < m; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("Sequential time: " + (end - start));

        start = System.currentTimeMillis();
        int[][] C1 = new int[n][n];

        int[] A1 = new int[n * m];
        int[] B1 = new int[n * m];
        int[] C2 = new int[n * n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                A1[i * m + j] = A[i][j];
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                B1[i * n + j] = B[i][j];
            }
        }

        int[] A2 = new int[n * m / size];
        int[] B2 = new int[n * m / size];
        int[] C3 = new int[n * n / size];

        MPI.COMM_WORLD.Scatter(A1, 0, n * m / size, MPI.INT, A2, 0, n * m / size, MPI.INT, 0);
        MPI.COMM_WORLD.Scatter(B1, 0, n * m / size, MPI.INT, B2, 0, n * m / size, MPI.INT, 0);

        for (int i = 0; i < n / size; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < m; k++) {
                    C3[i * n + j] += A2[i * m + k] * B2[k * n + j];
                }
            }
        }

        MPI.COMM_WORLD.Gather(C3, 0, n * n / size, MPI.INT, C2, 0, n * n / size, MPI.INT, 0);

        if (rank == 0) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    C1[i][j] = C2[i * n + j];
                }
            }
        }
        end = System.currentTimeMillis();
        System.out.println("Parallel time: " + (end - start));

        start = System.currentTimeMillis();
        int[][] C4 = new int[n][n];

        int[] A3 = new int[n * m];
        int[] B3 = new int[n * m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                A3[i * m + j] = A[i][j];
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                B3[i * n + j] = B[i][j];
            }
        }

        int[] A4 = new int[n * m / size];
        int[] B4 = new int[n * m / size];
        int[] C5 = new int[n * n / size];

        MPI.COMM_WORLD.Scatter(A3, 0, n * m / size, MPI.INT, A4, 0, n * m / size, MPI.INT, 0);
        MPI.COMM_WORLD.Scatter(B3, 0, n * m / size, MPI.INT, B4, 0, n * m / size, MPI.INT, 0);

        int[] A5 = new int[n * m / size];
        int[] B5 = new int[n * m / size];

        for (int i = 0; i < n / size; i++) {
            for (int j = 0; j < m; j++) {
                A5[i * m + j] = A4[i * m + j];
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n / size; j++) {
                B5[i * n / size + j] = B4[i * n + j];
            }
        }

        for (int i = 0; i < n / size; i++) {
            for (int j = 0; j < n / size; j++) {
                for (int k = 0; k < m; k++) {
                    C5[i * n / size + j] += A5[i * m + k] * B5[k * n / size + j];
                }
            }
        }

        MPI.COMM_WORLD.Gather(C5, 0, n * n / size, MPI.INT, C2, 0, n * n / size, MPI.INT, 0);

        if (rank == 0) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    C4[i][j] = C2[i * n + j];
                }
            }
        }
        end = System.currentTimeMillis();
        System.out.println("Parallel time: " + (end - start));

        MPI.Finalize();
    }
}
