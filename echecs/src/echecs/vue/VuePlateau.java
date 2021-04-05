package echecs.vue;

import java.awt.*;
import javax.swing.*;
import javax.swing.JPanel;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import echecs.model.*;
import echecs.model.piece.Piece;

/**
 * Vue du plateau
 *
 */
@objid ("e787c9f6-bc6d-46fb-b2af-aab7ef87f5f7")
public class VuePlateau extends JPanel {
	
    /**
     * Référence de la vue du jeu
     */
    @objid ("80a65fdc-d74c-4f5b-af23-0c671ae2083b")
    private VueJeu vueJeu;

    /**
     * Tableau de case
     */
    @objid ("89995781-7066-4e66-a210-7d8f974b5891")
    private VueCase[][] cases = new VueCase[8][8];

    /**
     * Constructeur
     * @param vueJeu
     */
    @objid ("1895e286-e9e5-4940-a139-973bd4cecd1b")
    public VuePlateau(VueJeu vueJeu) {
        this.vueJeu = vueJeu;
        this.setOpaque(true);
        this.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.BLACK));
        this.init();
        this.update();
    }

    /**
     * Initialise la vue
     */
    @objid ("f07aca52-7dfa-44b5-967c-dec0e608f884")
    private void init() {
        this.setLayout(new GridLayout(8,8));
        int couleur = 0;
        
        for(int i = 7; i >= 0; i--){
            for(int j = 0; j < 8; j++){
                cases[j][i] = new VueCase (this, j, i, couleur);
                cases[j][i].addMouseListener(this.vueJeu.getController());
                if(j != 7) couleur = (couleur+1)%2;
                this.add(cases[j][i]);
            }
        }
    }

    /**
     * Met à jour le plateau
     */
    @objid ("99136606-b22f-43e3-a77a-6dfe76e630d3")
    public void update() {
        Plateau plateau = this.vueJeu.getJeu().getPlateau();
        
        for(int x = 0; x < 8; x++){
            for(int y = 0; y < 8; y++){
                cases[x][y].setEnEvidence(false);
                Piece p = plateau.getPiece(x, y);
                cases[x][y].setPiece(p);
            }
        }
        
        this.repaint();
    }

    /**
     * Met en évidence toutes les cases pouvant être jouée par la pièce donnée
     * @param p Pièce à tester
     */
    @objid ("2756cc3b-ec95-4c40-83fa-55d5504e48e2")
    public void montrerCoupsPossibles(Piece p) {
        for (Coup c : p.getCoupsLegaux()) {
            cases[c.getCaseArrivee().getX()][c.getCaseArrivee().getY()].setEnEvidence(true);
        }
        cases[p.getX()][p.getY()].setEnEvidence(true);
        this.repaint();
    }

    /**
     * Désactive toutes les cases en évidence
     */
    @objid ("36403e62-fa3f-466c-a592-a8534d4ab1a4")
    public void resetCoupsPossibles() {
        for(int i = 7; i >= 0; i--){
            for(int j = 0; j < 8; j++){
                cases[j][i].setEnEvidence(false);
            }
        }
        this.repaint();
    }

}
