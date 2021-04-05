package echecs.model.piece;

import java.util.ArrayList;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import echecs.model.Coordonnees;
import echecs.model.Coup.Type;
import echecs.model.Coup;
import echecs.model.Plateau;

/**
 * Classe représentant un cavalier
 *
 */
@objid ("372ef44d-651f-4da7-b231-c481884d941d")
public class Cavalier extends Piece {
	
    /**
     * Table de valeur permettant de calculer la qualité du positionnement de la pièce. 
     * Si la pièce est bien placé la valeur sera positive sinon négative
     */
    @objid ("8686758a-0a09-4297-9a1a-8aa512f46160")
    private static int[][] TABLE = {
			{-50,-40,-30,-30,-30,-30,-40,-50},
			{-40,-20,  0,  0,  0,  0,-20,-40},
			{-30,  0, 10, 15, 15, 10,  0,-30},
			{-30,  5, 15, 20, 20, 15,  5,-30},
			{-30,  0, 15, 20, 20, 15,  0,-30},
			{-30,  5, 10, 15, 15, 10,  5,-30},
			{-40,-20,  0,  5,  5,  0,-20,-40},
			{-50,-40,-30,-30,-30,-30,-40,-50},
	};

    /**
     * Constructeur
     * @param plateau	Référence du plateau
     * @param couleur	Couleur de la pièce
     * @param x			Colonne
     * @param y			Ligne
     */
    @objid ("152955cd-4e70-4123-9737-199dddf7c8c9")
    public Cavalier(Plateau plateau, Couleur couleur, int x, int y) {
        super(plateau, couleur, x, y);
        // TODO Auto-generated constructor stub
    }

    /**
     * Calcule les coups pseudo légaux de la pièce
     * @return Une liste de coups pseudo-légaux
     */
    @objid ("ac4fc32f-bc23-45ea-a92e-4d0ada7c812e")
    public ArrayList<Coup> getCoupsPossibles() {
        ArrayList<Coup> liste = new ArrayList<Coup>();
        
        // déplacement en fonction de l'axe y
        if ((this.y - 2 >= 0 && this.x - 1 >= 0)) {
            Piece p = this.plateau.getPiece(this.x - 1, this.y - 2);
            if (p == null || p.couleur != this.couleur) {
                liste.add(new Coup(Type.AUTRE, this, new Coordonnees(this.x - 1, this.y - 2), p));
            }
        }
        
        if ((this.y - 2 >= 0 && this.x + 1 < 8)) {
            Piece p = this.plateau.getPiece(this.x + 1, this.y - 2);
            if (p == null || p.couleur != this.couleur) {
                liste.add(new Coup(Type.AUTRE, this, new Coordonnees(this.x + 1, this.y - 2), p));
            }
        }
        
        if ((this.y + 2 < 8 && this.x + 1 < 8)) {
            Piece p = this.plateau.getPiece(this.x + 1, this.y + 2);
            if (p == null || p.couleur != this.couleur) {
                liste.add(new Coup(Type.AUTRE, this, new Coordonnees(this.x + 1, this.y + 2), p));
            }
        }
        
        if ((this.y + 2 < 8 && this.x - 1 >= 0)) {
            Piece p = this.plateau.getPiece(this.x - 1, this.y + 2);
            if (p == null || p.couleur != this.couleur) {
                liste.add(new Coup(Type.AUTRE, this, new Coordonnees(this.x - 1, this.y + 2), p));
            }
        }
        
        // déplacement en fonction de l'axe X
        if ((this.x - 2 >= 0 && this.y - 1 >= 0)) {
            Piece p = this.plateau.getPiece(this.x - 2, this.y - 1);
            if (p == null || p.couleur != this.couleur) {
                liste.add(new Coup(Type.AUTRE, this, new Coordonnees(this.x - 2, this.y - 1), p));
            }
        }
        
        if ((this.x - 2 >= 0 && this.y + 1 < 8)) {
            Piece p = this.plateau.getPiece(this.x - 2, this.y + 1);
            if (p == null || p.couleur != this.couleur) {
                liste.add(new Coup(Type.AUTRE, this, new Coordonnees(this.x - 2, this.y + 1), p));
            }
        }
        
        if ((this.x + 2 < 8 && this.y + 1 < 8)) {    
            Piece p = this.plateau.getPiece(this.x + 2, this.y + 1);
            if (p == null || p.couleur != this.couleur) {
                liste.add(new Coup(Type.AUTRE, this, new Coordonnees(this.x + 2, this.y + 1), p));
            }
        }
        
        if ((this.x + 2 < 8 && this.y - 1 >= 0)) {
            Piece p = this.plateau.getPiece(this.x + 2, this.y - 1);
            if (p == null || p.couleur != this.couleur) {
                liste.add(new Coup(Type.AUTRE, this, new Coordonnees(this.x + 2, this.y - 1), p));
            }
        }
        return liste;
    }

    /**
     * Retourne une valeur représentant la qualité du positionnement de la pièce. 
     * Si la pièce est bien placé la valeur sera positive sinon négative
     * @return Le bonus ou malus de positionnement de la pièce
     */
    @objid ("9cd98959-99c7-4d3c-8d8f-deb3bc430a10")
    @Override
    public int getBonus() {
        if(this.couleur == Couleur.BLANC) {
            return Cavalier.TABLE[this.x][this.y];
        }
        // Si la pièce est noir on lit la table à l'envers
        else {
            return Cavalier.TABLE[this.x][7-this.y];
        }
    }

    /**
     * Retourne la valeur de la pièce. Le cavalier à une valeur de 300
     * @return la valeur de la pièce
     */
    @objid ("3e809962-019f-4b16-b63b-e7a014ec6018")
    @Override
    public int getValeur() {
        return 300;
    }

    
    /**
     * Renvoie la pièce sous forme d'une chaine
     */
    @objid ("93d6fc97-c979-49c3-bb34-26e938f60c76")
    public String toString() {
        if(this.couleur == Couleur.BLANC) {
            return "♘";
        } else {
            return "♞";
        }
    }

}
