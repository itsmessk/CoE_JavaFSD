class MatrixMultiplier {
    static class Worker implements Runnable {
        int[][] A, B, C;
        int row;
        Worker(int[][] A, int[][] B, int[][] C, int row) {
            this.A = A; this.B = B; this.C = C; this.row = row;
        }
        public void run() {
            for (int j = 0; j < B[0].length; j++)
                for (int k = 0; k < A[0].length; k++)
                    C[row][j] += A[row][k] * B[k][j];
        }
    }
    
    public static int[][] multiplyMatrices(int[][] A, int[][] B) throws InterruptedException {
        int[][] C = new int[A.length][B[0].length];
        Thread[] threads = new Thread[A.length];
        for (int i = 0; i < A.length; i++) {
            threads[i] = new Thread(new Worker(A, B, C, i));
            threads[i].start();
        }
        for (Thread t : threads) t.join();
        return C;
    }
}