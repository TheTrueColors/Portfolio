import java.util.Arrays;

public class Board {
    private static Board instance;
    private char[][] positions = new char[3][3];
    private int rounds;

    private Board() {
        for(int i = 0; i < 3; ++i) {
            Arrays.fill(this.positions[i], ' ');
        }

        this.rounds = 0;
    }

    public static Board getInstance() {
        if (instance == null) {
            instance = new Board();
        }

        return instance;
    }

    public boolean checkWinner() {
        if (this.rounds == 9) {
            System.out.printf("Nessun vincitore\n");
            return false;
        } else {
            for(int i = 0; i < 3; ++i) {
                if (this.positions[i][0] != ' ' && this.positions[i][0] == this.positions[i][1] && this.positions[i][0] == this.positions[i][2]) {
                    System.out.printf("Il vincitore e': %c\n", this.positions[i][0]);
                    return true;
                }

                if (this.positions[0][i] != ' ' && this.positions[0][i] == this.positions[1][i] && this.positions[0][i] == this.positions[2][i]) {
                    System.out.printf("Il vincitore e': %c\n", this.positions[0][i]);
                    return true;
                }
            }

            if (this.positions[0][0] != ' ' && this.positions[0][0] == this.positions[1][1] && this.positions[0][0] == this.positions[2][2]) {
                System.out.printf("Il vincitore e': %c\n", this.positions[0][0]);
                return true;
            } else if (this.positions[0][2] != ' ' && this.positions[0][2] == this.positions[1][1] && this.positions[0][2] == this.positions[2][0]) {
                System.out.printf("Il vincitore e': %c\n", this.positions[0][2]);
                return true;
            } else {
                return true;
            }
        }
    }

    public boolean addMove(char player, int x, int y) {
        if (this.positions[x][y] == ' ') {
            this.positions[x][y] = player;
            ++this.rounds;
            return true;
        } else {
            System.out.printf("Mossa non valida!\n");
            return false;
        }

    }

    public int getRounds() {
        return this.rounds;
    }

    public void showBoard() {
        for(int i = 0; i < 3; ++i) {
            System.out.printf(" %-3c|  %-3c| %-3c\n", this.positions[i][0], this.positions[i][1], this.positions[i][2]);
            if (i < 2) {
                System.out.printf(" %-3c|  %-3c| %-3c\n", '-', '-', '-');
            }
        }

    }
}
