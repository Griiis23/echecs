package echecs.model.piece;

import java.util.ArrayList;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import echecs.model.Coordonnees;
import echecs.model.Coup.Type;
import echecs.model.Coup;
import echecs.model.Plateau;

/**
 * Classe représentant une reine
 *
 */
@objid ("3da5c4ed-623e-4be3-ac07-01fe8d49043c")
public class Reine extends Piece {
	
	/**
     * Table de valeur permettant de calculer la qualité du positionnement de la pièce. 
     * Si la pièce est bien placé la valeur sera positive sinon négative
     */
    @objid ("95a71700-e9e9-40fd-aaf0-cb05f02e094d")
    private static int[][] TABLE = {
			{-20,-10,-10, -5, -5,-10,-10,-20},
			{-10,  0,  0,  0,  0,  0,  0,-10},
			{-10,  0,  5,  5,  5,  5,  0,-10},
			{ -5,  0,  5,  5,  5,  5,  0, -5},
			{  0,  0,  5,  5,  5,  5,  0, -5},
			{-10,  5,  5,  5,  5,  5,  0,-10},
			{-10,  0,  5,  0,  0,  0,  0,-10},
			{-20,-10,-10, -5, -5,-10,-10,-20}
	};

    /**
     * Constructeur
     * @param plateau	Référence du plateau
     * @param couleur	Couleur de la pièce
     * @param x			Colonne
     * @param y			Ligne
     */
    @objid ("bb36bf65-ccc3-40b2-9a10-0e03200faad2")
    public Reine(Plateau plateau, Couleur couleur, int x, int y) {
        super(plateau, couleur, x, y);
        // TODO Auto-generated constructor stub
    }

    /**
     * Calcule les coups pseudo légaux de la pièce
     * @return Une liste de coups pseudo-légaux
     */
    @objid ("9ce36dcc-2c70-4acc-87ec-d094c1dd056c")
    public ArrayList<Coup> getCoupsPossibles() {
        ArrayList<Coup> liste = new ArrayList<Coup>();
        
        //Coups fou
        for (int i = 1; i < 8; i++) {
            if (this.x - i < 0 || this.y + i >= 8) break;
            Piece p = this.plateau.getPiece(this.x - i, this.y + i);
            if (p != null) {
                if (this.couleur != p.couleur) liste.add(new Coup(Type.AUTRE, this, new Coordonnees(this.x - i, this.y + i), p));
                break;
            }
            liste.add(new Coup(Type.AUTRE, this, new Coordonnees(this.x - i, this.y + i), null));
        }
        
        
        for (int i = 1; i < 8; i++) {
            if (this.x + i >= 8 || this.y - i < 0)
                break;
            Piece p = this.plateau.getPiece(this.x + i, this.y - i);
            if (p != null) {
                if (this.couleur != p.couleur) liste.add(new Coup(Type.AUTRE, this, new Coordonnees(this.x + i, this.y - i), p));;
                break;
            }
            liste.add(new Coup(Type.AUTRE, this, new Coordonnees(this.x + i, this.y - i), null));;
        }
        
        for (int i = 1; i < 8; i++) {
            if (this.y - i < 0 || this.x - i < 0)
                break;
            Piece p = this.plateau.getPiece(this.x - i, this.y - i);
            if (p != null) {
                if (this.couleur != p.couleur) liste.add(new Coup(Type.AUTRE, this, new Coordonnees(this.x - i, this.y - i), p));
                break;
            }
            liste.add(new Coup(Type.AUTRE, this, new Coordonnees(this.x - i, this.y - i), p));
        }
        
        for (int i = 1; i < 8; i++) {
            if (this.y + i >= 8 || this.x + i >= 8)
                break;
            Piece p = this.plateau.getPiece(this.x + i, this.y + i);
            if (p != null) {
                if (this.couleur != p.couleur) liste.add(new Coup(Type.AUTRE, this, new Coordonnees(this.x + i, this.y + i), p));;
                break;
            }
            liste.add(new Coup(Type.AUTRE, this, new Coordonnees(this.x + i, this.y + i), null));
        }
        
        //Coups tour
        for (int i = this.x-1 ; i >= 0; i--) {
            Piece p = this.plateau.getPiece(i, this.y);
            if (p != null) {
                if (this.couleur != p.couleur) liste.add(new Coup(Type.AUTRE, this, new Coordonnees(i, this.y), p));
                break;
            }
            liste.add(new Coup(Type.AUTRE, this, new Coordonnees(i, this.y), null));
        }
        
        for (int i = this.x+1; i < 8; i++) {
            Piece p = this.plateau.getPiece(i, this.y);
            if (p != null) {
                if (this.couleur != p.couleur) liste.add(new Coup(Type.AUTRE, this, new Coordonnees(i, this.y), p));
                break;
            }
            liste.add(new Coup(Type.AUTRE, this, new Coordonnees(i, this.y), null));
        }
        
        for (int i = this.y-1; i >= 0; i--) {
            Piece p = this.plateau.getPiece(this.x, i);
            if (p != null) {
                if (this.couleur != p.couleur) liste.add(new Coup(Type.AUTRE, this, new Coordonnees(this.x, i), p));
                break;
            }
            liste.add(new Coup(Type.AUTRE, this, new Coordonnees(this.x, i), null));
        }
        
        for (int i = this.y+1; i < 8; i++) {
            Piece p = this.plateau.getPiece(this.x, i);
            if (p != null) {
                if (this.couleur != p.couleur) liste.add(new Coup(Type.AUTRE, this, new Coordonnees(this.x, i), p));
                break;
            }
            liste.add(new Coup(Type.AUTRE, this, new Coordonnees(this.x, i), null));
        }
        return liste;
    }

    /**
     * Retourne une valeur représentant la qualité du positionnement de la pièce. 
     * Si la pièce est bien placé la valeur sera positive sinon négative
     * @return Le bonus ou malus de positionnement de la pièce
     */
    @objid ("fb2e7dbd-dded-4330-b8f9-613b92143602")
    @Override
    public int getBonus() {
        if(this.couleur == Couleur.BLANC) {
            return Reine.TABLE[this.x][this.y];
        } 
        else {
            return Reine.TABLE[this.x][7-this.y];
        }
    }

    /**
     * Retourne la valeur de la pièce. La reine à une valeur de 900
     * @return la valeur de la pièce
     */
    @objid ("08ad2411-5bac-4190-9d5a-242cecfdd2f6")
    @Override
    public int getValeur() {
        return 900;
    }
    
    /**
     * Renvoie la pièce sous forme d'une chaine
     */
    @objid ("425af014-bd76-43a9-949f-6064d55eead0")
    public String toString() {
        if(this.couleur == Couleur.BLANC) {
            return "♕";
        } else {
            return "♛";
        }
    }

}
