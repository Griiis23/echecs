package echecs.event;

import javax.swing.event.EventListenerList;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import echecs.model.Coup;
import echecs.model.Joueur;
import echecs.model.piece.Piece.Couleur;

/**
 * Notifieur d'évènement du jeu
 *
 */
@objid ("04b42c72-6585-4425-9e63-49c3f9564136")
public class EchecsEventNotifieur {
    /**
     * Liste des observeurs
     */
    @objid ("9ceef26d-21a0-41d3-a102-6ae2e6ff587e")
    private EventListenerList listenerList = new EventListenerList();

    /**
     * Ajoute un observeur à la liste
     * @param listener Observeur
     */
    @objid ("8bdae198-4743-4d9c-926b-4fbdae5298b4")
    public void addEchecsEventListener(EchecsEventListener listener) {
        listenerList.add(EchecsEventListener.class, listener);
    }

    /**
     * Retire un observeur à la liste
     * @param listener
     */
    @objid ("0601770b-e94b-40cb-a4ba-cafddf4052ca")
    public void removeEchecsEventListener(EchecsEventListener listener) {
        listenerList.remove(EchecsEventListener.class, listener);
    }

    /**
     * Diffuse un évènement promotion à tous les observeurs de la liste
     * @param coup Coup de type promotion
     */
    @objid ("98e29004-bc13-4385-84f6-57d8c780fc44")
    public void diffuserPromotion(Coup coup) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i+2) {
          if (listeners[i] == EchecsEventListener.class) {
            ((EchecsEventListener) listeners[i+1]).promotion(coup);
          }
        }
    }

    /**
     * Diffuse un évènement déplacement à tous les observeurs de la liste
     */
    @objid ("6c7c6bb3-bd6c-4a40-bcdb-b629c32dd599")
    public void diffuserPieceDeplacee() {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i+2) {
          if (listeners[i] == EchecsEventListener.class) {
            ((EchecsEventListener) listeners[i+1]).pieceDeplacee();
          }
        }
    }

    /**
     * Diffuse un évènement déplacement illégal à tous les observeurs de la liste
     */
    @objid ("b7de1e91-6c1b-44df-8cf1-b33803a066e8")
    public void diffuserDeplacementIllegal() {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i+2) {
          if (listeners[i] == EchecsEventListener.class) {
            ((EchecsEventListener) listeners[i+1]).deplacementIllegal();
          }
        }
    }

    /**
     * Diffuse un évènement echec à tous les observeurs de la liste
     * @param couleur Couleur des pièces en echec
     */
    @objid ("ff3db5ff-8999-4af1-b6a2-983b0a991c0f")
    public void diffuserEchec(Couleur couleur) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i+2) {
          if (listeners[i] == EchecsEventListener.class) {
            ((EchecsEventListener) listeners[i+1]).echec(couleur);
          }
        }
    }

    /**
     * Diffuse un évènement mat à tous les observeurs de la liste
     * @param couleur Couleur des pièces en echec et mat
     */
    @objid ("6e4983cc-9ea6-4e67-b9fb-cb03f77e998a")
    public void diffuserEchecMat(Couleur couleur) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i+2) {
          if (listeners[i] == EchecsEventListener.class) {
            ((EchecsEventListener) listeners[i+1]).echecMat(couleur);
          }
        }
    }

    /**
     * Diffuse un évènement nulle à tous les observeurs de la liste
     * @param couleur Couleur des pièces en nulle
     */
    @objid ("cf700a63-989c-47c9-baee-b05a60b48ea8")
    public void diffuserNulle(Couleur couleur) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i+2) {
          if (listeners[i] == EchecsEventListener.class) {
            ((EchecsEventListener) listeners[i+1]).nulle(couleur);
          }
        }
    }

    /**
     * Diffuse un évènement perd au temps à tous les observeurs de la liste
     * @param couleur Couleur du joueur perdant au temps
     */
    @objid ("fca2469c-2f9b-4191-9836-e20526972b55")
    public void diffuserPerdTemps(Couleur couleur) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i+2) {
          if (listeners[i] == EchecsEventListener.class) {
            ((EchecsEventListener) listeners[i+1]).perdTemps(couleur);
          }
        }
    }

    /**
     * Diffuse un évènement tic chrono à tous les observeurs de la liste
     * @param joueur	Joueur ayant recu une mise à jour de son chrono
     */
    @objid ("3ad05ab0-bcf7-4942-a3e5-5da9ba28aafb")
    public void diffuserTicChrono(Joueur joueur) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i+2) {
          if (listeners[i] == EchecsEventListener.class) {
            ((EchecsEventListener) listeners[i+1]).ticChrono(joueur);
          }
        }
    }

}
