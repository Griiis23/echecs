package echecs.controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import echecs.model.*;
import echecs.model.Jeu;
import echecs.model.piece.Piece;
import echecs.vue.*;

/**
 * Controlleur de la vue du jeu
 *
 */
@objid ("8d424888-c517-4c9e-9071-9e71a65e2e9b")
public class ControllerJeu implements ActionListener, MouseListener {
    
	/**
     * Référence du jeu
     */
    @objid ("eec5c2c8-93bb-4913-bdb4-27c58c2be36b")
    private Jeu jeu;

    /**
     * Référence de la pièce selectionnée
     */
    @objid ("dffb9086-e678-4b56-8ee8-594d86fb2926")
    private Piece selection;

    /**
     * Constructeur
     * @param jeu	Référence du jeu
     */
    @objid ("bfda98ed-d1c8-4179-b47d-a5ab231cee1e")
    public ControllerJeu(Jeu jeu) {
        this.jeu = jeu;
    }

    /**
     * Action à declencher
     */
    @objid ("902a98c2-6b41-41fa-aade-a45fefb43608")
    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getActionCommand() == "facile") {
            jeu.setDifficulteIA(0);
        }
        else if (event.getActionCommand() == "normale") {
            jeu.setDifficulteIA(1);
        }
        else if (event.getActionCommand() == "difficile") {
            jeu.setDifficulteIA(2);
        }
        else if (event.getActionCommand() == "save") {
        
            if (this.jeu.getJoueur(this.jeu.getTour()) instanceof IA) {
                JOptionPane.showMessageDialog((Component) event.getSource(),
                        "Impossible de sauvegarder durant le tour le l'IA.",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        
            JFileChooser chooser = new JFileChooser();
        
            int retour = chooser.showSaveDialog((Component) event.getSource());
            if(retour == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
        
                try {
                    this.jeu.sauvegarder(file);
        
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog((Component) event.getSource(),
                            "Impossible de sauvegarder.",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                    exception.printStackTrace();
                }
            }
        }
        else if (event.getActionCommand() == "menu") {
            jeu.setEstTermine(true);
            Joueur j = jeu.getJoueur(jeu.getTour());
            if (j.getChrono() != null) j.getChrono().pause();
            
            JMenuItem source = (JMenuItem) event.getSource();
            JPopupMenu parent = (JPopupMenu) source.getParent();
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(parent.getInvoker());
            
            
            new VueMenu();
            frame.dispose();
        }
    }

    /**
     * Action à declencher
     */
    @objid ("7591ff24-3d90-431b-bdb4-17516869b903")
    @Override
    public void mouseClicked(MouseEvent event) {
        VueCase source = (VueCase) event.getSource();
          
        //On verifie si le joueur courant est humain et que la partie n'est pas terminée
        if (this.jeu.getJoueur(this.jeu.getTour()) instanceof IA || this.jeu.estTermine()) {
            selection = null;
            return;
        }
        
        //Si aucune piece n'est déja selectionné
        if(this.selection == null) {
            if(source.getPiece() != null && source.getPiece().getCouleur() == jeu.getTour()) {
                this.selection = source.getPiece();
                source.getVuePlateau().montrerCoupsPossibles(source.getPiece());;
            }
        }
        else {
            source.getVuePlateau().resetCoupsPossibles();
            //Si la case cliqué est différente de celle selectionné
            if(this.selection != source.getPiece() && selection.getCouleur() == jeu.getTour()) {
                //On tente le déplacement
                if(jeu.faireDeplacement(this.selection, source.getColonne(), source.getLigne()))
                    jeu.terminerTour();
            }
            this.selection = null;
        }
    }

    @objid ("e9d11447-f414-43de-8aab-8c2d6bf45c2b")
    @Override
    public void mouseEntered(MouseEvent event) {
        // TODO Auto-generated method stub
    }

    @objid ("72672738-8433-421b-b106-a523ca77295f")
    @Override
    public void mouseExited(MouseEvent event) {
        // TODO Auto-generated method stub
    }

    @objid ("f8f7b9b5-c0c8-4ad6-9fe1-1558d6d05655")
    @Override
    public void mousePressed(MouseEvent event) {
        // TODO Auto-generated method stub
    }

    @objid ("92b708c5-156d-4b52-9ad3-0de96a64881d")
    @Override
    public void mouseReleased(MouseEvent event) {
        // TODO Auto-generated method stub
    }

}
