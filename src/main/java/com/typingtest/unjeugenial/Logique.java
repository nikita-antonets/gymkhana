package com.typingtest.unjeugenial;

public class Logique {
    private final int tailleGrille;

    public Logique(int tailleGrille) {
        this.tailleGrille = tailleGrille;
    }

    public String verifierGagnant(int[][] M) {
        if (aGagneNoir(M)) {
            return "Le joueur noir a gagné !";
        } else if (aGagneRouge(M)) {
            return "Le joueur rouge a gagné !";
        }
        return "Aucun joueur n'a encore gagné.";
    }

    public boolean aGagneNoir(int[][] M) {
        boolean[][] visite = new boolean[tailleGrille][tailleGrille];
        // Vérifier depuis le bord haut (ligne 0)
        for (int j = 0; j < tailleGrille; j++) {
            if (M[0][j] == 1 && parcoursConnexeNoir(M, visite, 0, j)) {
                return true;
            }
        }
        return false;
    }

    public boolean aGagneRouge(int[][] M) {
        boolean[][] visite = new boolean[tailleGrille][tailleGrille];
        // Vérifier depuis le bord gauche (colonne 0)
        for (int i = 0; i < tailleGrille; i++) {
            if (M[i][0] == 2 && parcoursConnexeRouge(M, visite, i, 0)) {
                return true;
            }
        }
        return false;
    }

    private boolean parcoursConnexeNoir(int[][] M, boolean[][] visite, int i, int j) {
        if (i < 0 || i >= tailleGrille || j < 0 || j >= tailleGrille || visite[i][j] || M[i][j] != 1) {
            return false;
        }

        visite[i][j] = true;

        // Victoire si on atteint le bas
        if (i == tailleGrille - 1) {
            return true;
        }

        // Directions prioritaires pour les noirs (verticales)
        int[][] directions = {{1,0},{-1,0},{0,1},{0,-1},{-1,1},{1,-1}};

        for (int[] dir : directions) {
            if (parcoursConnexeNoir(M, visite, i + dir[0], j + dir[1])) {
                return true;
            }
        }

        return false;
    }

    private boolean parcoursConnexeRouge(int[][] M, boolean[][] visite, int i, int j) {
        if (i < 0 || i >= tailleGrille || j < 0 || j >= tailleGrille || visite[i][j] || M[i][j] != 2) {
            return false;
        }

        visite[i][j] = true;

        // Victoire si on atteint la droite
        if (j == tailleGrille - 1) {
            return true;
        }

        // Directions prioritaires pour les rouges (horizontales)
        int[][] directions = {{0,1},{0,-1},{1,0},{-1,0},{1,-1},{-1,1}};

        for (int[] dir : directions) {
            if (parcoursConnexeRouge(M, visite, i + dir[0], j + dir[1])) {
                return true;
            }
        }

        return false;
    }
}