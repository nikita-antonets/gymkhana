package com.typingtest.unjeugenial;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class Figures {
    private Node forme;
    private double tailleCellule;

    public Figures(String nom, int x, int y, double tailleCellule) {
        this.tailleCellule = tailleCellule;
        this.forme = creerForme(nom, x, y);
    }

    private Node creerForme(String nom, int x, int y) {
        switch (nom) {
            case "Pn":
                Circle cercleNoir = new Circle(tailleCellule / 2, Color.BLACK);
                cercleNoir.setCenterX(tailleCellule * x + tailleCellule / 2);
                cercleNoir.setCenterY(tailleCellule * y + tailleCellule / 2);
                return cercleNoir;

            case "Pr":
                Circle cercleRouge = new Circle(tailleCellule / 2, Color.RED);
                cercleRouge.setCenterX(tailleCellule * x + tailleCellule / 2);
                cercleRouge.setCenterY(tailleCellule * y + tailleCellule / 2);
                return cercleRouge;

            case ".":
                Rectangle espaceVide = new Rectangle(tailleCellule, tailleCellule, Color.TRANSPARENT);
                espaceVide.setX(x * tailleCellule);
                espaceVide.setY(y * tailleCellule);
//                espaceVide.setStroke(Color.LIGHTGRAY);
//                espaceVide.setStrokeWidth(1);
                return espaceVide;

            case "Ahr":
                Line arreteRougeH = new Line(
                        x * tailleCellule,
                        y * tailleCellule + tailleCellule / 2,
                        (x + 1) * tailleCellule,
                        y * tailleCellule + tailleCellule / 2
                );
                arreteRougeH.setStroke(Color.RED);
                arreteRougeH.setStrokeWidth(5);
                return arreteRougeH;

            case "Avr":
                Line arreteRougeV = new Line(
                        x * tailleCellule + tailleCellule / 2,
                        y * tailleCellule,
                        x * tailleCellule + tailleCellule / 2,
                        (y + 1) * tailleCellule
                );
                arreteRougeV.setStroke(Color.RED);
                arreteRougeV.setStrokeWidth(5);
                return arreteRougeV;

            case "Ahn":
                Line arreteNoireH = new Line(
                        x * tailleCellule,
                        y * tailleCellule + tailleCellule / 2,
                        (x + 1) * tailleCellule,
                        y * tailleCellule + tailleCellule / 2
                );
                arreteNoireH.setStroke(Color.BLACK);
                arreteNoireH.setStrokeWidth(5);
                return arreteNoireH;

            case "Avn":
                Line arreteNoireV = new Line(
                        x * tailleCellule + tailleCellule / 2,
                        y * tailleCellule,
                        x * tailleCellule + tailleCellule / 2,
                        (y + 1) * tailleCellule
                );
                arreteNoireV.setStroke(Color.BLACK);
                arreteNoireV.setStrokeWidth(5);
                return arreteNoireV;

            default:
                throw new IllegalArgumentException("Type de figure inconnu : " + nom);
        }
    }

    public Node getForme() {
        return forme;
    }

    public void changerPosition(int nouveauX, int nouveauY) {
        if (forme instanceof Circle) {
            Circle cercle = (Circle) forme;
            cercle.setCenterX(tailleCellule * nouveauX + tailleCellule / 2);
            cercle.setCenterY(tailleCellule * nouveauY + tailleCellule / 2);
        } else if (forme instanceof Rectangle) {
            Rectangle rect = (Rectangle) forme;
            rect.setX(nouveauX * tailleCellule);
            rect.setY(nouveauY * tailleCellule);
        }
    }

    public void changerCouleur(Color nouvelleCouleur) {
        if (forme instanceof Circle) {
            ((Circle) forme).setFill(nouvelleCouleur);
        } else if (forme instanceof Line) {
            ((Line) forme).setStroke(nouvelleCouleur);
        }
    }
}
