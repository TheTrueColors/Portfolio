#ifndef BOARD_H
#define BOARD_H

class Board
{
private:
    static Board *instance; 
    char positions[3][3];
    int rounds;


    Board();

    // Prevent copying and assignment
    Board(const Board &) = delete;
    Board &operator=(const Board &) = delete;

public:
    
    bool checkWinner();

    static Board *getInstance();

    bool addMove(char player, int x, int y);

    int getRound();


    void showBoard();
};

#endif // BOARD_H