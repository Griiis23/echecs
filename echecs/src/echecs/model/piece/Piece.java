package echecs.model.piece;

import java.io.Serializable;
import java.util.ArrayList;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import echecs.model.Coup;
import echecs.model.Plateau;

/**
 * Classe représentant une piece quelconque
 *
 */
@objid ("fed3276b-8bbf-4396-93b6-015343066704")
public abstract class Piece implements Serializable {
    
	/**
	 * Couleur de la pièce
	 */
	@objid ("9654a216-ace1-4245-a059-fe467c497d8a")
    protected Couleur couleur;

    /**
     * Colonne sur le plateau
     */
    @objid ("6da58b19-c678-4d95-9149-3487cd75317d")
    protected int x;

    /**
     * Ligne sur le plateau
     */
    @objid ("334e1f5d-7a32-4424-88cb-178bc1eb9c84")
    protected int y;

    /**
     * Référence du plateau
     */
    @objid ("e263ac1a-d772-48ab-8300-0ace55eaa120")
    protected Plateau plateau;

    /**
     * Constructeur
     * @param plateau	Référence du plateau
     * @param couleur	Couleur de la pièce
     * @param x			Colonne
     * @param y			Ligne
     */
    @objid ("e816b34f-6ad1-40da-8bcf-2aa31deec0da")
    public Piece(Plateau plateau, Couleur couleur, int x, int y) {
        this.plateau = plateau;
        this.couleur = couleur;
        this.x = x;
        this.y = y;
    }

    @objid ("e0c08cae-b337-4071-96e0-ff5718d2106b")
    public Couleur getCouleur() {
        return couleur;
    }

    /**
     * Teste si la pièce peut être pris en passant
     * @return
     */
    @objid ("8043cd82-ae68-4fce-a312-e94c5ebfa689")
    public boolean peutEtrePrisEnPassant() {
        return false;
    }

    @objid ("af8f072e-68c7-4e3c-8c6b-f577fba8f864")
    public int getX() {
        return x;
    }

    @objid ("76344756-fa1e-4d93-a190-e7602591bb4b")
    public int getY() {
        return y;
    }

    @objid ("7f97aea6-f92d-464d-bc4a-de3271a02b36")
    public void setX(int x) {
        this.x = x;
    }

    @objid ("03b9b4a4-4254-4a09-8c83-2b7b0ee66b26")
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Calcule les coups pseudo légaux de la pièce
     * @return Une liste de coups pseudo-légaux
     */
    @objid ("cda039c7-d5bd-458a-a38b-d04bc11d8ac5")
    public abstract ArrayList<Coup> getCoupsPossibles();

    /**
     * Calcule les coups légaux de la pièce
     * @return Une liste de coups légaux
     */
    @objid ("0ef2cd3e-778f-4be8-b9a4-9d0fd5d7259a")
    public ArrayList<Coup> getCoupsLegaux() {
        ArrayList<Coup> coupsLegaux = new ArrayList<Coup>();
        Roi roi = this.plateau.getRoi(this.getCouleur());
        for (Coup coup : this.getCoupsPossibles()) {
            this.plateau.getJeu().faireDeplacement(coup);
            if (!roi.estEchec()) coupsLegaux.add(coup);
            this.plateau.getJeu().annulerDeplacement();
        }
        return coupsLegaux;
    }

    
    /**
     * Retourne une valeur représentant la qualité du positionnement de la pièce. 
     * Si la pièce est bien placé la valeur sera positive sinon négative
     * @return Le bonus ou malus de positionnement de la pièce
     */
    @objid ("e862f96e-cd76-4490-ad67-346c14a33862")
    public abstract int getBonus();

    /**
     * Retourne la valeur de la pièce. Le roi à la plus haute valeur et le pion la moindre
     * @return la valeur de la pièce
     */
    @objid ("384ae233-cb6a-4928-bc63-a9bae9f479d0")
    public abstract int getValeur();

    /**
     * Ennumération des couleurs d'une pièce, blanc ou noir
     *
     */
    @objid ("b7c089d2-0104-432b-8756-a238ae11c7cc")
    public enum Couleur {
        BLANC,
        NOIR;
    }

}
