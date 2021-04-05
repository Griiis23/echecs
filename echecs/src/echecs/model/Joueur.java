package echecs.model;

import java.io.Serializable;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import echecs.model.piece.Piece.Couleur;

/**
 * Classe représentant un joueur
 *
 */
@objid ("d6b556b7-6bd6-4c67-8bd3-5eef9985ff48")
public abstract class Joueur implements Serializable {
    
	/**
     * Couleur des pièces du joueur
     */
    @objid ("98ef04ae-983c-4ae0-997e-f7f69936995d")
    private Couleur couleur;

    /**
     * Référence du jeu
     */
    @objid ("db5f7659-7aa2-4be1-ad09-5a8ffb735738")
    private Jeu jeu;

    /**
     * Référence du pendule
     */
    @objid ("d6186387-cb6d-4abb-a540-087a2f8dabce")
    private Chrono chrono;

    /**
     * Constructeur
     * @param jeu		Référence du jeu
     * @param couleur	Couleur des pièces du joueur
     */
    @objid ("64dbec15-c9f1-4e7a-a6ea-f493375e1ec4")
    public Joueur(Jeu jeu, Couleur couleur) {
        super();
        this.jeu = jeu;
        this.couleur = couleur;
    }

    // Getters & setters
    
    @objid ("7bc6966a-1b48-4f52-9d4b-22d8175a1999")
    public Jeu getJeu() {
        return jeu;
    }

    @objid ("f801fd78-9420-4400-9477-c794f9812c01")
    public Couleur getCouleur() {
        return couleur;
    }

    @objid ("849df5b5-2496-4cca-8f58-633bd0403d8c")
    public Chrono getChrono() {
        return chrono;
    }

    @objid ("553f50c2-aee7-46c5-9b82-046443a0b1e7")
    public void setChrono(Chrono chrono) {
        this.chrono = chrono;
    }

}
