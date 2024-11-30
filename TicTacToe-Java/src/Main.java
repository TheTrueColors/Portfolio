import java.util.Scanner;

public class Main {
    public Main() {
    }

    public static void clearConsole() {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                (new ProcessBuilder(new String[]{"cmd", "/c", "cls"})).inheritIO().start().waitFor();
            } else {
                System.out.print("clear");
                System.out.flush();
            }
        } catch (Exception e) {
            System.err.println("Errore durante la pulizia della console: " + e.getMessage());
        }

    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Board gameBoard = Board.getInstance();
        boolean win = false;
        boolean correctMove = false;
        int x = 0, y = 0;

        for(int i = 0; i < 9; ++i) {
            gameBoard.showBoard();
            System.out.printf("\n");

            while(!correctMove)
            {
                System.out.printf("Giocatore X inserisci la tua mossa (coordinate della cella esempio: 0 0):");
                x = scanner.nextInt();
                y = scanner.nextInt();
                correctMove= gameBoard.addMove('X', x, y);
            }

            correctMove = false;

            if (gameBoard.getRounds() >= 5) {
                win = gameBoard.checkWinner();
            }

            if (win) {
                gameBoard.showBoard();
                break;
            }

            clearConsole();
            gameBoard.showBoard();
            System.out.printf("\n");

            while(!correctMove) {
                System.out.printf("Giocatore O inserisci la tua mossa (coordinate della cella esempio: 0 0):");
                x = scanner.nextInt();
                y = scanner.nextInt();
                correctMove= gameBoard.addMove('O', x, y);
            }

            correctMove = false;

            if (gameBoard.getRounds() >= 5) {
                clearConsole();
                win = gameBoard.checkWinner();
            }

            if (win) {
                gameBoard.showBoard();
                System.out.printf("\n");
                break;
            }

            clearConsole();
        }

        scanner.close();
    }
}
