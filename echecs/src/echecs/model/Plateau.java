package echecs.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import echecs.model.piece.Cavalier;
import echecs.model.piece.Fou;
import echecs.model.piece.Piece.Couleur;
import echecs.model.piece.Piece;
import echecs.model.piece.Pion;
import echecs.model.piece.Reine;
import echecs.model.piece.Roi;
import echecs.model.piece.Tour;

/**
 * Classe représentant le plateu du jeu
 *
 */
@objid ("c5b604e3-a5e9-4e76-b46a-885038c52514")
public class Plateau implements Serializable {
	
    /**
     * Tableau de pièce représentant notre plateau 
     */
    @objid ("4eb1ed26-d532-4765-882c-cb51758abefe")
    private Piece[][] pieces = new Piece[8][8];

    /**
     * Référence du jeu
     */
    @objid ("b8219aec-d19e-4e6a-98fe-486308f8d6bc")
    private Jeu jeu;

    /**
     * Liste des pièces prises
     */
    @objid ("619c2aa3-377a-475c-a083-b30e42f1b938")
    private ArrayList<Piece> piecesPrises = new ArrayList<Piece>();

    /**
     * Constructeur
     * @param jeu	Référence du jeu
     */
    @objid ("ad76832c-c0de-4d03-902e-67f74c9f59fd")
    public Plateau(Jeu jeu) {
        this.jeu = jeu;
        
        this.setPiece(0, 0, new Tour(this, Couleur.BLANC, 0, 0));
        this.setPiece(1, 0, new Cavalier(this, Couleur.BLANC, 1, 0));
        this.setPiece(2, 0, new Fou(this, Couleur.BLANC, 2, 0));
        this.setPiece(3, 0, new Reine(this, Couleur.BLANC, 3, 0));
        this.setPiece(4, 0, new Roi(this, Couleur.BLANC, 3, 0));
        this.setPiece(5, 0, new Fou(this, Couleur.BLANC, 4, 0));
        this.setPiece(6, 0, new Cavalier(this, Couleur.BLANC, 4, 0));
        this.setPiece(7, 0, new Tour(this, Couleur.BLANC, 4, 0));
        for (int i = 0; i < 8; i++) this.setPiece(i, 1, new Pion(this, Couleur.BLANC, i, 1));
        
        this.setPiece(0, 7, new Tour(this, Couleur.NOIR, 0, 7));
        this.setPiece(1, 7, new Cavalier(this, Couleur.NOIR, 1, 7));
        this.setPiece(2, 7, new Fou(this, Couleur.NOIR, 2, 7));
        this.setPiece(3, 7, new Reine(this, Couleur.NOIR, 3, 7));
        this.setPiece(4, 7, new Roi(this, Couleur.NOIR, 3, 7));
        this.setPiece(5, 7, new Fou(this, Couleur.NOIR, 4, 7));
        this.setPiece(6, 7, new Cavalier(this, Couleur.NOIR, 4, 7));
        this.setPiece(7, 7, new Tour(this, Couleur.NOIR, 4, 7));
        for (int i = 0; i < 8; i++) this.setPiece(i, 6, new Pion(this, Couleur.NOIR, i, 6));
    }
    
    /**
     * Constructeur
     * @param jeu	Référence du jeu
     * @param s		Chaine de charactère représentant une position de départ dans un format inspiré par la notation Forsyth-Edwards
     */
    @objid ("12126bfb-fcc7-42aa-98cf-df40293ed6d5")
    public Plateau(Jeu jeu, String s) {
        this.jeu = jeu;
        
        int x = 0;
        int y = 7;
        for (int i=0; i < s.length(); i++) {
            switch (s.charAt(i)) {
                //Changement de rangée
                case '/':
                    x = -1;
                    y--;
                    break;
                //Pièce noir
                case 'r' :
                    this.setPiece(x, y, new Tour(this, Couleur.NOIR, x, y));
                    break;
                case 'n' :
                    this.setPiece(x, y, new Cavalier(this, Couleur.NOIR, x, y));
                    break;
                case 'b' :
                    this.setPiece(x, y, new Fou(this, Couleur.NOIR, x, y));
                    break;
                case 'q' :
                    this.setPiece(x, y, new Reine(this, Couleur.NOIR, x, y));
                    break;
                case 'p' :
                    this.setPiece(x, y, new Pion(this, Couleur.NOIR, x, y));
                    break;
                case 'k' :
                    this.setPiece(x, y, new Roi(this, Couleur.NOIR, x, y));
                    break;
                //Pièce blanc
                case 'R' :
                    this.setPiece(x, y, new Tour(this, Couleur.BLANC, x, y));
                    break;
                case 'N' :
                    this.setPiece(x, y, new Cavalier(this, Couleur.BLANC, x, y));
                    break;
                case 'B' :
                    this.setPiece(x, y, new Fou(this, Couleur.BLANC, x, y));
                    break;
                case 'Q' :
                    this.setPiece(x, y, new Reine(this, Couleur.BLANC, x, y));
                    break;
                case 'P' :
                    this.setPiece(x, y, new Pion(this, Couleur.BLANC, x, y));
                    break;
                case 'K' :
                    this.setPiece(x, y, new Roi(this, Couleur.BLANC, x, y));
                    break;
                //Case vide
                default:
                    x += Character.getNumericValue(s.charAt(i)) - 1;
                    break;
            }
            x++;
        }
    }

