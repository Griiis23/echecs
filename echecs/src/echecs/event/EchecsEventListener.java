package echecs.event;

import java.util.EventListener;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import echecs.model.Coup;
import echecs.model.Joueur;
import echecs.model.piece.Piece.Couleur;

/**
 * Interface pour les observeurs du jeu
 *
 */
@objid ("0ee43784-a04c-4aa3-8d41-63c6b94e85f4")
public interface EchecsEventListener extends EventListener {
    /**
     * Action à déclencher lors d'un évènement promotion
     * @param coup	Coup de type promotion
     */
    @objid ("ebaf6b68-ee82-43b8-870b-40acaff9cce8")
    void promotion(Coup coup);

    /**
     * Action à déclencher lors d'un évènement déplacement
     */
    @objid ("aaa8f97c-b0b2-4237-a501-410815058d9d")
    void pieceDeplacee();

    /**
     * Action à déclencher lors d'un évènement déplacement illégal
     */
    @objid ("373d5e2e-39a5-4af8-b0aa-d7560fceada4")
    void deplacementIllegal();

    /**
     * Action à déclencher lors d'un évènement mat
     */
    @objid ("8a03f472-5c9b-41a6-8b9c-a729cc7bda67")
    void echecMat(Couleur couleur);

    /**
     * Action à déclencher lors d'un évènement nulle
     */
    @objid ("585b8330-05b6-43db-ad31-f665674e4a66")
    void nulle(Couleur couleur);

    /**
     * Action à déclencher lors d'un évènement échec
     */
    @objid ("568237ba-ca55-4cea-ab09-37ebbf6deae5")
    void echec(Couleur couleur);

    /**
     * Action à déclencher lors d'un évènement tic chrono
     */
    @objid ("36d98109-8ff9-438e-82cb-fb9983eed71c")
    void ticChrono(Joueur joueur);

    /**
     * Action à déclencher lors d'un évènement perd au temps
     */
    @objid ("a9ae28e1-edd0-45f6-9daa-ffb07cac5129")
    void perdTemps(Couleur couleur);

}
