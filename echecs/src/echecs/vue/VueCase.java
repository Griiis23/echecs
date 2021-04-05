package echecs.vue;

import java.awt.*;
import java.awt.Color;
import java.awt.Graphics;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import echecs.model.piece.Piece;


/**
 * Vue d'une case de l'échéquier
 *
 */
@objid ("73f86071-aaa4-4dbd-8cb6-b4290ba0a7bc")
public class VueCase extends JPanel {
    
	/**
     * Taille en pixel d'une case
     */
    @objid ("1d97721c-e0ef-45dd-ac63-5f760fba6353")
    private static final int TAILLE = 64;

    /**
     * Colonne où est situé la case
     */
    @objid ("84af7ce8-1b47-4a5d-a734-32dea1c127da")
    private int colonne;

    /**
     * Ligne où est situé la case
     */
    @objid ("8a5b55c1-df58-40bb-9794-0b21b8fedf40")
    private int ligne;

    /**
     * 1 si il s'agit d'une case foncée, 0 si il s'agit d'une case claire
     */
    @objid ("91cbdd62-8a1f-4d53-af0a-d9a74315db8d")
    private int couleur;

    /**
     * Vrai si la case est mise en évidence (change de couleur)
     */
    @objid ("9f41f48a-5b2d-4069-969d-42d1738d6d97")
    private boolean enEvidence;

    
    /**
     * Définie la couleur claire et foncée
     */
    @objid ("300bd678-f1d0-4ae8-b947-0d1ff74c7b43")
    private static final Color[] COULEURS = {Color.WHITE, new Color(0,52,95)};

    /**
     * Référence de la pièce affichée dans la case
     */
    @objid ("401ecabc-e2eb-4a8f-a232-acfa4d50995f")
    private Piece piece;

    /**
     * Référence de la vue du plateau
     */
    @objid ("3416abe8-1a13-4ba2-9160-1688faa65232")
    private VuePlateau vuePlateau;

    /**
     * Constructeur
     * @param vuePlateau	Référence de la vue du plateau
     * @param x				Colonne
     * @param y				Ligne
     * @param couleur		1 si il s'agit d'une case foncée, 0 si il s'agit d'une case claire
     */
    @objid ("00c0562d-e006-432b-aefd-dcf1ce4c46de")
    public VueCase(VuePlateau vuePlateau, int x, int y, int couleur) {
        this.vuePlateau = vuePlateau;
        this.colonne = x;
        this.ligne = y;
        this.couleur = couleur;
        this.setPreferredSize(new Dimension(TAILLE,TAILLE));
    }

    /**
     * Dessine le composant
     */
    @objid ("0b688af4-aea0-4908-afa0-a7981aa764ae")
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
           
        
        g.setColor(COULEURS[couleur]);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(COULEURS[(couleur + 1) % 2]);
        if (this.colonne == 0) g.drawString(this.ligne + 1 + "", 2, 12);
        if (this.ligne == 0) g.drawString((char) (this.colonne + 65) + "", this.getWidth() - 12, this.getHeight() - 2);
        if (this.enEvidence) {
            g.setColor(new Color(0,255,255,128));
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
        
        if(piece != null) {
            Image img;
            String path = "/res/" + piece.getClass().getSimpleName() + piece.getCouleur() + ".png";
            try{
                img = ImageIO.read(getClass().getResource(path));
                g.drawImage(img, 0, 0, this);
            } catch(Exception e) {
                System.out.println("Impossible de charger l'image " + path);
            }
        }
    }
    
    // Getters & setters

    @objid ("db10ccc9-4123-47c2-aa87-42fb4032b1ba")
    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    @objid ("f2188004-c091-4c6e-bc37-89758f657e45")
    public void setEnEvidence(boolean enEvidence) {
        this.enEvidence = enEvidence;
    }

    @objid ("226dacce-ffa8-42f1-a2f3-966ea73a7d50")
    public Piece getPiece() {
        return piece;
    }

    @objid ("7a7162ee-6a7b-478f-83b1-e5124f4b45c3")
    public int getColonne() {
        return colonne;
    }

    @objid ("ce9c5e71-6cf5-434c-a109-a018b9fb8f00")
    public int getLigne() {
        return ligne;
    }

    @objid ("452ee06e-2d0f-4088-a184-fcc842d36341")
    public VuePlateau getVuePlateau() {
        return vuePlateau;
    }

}
