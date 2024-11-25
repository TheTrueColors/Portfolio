#include <iostream>
#include <stdlib.h>

#include "classes\board.h"

int main()
{
    Board *gameBoard = Board::getInstance();
    int x, y;
    bool win = 0;
    bool correctMove = false;

    for (int i = 0; i < 9; i++)
    {
        gameBoard->showBoard();
        printf("\n");

        while (!correctMove)
        {
            std::cout << "Giocatore X inserisci la tua mossa (coordinate della cella esempio: 0 0):";
            std::cin >> x >> y;
            correctMove = gameBoard->addMove('X', x, y);
        }

        correctMove = false;

        if (gameBoard->getRound() >= 5)
        {
            std::system("cls");
            win = gameBoard->checkWinner();
        }

        if (win == true)
        {
            gameBoard->showBoard();
            break;
        }

        std::system("cls");

        gameBoard->showBoard();
        printf("\n");

        while (!correctMove)
        {
            std::cout << "Giocatore O inserisci la tua mossa (coordinate della cella esempio: 0 0):";
            std::cin >> x >> y;
            correctMove = gameBoard->addMove('O', x, y);
        }

        correctMove = false;

        if (gameBoard->getRound() >= 5)
        {
            std::system("cls");
            win = gameBoard->checkWinner();
        }

        if (win == true)
        {
            gameBoard->showBoard();
            break;
        }

        std::system("cls");
    }

    std::system("pause");
    return 0;
}