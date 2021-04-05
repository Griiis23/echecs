package echecs.model;

import java.io.Serializable;
import com.modeliosoft.modelio.javadesigner.annotations.objid;

/**
 * Classe représentant des coordonnéee sur le plateau du jeu
 *
 */
@objid ("a4e98a82-0f92-4689-8498-2b4d4d70eff1")
public class Coordonnees implements Serializable {
   
	/**
     * Colonne
     */
    @objid ("7ee15b88-56dc-464a-834f-0557d1fa7194")
    private int x;

    /**
     * Ligne
     */
    @objid ("b2466153-e1ab-4418-b9ba-4e9d034951b9")
    private int y;

    /**
     * Constructeur 
     * @param x Colonne
     * @param y Lingne
     */
    @objid ("c64f5f49-68ea-4515-a8c3-4e1b14326646")
    public Coordonnees(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Renvoie les coordonnées sous la forme d'une chaine
     */
    @objid ("1d62d30e-6f19-4621-99d3-ad809ddff5cb")
    public String toString() {
        return (char)(this.x + 65) + "" +(this.y+1);
    }
    

    // Getters & setters
    
    @objid ("c182da4b-bb81-4545-96a1-48f15308d2ac")
    public int getX() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.x;
    }

    @objid ("570f937f-aee8-4612-9ce3-321e87d3b25a")
    public void setX(int value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.x = value;
    }

    @objid ("846065c9-e478-4b76-a915-831cfaa7c3ca")
    public int getY() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.y;
    }

    @objid ("7eb70874-f88d-4a7f-ba0e-265c37ad8ca1")
    public void setY(int value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.y = value;
    }

}
