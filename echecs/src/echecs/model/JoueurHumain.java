package echecs.model;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import echecs.model.piece.Piece.Couleur;


/**
 * Classe représentant un joueur humain
 *
 */
@objid ("0ce33e64-5527-4c2c-9d8c-dc989c744838")
public class JoueurHumain extends Joueur {
    
	/**
     * Constructeur
     * @param jeu		Référence du jeu
     * @param couleur	Couleur des pièces du joueur
     */
    @objid ("2bb89fd4-8969-4c3b-85b1-f48e325afc89")
    public JoueurHumain(Jeu jeu, Couleur couleur) {
        super(jeu, couleur);
    }

    
    /**
     * Renvoie une chaine de la forme "Joueur couleur" 
     */
    @objid ("5eb57e78-6b03-4afd-985c-18b264e148c4")
    public String toString() {
        return "Joueur " + this.getCouleur().toString().toLowerCase();
    }

}
