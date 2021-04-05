package echecs.model.piece;

import java.util.ArrayList;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import echecs.model.Coordonnees;
import echecs.model.Coup.Type;
import echecs.model.Coup;
import echecs.model.Plateau;

/**
 * Classe représentant un roi
 *
 */
@objid ("87eb72e7-0f8b-4309-ae09-0e379a9467d9")
public class Roi extends Piece {
	
	/**
     * Table de valeur permettant de calculer la qualité du positionnement de la pièce. 
     * Si la pièce est bien placé la valeur sera positive sinon négative. 
     * Table pour le début et milieu de partie
     */
    @objid ("31249350-5566-440b-8266-1703d868214f")
    private static int[][] TABLE_MID = {
			{-30,-40,-40,-50,-50,-40,-40,-30},
			{-30,-40,-40,-50,-50,-40,-40,-30},
			{-30,-40,-40,-50,-50,-40,-40,-30},
			{-30,-40,-40,-50,-50,-40,-40,-30},
			{-20,-30,-30,-40,-40,-30,-30,-20},
			{-10,-20,-20,-20,-20,-20,-20,-10},
			{ 20, 20,  0,  0,  0,  0, 20, 20},
			{ 20, 30, 10,  0,  0, 10, 30, 20}
	};

    /**
     * Table de valeur permettant de calculer la qualité du positionnement de la pièce. 
     * Si la pièce est bien placé la valeur sera positive sinon négative. 
     * Table pour la fin de partie
     */
    @objid ("c4b69df6-4c6a-421b-9c62-839430d14357")
    private static int[][] TABLE_END = {
			{-50,-40,-30,-20,-20,-30,-40,-50},
			{-30,-20,-10,  0,  0,-10,-20,-30},
			{-30,-10, 20, 30, 30, 20,-10,-30},
			{-30,-10, 30, 40, 40, 30,-10,-30},
			{-30,-10, 30, 40, 40, 30,-10,-30},
			{-30,-10, 20, 30, 30, 20,-10,-30},
			{-30,-30,  0,  0,  0,  0,-30,-30},
			{-50,-30,-30,-30,-30,-30,-30,-50}
	};
    
    /**
     * Constructeur
     * @param plateau	Référence du plateau
     * @param couleur	Couleur de la pièce
     * @param x			Colonne
     * @param y			Ligne
     */
    @objid ("a446c58a-cd9f-411f-9937-8562059d843d")
    public Roi(Plateau plateau, Couleur couleur, int x, int y) {
        super(plateau, couleur, x, y);
        // TODO Auto-generated constructor stub
    }

    /**
     * Teste si le roi est en echec
     * @return Vrai si le roi est en echec
     */
    @objid ("9bc711b3-3c2e-4088-b3da-a149c0d2c7dc")
    public boolean estEchec() {
        // On vérifie si notre roi est dans une case attaquée par la couleur enmemie
        return this.plateau.estCaseAttaquee(this.couleur == Couleur.BLANC ? Couleur.NOIR : Couleur.BLANC, this.x, this.y);
    }

    /**
     * Teste si le roi est mat
     * @return Vrai si le roi est mat
     */
    @objid ("7e9f12c8-9fe9-4d74-b6a2-a5f7e99b6218")
    public boolean estEchecMat() {
        return this.estEchec() && this.plateau.getTousCoupsLegaux(this.couleur).size() == 0;
    }

    /**
     * Teste si la partie est pat
     * @return Vrai si la partie est pat 
     */
    @objid ("32bac9a0-125d-4dd1-a967-3bc344f7baee")
    public boolean estPat() {
        return !this.estEchec() && this.plateau.getTousCoupsLegaux(this.couleur).size() == 0;
    }

    /**
     * Teste si le roi s'est deja déplacé
     * @return Vrai si le roi ne s'est jamais déplacé
     */
    @objid ("ae465640-841c-4b8c-8020-a313731b6b44")
    public boolean peutRoque() {
        for (Coup c : this.plateau.getJeu().getCoups()) {
            if (c.getPieceDeplacee() == this)
                return false;
        }
        return true;
    }

