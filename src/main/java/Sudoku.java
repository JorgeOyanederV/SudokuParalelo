import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * Esta clase nos permite resolver tableros de sudoku mediante backtracking
 */
public class Sudoku {

    /**
     * Variable que identificara cuando una casilla este vacia o no assignada.
     */
    private static final int UNASSIGNED = 0;

    /**
     * Variable que almacenara el tablero
     */
    private final int[][] sudoku;

    /**
     * El "Executer"
     */
    ExecutorService executorService;


    /**
     * Constructor de la clase.
     *
     * @param sudoku tablero
     */
    public Sudoku(int[][] sudoku) {
        this.sudoku = sudoku;
    }

    /**
     * Este metodo indica si el numero es permitido en la casilla en la posicion fila (row) y columna (col), ademas de su sector (box)
     * retornando true si el numero se puede ocupar esta casilla de lo contrario retornara false.
     */
    private boolean isAllowed(int row, int col, int number) throws InterruptedException {
        executorService = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(8), new ThreadPoolExecutor.CallerRunsPolicy());
        boolean allowed;
        System.out.println("is allow");
        executorService.submit(new Task(number, row, this.sudoku));
        executorService.submit(new Task(number, this.sudoku, col));
        executorService.submit(new Task(number, row, col, this.sudoku));
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.HOURS);
        allowed = Task.IsAllowed();
        Task.SetAllowed();
        return allowed;
    }

    /**
     * Este metodo resuelve el puzzle mediante backtracking
     */
    public boolean solve() throws InterruptedException {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (sudoku[row][col] == UNASSIGNED) {
                    for (int number = 1; number <= 9; number++) {
                        System.out.println(number);
                        if (isAllowed(row, col, number)) {
                            sudoku[row][col] = number;
                            if (solve()) {
                                return true;
                            } else {
                                sudoku[row][col] = UNASSIGNED;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Este metodo imprime la solucion del puzzle.
     */
    public void display() {
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0 && i != 0) {
                System.out.println("----------------------------------\n");
            }
            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0 && j != 0) {
                    System.out.print(" | ");
                }
                System.out.print(" " + sudoku[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("\n\n__________________________________________\n\n");
    }

    /**
     * Class to evaluate number
     */
    private static class Task implements Runnable {
        private final int[][] sudoku;
        private int row;
        private int col;
        private final int number;
        private final static AtomicBoolean isAllowed = new AtomicBoolean(true);

        /**
         * @param number numero a testear
         * @param row    fila a testear
         * @param sudoku tablero a testear
         */
        public Task(int number, int row, int[][] sudoku) {
            this.number = number;
            this.row = row;
            this.sudoku = sudoku;
            tarea1();
        }

        /**
         * @param number numero a testear
         * @param sudoku tablero a testear
         * @param col    columna a testear
         */
        public Task(int number, int[][] sudoku, int col) {
            this.number = number;
            this.col = col;
            this.sudoku = sudoku;
            tarea2();
        }

        /**
         * @param number numero a testear
         * @param row    fila a testear
         * @param col    columna a testear
         * @param sudoku tablero a testear
         */
        public Task(int number, int row, int col, int[][] sudoku) {
            this.number = number;
            this.row = row;
            this.col = col;
            this.sudoku = sudoku;
            tarea3();
        }

        /**
         * Verifica que el numero no este en la fila
         */
        public void tarea1() {
            while (isAllowed.get()) {
                if (containsInRow(row, number)) {
                    isAllowed.set(false);
                }
                return;
            }
        }

        /**
         * Verifica que el numero no este  en la columna
         */
        public void tarea2() {
            while (isAllowed.get()) {
                if (containsInCol(col, number)) {
                    isAllowed.set(false);
                }
                return;
            }

        }

        /**
         * Verifica que el numero no este en el cuadrante
         */
        public void tarea3() {
            while (isAllowed.get()) {
                if (containsInBox(row, col, number)) {
                    isAllowed.set(false);
                }
                return;
            }
        }

        /**
         * Este metodo verifica que en la fila (row) existe el valor (number) indicado, retornando true si existe y false si no esta.
         */
        public boolean containsInRow(int row, int number) {
            for (int i = 0; i < 9; i++) {
                if (sudoku[row][i] == number) {
                    return true;
                }
            }
            return false;
        }

        /**
         * Evalua si una numero esta en la columna
         *
         * @param col    columna a evaluar
         * @param number numero a evaluar
         * @return true si el numero esta en la columna y false de lo contrario.
         */
        private boolean containsInCol(int col, int number) {
            for (int i = 0; i < 9; i++) {
                if (sudoku[i][col] == number) {
                    return true;
                }
            }
            return false;
        }

        /**
         * @param row fila evaluar
         * @param col columna a evaluar
         * @param number numero a evaluar
         * @return true si el numero esta consenido en la caja de lo contrario false.
         */
        private boolean containsInBox(int row, int col, int number) {
            int r = row - row % 3;
            int c = col - col % 3;
            for (int i = r; i < r + 3; i++) {
                for (int j = c; j < c + 3; j++) {
                    if (sudoku[i][j] == number) {
                        return true;
                    }
                }
            }
            return false;
        }

        /**
         * @return true si el numero esta permitido en la fila, columna y caja y false de lo contrario.
         */
        public static boolean IsAllowed() {
            return isAllowed.get();
        }

        /**
         * Cambia el valor de la variable atomica a true
         */
        public static void SetAllowed() {
            isAllowed.set(true);
        }

        @Override
        public void run() {

        }
    }
}
