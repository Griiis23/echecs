package echecs.model.piece;

import java.util.ArrayList;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import echecs.model.Coordonnees;
import echecs.model.Coup.Type;
import echecs.model.Coup;
import echecs.model.Plateau;

/**
 * Classe représentant un fou
 *
 */
@objid ("c79cb5b4-0c51-49ec-a0c4-cb67aab35dab")
public class Fou extends Piece {
	
	/**
     * Table de valeur permettant de calculer la qualité du positionnement de la pièce. 
     * Si la pièce est bien placé la valeur sera positive sinon négative
     */
    @objid ("464e42e3-7606-4c96-bb9b-0f3fc65c44f7")
    private static int[][] TABLE = {
			{-20,-10,-10,-10,-10,-10,-10,-20},
			{-10,  0,  0,  0,  0,  0,  0,-10},
			{-10,  0,  5, 10, 10,  5,  0,-10},
			{-10,  5,  5, 10, 10,  5,  5,-10},
			{-10,  0, 10, 10, 10, 10,  0,-10},
			{-10, 10, 10, 10, 10, 10, 10,-10},
			{-10,  5,  0,  0,  0,  0,  5,-10},
			{-20,-10,-10,-10,-10,-10,-10,-20}
	};

    /**
     * Constructeur
     * @param plateau	Référence du plateau
     * @param couleur	Couleur de la pièce
     * @param x			Colonne
     * @param y			Ligne
     */
    @objid ("f49de305-6880-4a97-aad2-981e9a76b954")
    public Fou(Plateau plateau, Couleur couleur, int x, int y) {
        super(plateau, couleur, x, y);
        // TODO Auto-generated constructor stub
    }

    /**
     * Calcule les coups pseudo légaux de la pièce
     * @return Une liste de coups pseudo-légaux
     */
    @objid ("0ea2e681-71ab-40f9-b750-486a23856035")
    public ArrayList<Coup> getCoupsPossibles() {
        ArrayList<Coup> liste = new ArrayList<Coup>();
        
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
        return liste;
    }

    /**
     * Retourne une valeur représentant la qualité du positionnement de la pièce. 
     * Si la pièce est bien placé la valeur sera positive sinon négative
     * @return Le bonus ou malus de positionnement de la pièce
     */
    @objid ("9c002bed-822b-40e4-a145-8de9867d5c4f")
    @Override
    public int getBonus() {
        if(this.couleur == Couleur.BLANC) {
            return Fou.TABLE[this.x][this.y];
        } 
        else {
            return Fou.TABLE[this.x][7-this.y];
        }
    }

    /**
     * Retourne la valeur de la pièce. Le fou à une valeur de 300
     * @return la valeur de la pièce
     */
    @objid ("25ebc653-db6e-4ee7-b722-aaa79e9533c4")
    @Override
    public int getValeur() {
        return 300;
    }

    /**
     * Renvoie la pièce sous forme d'une chaine
     */
    @objid ("527574b1-6ccd-4381-b2e7-dc90aa49b22a")
    public String toString() {
        if(this.couleur == Couleur.BLANC) {
            return "♗";
        } else {
            return "♝";
        }
    }

}
