import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * La clase principal Main
 */
public class Main {
    /*
     * El Loggger
     */
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    /**
     * El Main
     *
     * @param args
     * @throws IOException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException {

        /*
          Creacion del Reloj
         */
        final StopWatch stopWatch = StopWatch.createStarted();

        /*
          Matriz que almacenara el sudoku
         */
        int[][] sudoku = {{0, 0, 3, 0, 2, 0, 6, 0, 0},
                {9, 0, 0, 3, 0, 5, 0, 0, 1},
                {0, 0, 1, 8, 0, 6, 4, 0, 0},
                {0, 0, 8, 1, 0, 2, 9, 0, 0},
                {7, 0, 0, 0, 0, 0, 0, 0, 8},
                {0, 0, 6, 7, 0, 8, 2, 0, 0},
                {0, 0, 2, 6, 0, 9, 5, 0, 0},
                {8, 0, 0, 2, 0, 3, 0, 0, 9},
                {0, 0, 5, 0, 1, 0, 3, 0, 0}};

        /*
          Creacion del tablero de Sudoku a resolver.
         */
        Sudoku miSudoku = new Sudoku(sudoku);

        /*
          Resolviendo el sudoku.
         */
        miSudoku.solve();

        /*
          Imprime el tiempo que demoro en realizar la tarea.
         */
        log.debug("Done in {}.", stopWatch);

        // Imprimiendo la solucion del sudoku
        miSudoku.display();
    }

    /**
     * Metodo que lee los datos del texto sudoku y retorna una matriz de 9 x 9 si es que se quiere importar.
     */
    public static int[][] lectura() throws FileNotFoundException {
        int[][] matriz = new int[9][9];
        Scanner input = new Scanner(new File("\\sudoku.txt")); // Aqui tuve problemas

        for (int i = 0; i < 9; i++) {
            String line = input.nextLine();
            StringTokenizer st = new StringTokenizer(line, ",");
            for (int j = 0; j < 9; j++) {
                matriz[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        return matriz;
    }
}