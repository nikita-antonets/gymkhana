package com.typingtest.unjeugenial;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;

public class JeuGymkhana extends Application {
    private static final int TAILLE_GRILLE = 9; // Doit être impair
    private boolean tourNoir = true;
    private final Logique logique = new Logique(TAILLE_GRILLE);
    private final String[][] matrice = genererMatriceInitiale();

    @Override
    public void start(Stage stage) {
        Pane root = new Pane();
        double tailleCellule = initialiserPlateau(root);

        Scene scene = new Scene(root, tailleCellule * TAILLE_GRILLE, tailleCellule * TAILLE_GRILLE);
        stage.setTitle("Jeu Gymkhana " + TAILLE_GRILLE + "x" + TAILLE_GRILLE);
        stage.setScene(scene);
        stage.show();
    }

    private double initialiserPlateau(Pane root) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double tailleCellule = Math.min(screenBounds.getWidth(), screenBounds.getHeight()) * 0.7 / TAILLE_GRILLE;

        for (int y = 0; y < TAILLE_GRILLE; y++) {
            for (int x = 0; x < TAILLE_GRILLE; x++) {
                Figures figure = new Figures(matrice[y][x], x, y, tailleCellule);
                root.getChildren().add(figure.getForme());

                if (matrice[y][x].equals(".")) {
                    configurerCaseCliquable(root, figure, x, y, tailleCellule);
                }
            }
        }
        return tailleCellule;
    }

    private void configurerCaseCliquable(Pane root, Figures figure, int x, int y, double tailleCellule) {
        figure.getForme().setOnMouseClicked(event -> {
            String newForm = tourNoir ?
                    (y % 2 == 0 ? "Ahn" : "Avn") :
                    (y % 2 != 0 ? "Ahr" : "Avr");

            // Mise à jour de la matrice
            matrice[y][x] = newForm;

            // Mise à jour de l'affichage
            Figures nouvelleFigure = new Figures(newForm, x, y, tailleCellule);
            root.getChildren().remove(figure.getForme());
            root.getChildren().add(nouvelleFigure.getForme());

            // Vérification victoire
            int[][] matriceNumerique = convertirMatrice();
            String resultat = logique.verifierGagnant(matriceNumerique);

            if (!resultat.contains("encore")) {
                new Alert(Alert.AlertType.INFORMATION, resultat).show();
            }

            tourNoir = !tourNoir;
        });
    }

    private int[][] convertirMatrice() {
        int[][] matriceNumerique = new int[TAILLE_GRILLE][TAILLE_GRILLE];
        for (int y = 0; y < TAILLE_GRILLE; y++) {
            for (int x = 0; x < TAILLE_GRILLE; x++) {
                switch (matrice[y][x]) {
                    case "Pn", "Ahn", "Avn" -> matriceNumerique[y][x] = 1;  // Noir
                    case "Pr", "Ahr", "Avr" -> matriceNumerique[y][x] = 2;  // Rouge
                    default -> matriceNumerique[y][x] = 0;                 // Vide
                }
            }
        }
        return matriceNumerique;
    }

    private String[][] genererMatriceInitiale() {
        String[][] matrice = new String[TAILLE_GRILLE][TAILLE_GRILLE];
        for (int y = 0; y < TAILLE_GRILLE; y++) {
            for (int x = 0; x < TAILLE_GRILLE; x++) {
                if (y % 2 == 0) { // Lignes paires (0-based)
                    matrice[y][x] = (x % 2 == 0) ? "." : "Pn";
                } else { // Lignes impaires
                    matrice[y][x] = (x % 2 == 0) ? "Pr" : ".";
                }
            }
        }
        return matrice;
    }

    public static void main(String[] args) {
        launch(args);
    }
}