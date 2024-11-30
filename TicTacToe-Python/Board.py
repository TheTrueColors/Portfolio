import numpy as np

class Board:
    _instance = None

    def __new__(cls):
        if not cls._instance:
            cls._instance = super(Board, cls).__new__(cls)
            cls._instance.positions = np.full((3, 3), ' ')  # Matr. 3x3 con spazi vuoti
            cls._instance.rounds = 0  # Numero di mosse effettuate
        return cls._instance

    def addMove(self, player, x, y):
        if self.positions[x, y] == ' ':
            self.positions[x, y] = player
            self.rounds += 1
            return True
        else:
            print("Mossa non valida!")
            return False

    def showBoard(self):
        for i in range(3):  # Usa un contatore per accedere alle righe
            print(f" {self.positions[i, 0]:<3}|  {self.positions[i, 1]:<3}| {self.positions[i, 2]:<3}")
            if i < 2:  # Stampa una linea tra le righe, ma non dopo l'ultima riga
                print(f" {'-':<3}|  {'-':<3}| {'-':<3}")

    def getRounds(self):
        return self.rounds

    def checkWinner(self):
        if self.rounds == 9:
            print("Nessuno vincitore\n")
            return False

        # Controlla righe
        for i in range(3):
            if self.positions[i, 0] != ' ' and self.positions[i, 0] == self.positions[i, 1] == self.positions[i, 2]:
                print(f"Il vincitore è {self.positions[i, 0]} \n")
                return True

        # Controlla colonne
        for i in range(3):
            if self.positions[0, i] != ' ' and self.positions[0, i] == self.positions[1, i] == self.positions[2, i]:
                print(f"Il vincitore è {self.positions[0, i]} \n")
                return True

        # Controlla diagonali
        if self.positions[0, 0] != ' ' and self.positions[0, 0] == self.positions[1, 1] == self.positions[2, 2]:
            print(f"Il vincitore è {self.positions[0, 0]} \n")
            return True
        if self.positions[0, 2] != ' ' and self.positions[0, 2] == self.positions[1, 1] == self.positions[2, 0]:
            print(f"Il vincitore è {self.positions[0, 2]} \n")
            return True

        return False
