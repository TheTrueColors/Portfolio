#include "board.h"
#include <iostream>
#include <stdlib.h>
#include <cstring>

Board *Board::instance = nullptr;

Board::Board()
{
    memset(positions, ' ', sizeof(positions));
    rounds = 0;
}

bool Board::checkWinner()
{

    if (rounds == 9)
    {
        printf("Nessun vincitore\n");
        return 0;
    }

    // Controllo righe e colonne
    for (int i = 0; i < 3; i++)
    {
        if (positions[i][0] != ' ' && positions[i][0] == positions[i][1] && positions[i][0] == positions[i][2])
        {
            printf("Il vincitore e': %c\n", positions[i][0]);
            return 1;
        }

        if (positions[0][i] != ' ' && positions[0][i] == positions[1][i] && positions[0][i] == positions[2][i])
        {
            printf("Il vincitore e': %c\n", positions[0][i]);
            return 1;
        }
    }

    // Controllo diagonali
    if (positions[0][0] != ' ' && positions[0][0] == positions[1][1] && positions[0][0] == positions[2][2])
    {
        printf("Il vincitore e': %c\n", positions[0][0]);
        return 1;
    }

    if (positions[0][2] != ' ' && positions[0][2] == positions[1][1] && positions[0][2] == positions[2][0])
    {
        printf("Il vincitore e': %c\n", positions[0][2]);
        return 1;
    }

    return 0;
}

Board *Board::getInstance()
{
    if (instance == nullptr)
        instance = new Board();

    return instance;
}

bool Board::addMove(char player, int x, int y)
{
    if (positions[x][y] == ' ')
    {
        positions[x][y] = player;
        rounds++;
        return true;
    }
    else
    {
        printf("Mossa non valida!\n");
        return false;
    }

return false;
}

int Board::getRound() { return rounds; }

void Board::showBoard()
{
    for (int i = 0; i < 3; i++)
    {
        printf(" %-3c|  %-3c| %-3c\n", positions[i][0], positions[i][1], positions[i][2]);
        if (i < 2)
            printf(" %-3c|  %-3c| %-3c\n",'-','-','-');
    }
}
