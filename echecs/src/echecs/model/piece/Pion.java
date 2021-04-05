package echecs.model.piece;

import java.util.ArrayList;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import echecs.model.Coordonnees;
import echecs.model.Coup.Type;
import echecs.model.Coup;
import echecs.model.Plateau;

/**
 * Classe représentant un pion
 *
 */
@objid ("d2c817c4-b976-4376-b738-bb0b908ee7c0")
public class Pion extends Piece {
	
	/**
     * Table de valeur permettant de calculer la qualité du positionnement de la pièce. 
     * Si la pièce est bien placé la valeur sera positive sinon négative
     */
    @objid ("0631c560-1435-4d42-baac-ae987c99370b")
    private static int[][] TABLE = {
			{ 0,  0,  0,  0,  0,  0,  0,  0},
			{50, 50, 50, 50, 50, 50, 50, 50},
			{10, 10, 20, 30, 30, 20, 10, 10},
			{ 5,  5, 10, 25, 25, 10,  5,  5},
			{ 0,  0,  0, 20, 20,  0,  0,  0},
			{ 5, -5,-10,  0,  0,-10, -5,  5},
			{ 5, 10, 10,-20,-20, 10, 10,  5},
			{ 0,  0,  0,  0,  0,  0,  0,  0}
	};

    
    /**
     * Constructeur
     * @param plateau	Référence du plateau
     * @param couleur	Couleur de la pièce
     * @param x			Colonne
     * @param y			Ligne
     */
    @objid ("172c585b-0245-4554-aecf-34df0d58185f")
    public Pion(Plateau plateau, Couleur couleur, int x, int y) {
        super(plateau, couleur, x, y);
        // TODO Auto-generated constructor stub
    }

    @objid ("f1be7fa3-e5b2-49f6-966e-4c35ec8d123e")
    public boolean premierDeplacement() {
        if (this.couleur == Couleur.BLANC && this.y == 1)
            return true;
        if (this.couleur == Couleur.NOIR && this.y == 6)
            return true;
        return false;
    }

    @objid ("3aaf425f-c032-4786-a78e-d0bb2cb34d02")
    public boolean peutEtrePrisEnPassant() {
        Coup dernier = this.plateau.getJeu().getDernierCoup();
        if (dernier != null && dernier.getPieceDeplacee() == this) {
            int dist = dernier.getCaseArrivee().getY() - dernier.getCaseDepart().getY();
            if (dist * dist != 1)
                return true;
        }
        return false;
    }

    /**
     * Calcule les coups pseudo légaux de la pièce
     * @return Une liste de coups pseudo-légaux
     */
    @objid ("c75207aa-468a-4018-afb9-92a5fc2e291f")
    public ArrayList<Coup> getCoupsPossibles() {
        ArrayList<Coup> liste = new ArrayList<Coup>();
        
        int direction = this.couleur == Piece.Couleur.BLANC ? 1 : -1;
        
        if(this.y + direction < 8 && this.y + direction >= 0) {
            // Simple push
            if (this.y + 1 < 8 && this.plateau.getPiece(this.x, this.y + direction) == null) {
                if((this.couleur == Couleur.BLANC && this.y+1 == 7) || (this.couleur == Couleur.NOIR && this.y-1 == 0)) 
                    liste.add(new Coup(Type.PROMOTION, this, new Coordonnees(this.x, this.y + direction), null));
                else liste.add(new Coup(Type.AUTRE, this, new Coordonnees(this.x, this.y + direction), null));
                // Double push
                if (premierDeplacement())
                    if (this.plateau.getPiece(this.x, this.y + direction*2) == null)
                        liste.add(new Coup(Type.AUTRE, this, new Coordonnees(this.x, this.y + direction*2), null));
            }
        
            // Capture droite
            if (this.x + 1 < 8) {
                Piece p = this.plateau.getPiece(this.x + 1, this.y + direction);
                if (p != null && p.couleur != this.couleur) {
                    if((this.couleur == Couleur.BLANC && this.y+1 == 7) || (this.couleur == Couleur.NOIR && this.y-1 == 0)) 
                        liste.add(new Coup(Type.PROMOTION, this, new Coordonnees(this.x + 1, this.y + direction), p));
                    else liste.add(new Coup(Type.AUTRE, this, new Coordonnees(this.x + 1, this.y + direction), p));
                }
            }
        
            // Capture gauche
            if (this.x - 1 >= 0) {
                Piece p = this.plateau.getPiece(this.x - 1, this.y + direction);
                if (p != null && p.couleur != this.couleur) {
                    if((this.couleur == Couleur.BLANC && this.y+1 == 7) || (this.couleur == Couleur.NOIR && this.y-1 == 0)) 
                        liste.add(new Coup(Type.PROMOTION, this, new Coordonnees(this.x - 1, this.y + direction), p));
                    else liste.add(new Coup(Type.AUTRE, this, new Coordonnees(this.x - 1, this.y + direction), p));
                }
            }
        
            // En passant droit
            if (this.x + 1 < 8) {
                Piece p = this.plateau.getPiece(this.x + 1, this.y);
                if (p != null && p.couleur != this.couleur && p.peutEtrePrisEnPassant()) {
                    liste.add(new Coup(Type.ENPASSANT, this, new Coordonnees(this.x + 1, this.y + direction), p));
                }
            }
        
            // En passant gauche
            if (this.x - 1 >= 0) {
                Piece p = this.plateau.getPiece(this.x - 1, this.y);
                if (p != null && p.couleur != this.couleur && p.peutEtrePrisEnPassant()) {
                    liste.add(new Coup(Type.ENPASSANT, this, new Coordonnees(this.x - 1, this.y + direction), p));
                }
            }
        }
        return liste;
    }

    /**
     * Retourne une valeur représentant la qualité du positionnement de la pièce. 
     * Si la pièce est bien placé la valeur sera positive sinon négative
     * @return Le bonus ou malus de positionnement de la pièce
     */
    @objid ("7a5fe95d-a199-4fd3-a8d3-463206cd00da")
    @Override
    public int getBonus() {
        if(this.couleur == Couleur.BLANC) {
            return Pion.TABLE[this.x][this.y];
        } 
        else {
            return Pion.TABLE[this.x][7-this.y];
        }
    }

    /**
     * Retourne la valeur de la pièce. Le pion à une valeur de 100
     * @return la valeur de la pièce
     */
    @objid ("fccf643e-1e63-4dc4-8be9-b7b7a32e6151")
    @Override
    public int getValeur() {
        return 100;
    }

    /**
     * Renvoie la pièce sous forme d'une chaine
     */
    @objid ("144887b2-9148-4fcf-bf81-d4a03f082aef")
    public String toString() {
        if(this.couleur == Couleur.BLANC) {
            return "♙";
        } else {
            return "♟";
        }
    }

}