    /**
     * Calcule les coups pseudo légaux de la pièce
     * @return Une liste de coups pseudo-légaux
     */
    @objid ("7a9e13fe-a3e8-4d17-9032-b9b030b553e3")
    public ArrayList<Coup> getCoupsPossibles() {
        ArrayList<Coup> liste = new ArrayList<Coup>();
        
        for(int i = this.x - 1; i <= this.x + 1; i++) {
            for(int j = this.y - 1; j <= this.y + 1; j++) {
                 if (i >= 0 && j>=0 && i < 8 && j < 8 && !(i==this.x && j==this.y)) {
                     Piece p = this.plateau.getPiece(i, j);
                     if (p != null) {
                         if (this.couleur != p.couleur) liste.add(new Coup(Type.AUTRE, this, new Coordonnees(i, j), p));
                         continue;
                     }
                     liste.add(new Coup(Type.AUTRE, this, new Coordonnees(i, j), null));
                 }
            }
        }
        return liste;
    }

    /**
     * Calcule les coups légaux de la pièce
     * @return Une liste de coups légaux
     */
    @objid ("b243d173-69f5-47f4-b4ca-8f331ff54f14")
    @Override
    public ArrayList<Coup> getCoupsLegaux() {
        ArrayList<Coup> coupsLegaux = super.getCoupsLegaux();
        
        //On ajoute les roques
        if (this.peutPetitRoque())
            coupsLegaux.add(new Coup(Type.PETIT_ROQUE, this, new Coordonnees(6, this.getY()), null));
        
        if (this.peutGrandRoque())
            coupsLegaux.add(new Coup(Type.GRAND_ROQUE, this, new Coordonnees(2, this.getY()), null));
        return coupsLegaux;
    }

    /**
     * Teste si le roi peut faire un petit roque
     * @return Vrai si roi peut faire un petit roque
     */
    @objid ("2fb9082f-15a6-45d8-aefb-8310a96dda53")
    public boolean peutPetitRoque() {
        Piece p = this.plateau.getPiece(7, this.y);
        if (p != null && p instanceof Tour && this.peutRoque() && ((Tour)p).peutRoque()) {
            
            for(int i = 1; i < 3; i++) if(this.plateau.getPiece(this.x + i, this.y) != null)
                return false;
            
            
            for(int i = 0; i < 3; i++) 
                if(this.plateau.estCaseAttaquee(this.couleur == Couleur.BLANC ? Couleur.NOIR : Couleur.BLANC,this.x + i, this.y))
                    return false;
            
            return true;
        }
        return false;
    }

    /**
     * Teste si le roi peut faire un grand roque
     * @return Vrai si roi peut faire un grand roque
     */
    @objid ("a5246e5e-b06a-463c-bef6-a0ab9b862063")
    public boolean peutGrandRoque() {
        Piece p = this.plateau.getPiece(0, this.y);
        if (p != null && p instanceof Tour && this.peutRoque() && ((Tour)p).peutRoque()) {
            
            for(int i = 1; i <= 3; i++) if(this.plateau.getPiece(this.x - i, this.y) != null) 
                return false;
            
            for(int i = 0; i <= 3; i++)
                if(this.plateau.estCaseAttaquee(this.couleur == Couleur.BLANC ? Couleur.NOIR : Couleur.BLANC,this.x - i, this.y)) 
                    return false;
            
            return true;
        }
        return false;
    }

    /**
     * Retourne une valeur représentant la qualité du positionnement de la pièce. 
     * Si la pièce est bien placé la valeur sera positive sinon négative
     * @return Le bonus ou malus de positionnement de la pièce
     */
    @objid ("12ec9511-4724-40df-9cff-4ee0a54cf752")
    @Override
    public int getBonus() {
        if (this.plateau.getPieces().size() > 16) {
            if (this.couleur == Couleur.BLANC)
                return Roi.TABLE_MID[this.x][this.y];
            else
                return Roi.TABLE_MID[this.x][7 - this.y];
        } else {
            if (this.couleur == Couleur.BLANC)
                return Roi.TABLE_END[this.x][this.y];
            else
                return Roi.TABLE_END[this.x][7 - this.y];
        }
    }

    /**
     * Retourne la valeur de la pièce. Le roi à une valeur de 20000
     * @return la valeur de la pièce
     */
    @objid ("fa5eb665-9f94-4ba5-812f-4d8bd37411e5")
    @Override
    public int getValeur() {
        return 20000;
    }

    /**
     * Renvoie la pièce sous forme d'une chaine
     */
    @objid ("ae85e822-fd3a-46c0-8a2c-ed31ce36f5cf")
    public String toString() {
        if(this.couleur == Couleur.BLANC) {
            return "♔";
        } else {
            return "♚";
        }
    }

}
