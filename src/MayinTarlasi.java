import java.util.Scanner;

public class MayinTarlasi {
    private char[][] board;
    private char[][] minePositions;
    private int size;
    private int remainingTiles;

    public MayinTarlasi(char[][] minePositions) {
        this.size = minePositions.length;
        this.board = new char[size][size];
        this.minePositions = minePositions;
        this.remainingTiles = size * size;

        initializeBoard();
    }

    public void play() {
        System.out.println("Mayın Tarlası Oyuna Hoşgeldiniz !");
        printBoard();

        Scanner scanner = new Scanner(System.in);
        while (remainingTiles > 0) {
            System.out.print("Satır Giriniz: ");
            int row = scanner.nextInt();

            System.out.print("Sütun Giriniz: ");
            int col = scanner.nextInt();

            if (!isValidMove(row, col)) {
                System.out.println("Geçersiz bir hamle yaptınız. Lütfen tekrar deneyin.");
                continue;
            }

            if (minePositions[row][col] == '*') {
                revealBoard();
                System.out.println("Game Over!!");
                break;
            }

            int numAdjacentMines = countAdjacentMines(row, col);
            board[row][col] = (char) (numAdjacentMines + '0');
            remainingTiles--;

            printBoard();

            if (remainingTiles == countMines()) {
                System.out.println("Oyunu Kazandınız!");
                break;
            }
        }
    }

    private void initializeBoard() {
        for (char[] row : board) {
            for (int j = 0; j < row.length; j++) {
                row[j] = '-';
            }
        }
    }

    private void printBoard() {
        for (char[] row : board) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
        System.out.println("===========================");
    }

    private int countAdjacentMines(int row, int col) {
        int count = 0;

        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (isValidPosition(i, j) && minePositions[i][j] == '*') {
                    count++;
                }
            }
        }

        return count;
    }

    private boolean isValidMove(int row, int col) {
        return isValidPosition(row, col) && board[row][col] == '-';
    }

    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < size && col >= 0 && col < size;
    }

    private int countMines() {
        int count = 0;

        for (char[] row : minePositions) {
            for (char cell : row) {
                if (cell == '*') {
                    count++;
                }
            }
        }

        return count;
    }

    private void revealBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (minePositions[i][j] == '*') {
                    board[i][j] = '*';
                } else {
                    int numAdjacentMines = countAdjacentMines(i, j);
                    board[i][j] = (char) (numAdjacentMines + '0');
                }
            }
        }
    }

    public static void main(String[] args) {
        char[][] minePositions = {
                {'-', '-', '-'},
                {'-', '*', '-'},
                {'-', '*', '-'}
        };

        MayinTarlasi game = new MayinTarlasi(minePositions);
        game.play();
    }
}
