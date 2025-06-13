package com.typingtest.unjeugenial;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;

public class JeuGymkhana extends Application {

    private static final int TAILLE_GRILLE = 9
            ; // Nombre impair positif
    private boolean tourNoir = true; // Variable pour suivre le tour actuel

    // Méthode pour générer automatiquement la matrice initiale
    private String[][] genererMatriceInitiale(int taille) {
        if (taille <= 0 || taille % 2 == 0) {
            throw new IllegalArgumentException("La taille doit être un nombre impair et positif");
        }

        String[][] matrice = new String[taille][taille];

        for (int y = 0; y < taille; y++) {
            for (int x = 0; x < taille; x++) {
                if (y % 2 == 0) {
                    if (x % 2 == 0) {
                        matrice[y][x] = ".";
                    } else {
                        matrice[y][x] = "Pn";
                    }
                } else {
                    if (x % 2 == 0) {
                        matrice[y][x] = "Pr";
                    } else {
                        matrice[y][x] = ".";
                    }
                }
            }
        }

        return matrice;
    }

    @Override
    public void start(Stage stage) {
        Pane root = new Pane();

        // Obtenir les dimensions de l'écran
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        // Calculer la taille de la fenêtre à 70% de la taille de l'écran
        double tailleFenetre = Math.min(screenWidth, screenHeight) * 0.7;
        double tailleCellule = tailleFenetre / TAILLE_GRILLE;

        String[][] matrice = genererMatriceInitiale(TAILLE_GRILLE);

        for (int y = 0; y < matrice.length; y++) {
            for (int x = 0; x < matrice[y].length; x++) {
                String typeFigure = matrice[y][x];
                Figures figure = new Figures(typeFigure, x, y, tailleCellule);
                root.getChildren().add(figure.getForme());

                if (typeFigure.equals(".")) {
                    boolean surBord = (x == 0 || x == TAILLE_GRILLE - 1 || y == 0 || y == TAILLE_GRILLE - 1);

                    if (!surBord) {
                        final int posX = x;
                        final int posY = y;

                        figure.getForme().setOnMouseClicked(event -> {
                            System.out.println("Case cliquée à la position (" + posX + ", " + posY + ")");

                            String newForm;
                            if (tourNoir) {
                                if (posY % 2 == 0) {
                                    newForm = "Ahn";
                                } else {
                                    newForm = "Avn";
                                }
                            } else {
                                if (posY % 2 != 0) {
                                    newForm = "Ahr";
                                } else {
                                    newForm = "Avr";
                                }
                            }

                            // Remplacer l'ancienne forme par une nouvelle
                            Figures newFigure = new Figures(newForm, posX, posY, tailleCellule);
                            root.getChildren().remove(figure.getForme());
                            root.getChildren().add(newFigure.getForme());

                            // Changer de tour et mettre à jour la couleur de fond
                            tourNoir = !tourNoir;
                            root.setStyle("-fx-background-color: " + (tourNoir ? "#d1d1d1" : "#ffdeeb") + ";");
                        });

                        figure.getForme().setOnMouseEntered(event -> {
                            stage.getScene().setCursor(javafx.scene.Cursor.HAND);
                        });

                        figure.getForme().setOnMouseExited(event -> {
                            stage.getScene().setCursor(javafx.scene.Cursor.DEFAULT);
                        });
                    }
                }
            }
        }

        // Définir la couleur de fond initiale
        root.setStyle("-fx-background-color: #d1d1d1;");

        Scene scene = new Scene(root, tailleFenetre, tailleFenetre);
        stage.setTitle("Jeu Gymkhana (" + TAILLE_GRILLE + "x" + TAILLE_GRILLE + ")");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
