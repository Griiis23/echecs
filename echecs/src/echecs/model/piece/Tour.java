package echecs.model.piece;

import java.util.ArrayList;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import echecs.model.Coordonnees;
import echecs.model.Coup.Type;
import echecs.model.Coup;
import echecs.model.Plateau;

/**
 * Classe représentant une tour
 *
 */
@objid ("e6d6914b-6cb5-4c3d-bd25-890f5ab2f68a")
public class Tour extends Piece {
	
	/**
     * Table de valeur permettant de calculer la qualité du positionnement de la pièce. 
     * Si la pièce est bien placé la valeur sera positive sinon négative
     */
    @objid ("e21bcb20-a2b2-4b94-982a-207e59eb234b")
    private static int[][] TABLE = {
			{ 0,  0,  0,  0,  0,  0,  0,  0 },
			{ 5, 10, 10, 10, 10, 10, 10,  5 },
			{-5,  0,  0,  0,  0,  0,  0, -5 },
			{-5,  0,  0,  0,  0,  0,  0, -5 },
			{-5,  0,  0,  0,  0,  0,  0, -5 },
			{-5,  0,  0,  0,  0,  0,  0, -5 },
			{-5,  0,  0,  0,  0,  0,  0, -5 },
			{ 0,  0,  0,  5,  5,  0,  0,  0 }
	};

    /**
     * Constructeur
     * @param plateau	Référence du plateau
     * @param couleur	Couleur de la pièce
     * @param x			Colonne
     * @param y			Ligne
     */
    @objid ("252af38a-76ad-4660-bdfe-f904f8642e99")
    public Tour(Plateau plateau, Couleur couleur, int x, int y) {
        super(plateau, couleur, x, y);
        // TODO Auto-generated constructor stub
    }

    /**
     * Teste si la tour s'est deja déplacée
     * @return Vrai si la tour ne s'est jamais déplacée
     */
    @objid ("3220c41a-cbdd-4583-bb5e-c48fafdb5de8")
    public boolean peutRoque() {
        for(Coup c : this.plateau.getJeu().getCoups()) {
            if(c.getPieceDeplacee() == this) return false;
        }
        return true;
    }

    /**
     * Calcule les coups pseudo légaux de la pièce
     * @return Une liste de coups pseudo-légaux
     */
    @objid ("4ecae018-28a8-4759-a3dc-ffc03eb92497")
    public ArrayList<Coup> getCoupsPossibles() {
        ArrayList<Coup> liste = new ArrayList<Coup>();
        
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
    @objid ("c46bf325-1bde-4ff3-8fbe-ae6e6e526cc1")
    @Override
    public int getBonus() {
        if(this.couleur == Couleur.BLANC) {
            return Tour.TABLE[this.x][this.y];
        } 
        else {
            return Tour.TABLE[this.x][7-this.y];
        }
    }

    /**
     * Retourne la valeur de la pièce. La tour à une valeur de 500
     * @return la valeur de la pièce
     */
    @objid ("e88d6b47-ce4b-4d6c-90f0-2deb9e7057b0")
    @Override
    public int getValeur() {
        return 500;
    }

    /**
     * Renvoie la pièce sous forme d'une chaine
     */
    @objid ("ed4a07c3-c0c7-4109-b0d7-c25208f7447c")
    public String toString() {
        if(this.couleur == Couleur.BLANC) {
            return "♖";
        } else {
            return "♜";
        }
    }

}