    /**
     * Teste si la case de coordonnées x et y est attaquée par une pièce de la couleur donnée
     * @param couleur	Couleur des pièces à tester
     * @param x			Colonne
     * @param y			Ligne
     * @return Vrai si une pièce de couleur donnée peut se déplacer à la case x y
     */
    @objid ("1f995dc9-8fec-42f0-8242-e6554027628f")
    public boolean estCaseAttaquee(Couleur couleur, int x, int y) {
        for (Coup c : this.getTousCoupsPossibles(couleur)) {
            if (x == c.getCaseArrivee().getX() && y == c.getCaseArrivee().getY())
                return true;
        }
        return false;
    }

    
    /**
     * Calcule tous les coups pseudo légaux possibles des pièces de la couleur donnée
     * @param couleur	Couleur des pièces à tester
     * @return Une liste de coup pseudo légaux
     */
    @objid ("9ad2b080-511a-4144-9fbb-9f34f2fc8e2a")
    public ArrayList<Coup> getTousCoupsPossibles(Couleur couleur) {
        ArrayList<Coup> coups = new ArrayList<Coup>();
        
        for (Piece p : this.getPieces(couleur)) {
            for (Coup c : p.getCoupsPossibles()) {
                coups.add(c);
            }
        }
        return coups;
    }

    /**
     * Calcule tous les coups légaux possibles des pièces de la couleur donnée. 
     * Un coup est légal si il ne met pas le roi de la pièce déplacée en échec
     * @param couleur	Couleur des pièces à tester
     * @return Une liste de coup légaux
     */
    @objid ("2efb8e9f-240d-4c83-9f13-dc182465bd85")
    public ArrayList<Coup> getTousCoupsLegaux(Couleur couleur) {
        ArrayList<Coup> coups = new ArrayList<Coup>();
        
        for (Piece p : this.getPieces(couleur)) {
            for (Coup c : p.getCoupsLegaux()) {
                coups.add(c);
            }
        }
        return coups;
    }
   
    
    /**
     * Retourne le roi de la couleur donnée
     * @param couleur	Couleur du roi
     * @return Le roi de la couleur donnée
     */
    @objid ("cb22a4d7-4ead-4768-ab5a-06a03fbc114a")
    public Roi getRoi(Couleur couleur) {
        Piece p;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                p = pieces[i][j];
                if(p != null && p.getCouleur() == couleur && p instanceof Roi)
                    return (Roi) p;
            }
        }
        return null;
    }

    /**
     * Retourne toutes les pièces du plateau
     * @return Une liste de pièces
     */
    @objid ("07512d1b-0d70-4506-ac66-a0f9a6ce09c0")
    public List<Piece> getPieces() {
        List<Piece> list = new ArrayList<Piece>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(pieces[i][j] != null) list.add(pieces[i][j]);
            }
        }
        return list;
    }

    /**
     * Retourne toutes les pièces de la couleur donnée
     * @param couleur	Couleur des pièces
     * @return Une liste de pièces
     */
    @objid ("a0112055-cc7f-4b41-8fc7-f31e1ee3234e")
    public List<Piece> getPieces(Couleur couleur) {
        List<Piece> list = new ArrayList<Piece>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(pieces[i][j] != null && pieces[i][j].getCouleur() == couleur)
                    list.add(pieces[i][j]);
            }
        }
        return list;
    }

    /**
     * Retourne la pièce aux coordonnées données ou null si il n'y a pas de pièce aux coordonnées données
     * @param x 	Colonne
     * @param y		Ligne
     * @return Une pièce ou null
     * @throws IndexOutOfBoundsException
     */
    @objid ("c408bba4-8bfd-4ca3-b69c-75553f91300f")
    public Piece getPiece(int x, int y) throws IndexOutOfBoundsException {
        return pieces[x][y];
    }

    /**
     * Positionne une pièce aux coordonnées données
     * @param x		Colonne
     * @param y		Ligne
     * @param piece la pièce à placer
     */
    @objid ("537039c4-bc49-4343-95f4-b8291bac7455")
    public void setPiece(int x, int y, Piece piece) {
        this.pieces[x][y] = piece;
        piece.setX(x);
        piece.setY(y);
    }

    /**
     * Retourne la liste des pièces capturées
     * @return Une liste de pièce
     */
    @objid ("b3c5cf82-3fa7-4010-adaa-84612ba14fb0")
    public ArrayList<Piece> getPiecesPrises() {
        return this.piecesPrises;
    }

    /**
     * Vide une case du plateau
     * @param x	Colonne
     * @param y	Ligne
     */
    @objid ("7de6ea7a-d157-4e7e-a004-32dbe6f635fe")
    public void removePiece(int x, int y) {
        this.pieces[x][y] = null;
    }

    @objid ("d150ed68-47e3-4256-81af-b83398d41ec5")
    public Jeu getJeu() {
        return this.jeu;
    }

}
