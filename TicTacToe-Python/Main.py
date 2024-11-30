from Board import Board
import os
import platform

def pulisci_schermo():
    # Controlla il sistema operativo
    if platform.system() == "Windows":
        os.system("cls")  # Comando per Windows
    else:
        os.system("clear")

def main():
    gameBoard = Board()
    x = 0
    y = 0
    win = False
    correctMove = False

    gameBoard.showBoard()

    for i in range(9):

        while correctMove!= True:
            print("Giocatore X inserisci la tua mossa (coordinate della cella : 0 0")
            x, y = map(int, input().split())
            correctMove = gameBoard.addMove('X',x,y)

        correctMove = False
        gameBoard.showBoard()
        print("\n")

        if gameBoard.getRounds()>=5:
            pulisci_schermo()
            win = gameBoard.checkWinner()
            if win:
                break


        while correctMove!= True:
            print("Giocatore O inserisci la tua mossa (coordinate della cella : 0 0")
            x, y = map(int, input().split())
            correctMove=gameBoard.addMove('O', x, y)

        correctMove = False
        gameBoard.showBoard()
        print("\n")

        if gameBoard.getRounds() >= 5:
            pulisci_schermo()
            win = gameBoard.checkWinner()
            if win:
                break


        pulisci_schermo()

if __name__ == "__main__":
    main()



