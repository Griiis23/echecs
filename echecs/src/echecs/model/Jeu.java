package echecs.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import echecs.event.EchecsEventListener;
import echecs.event.EchecsEventNotifieur;
import echecs.model.Coup.Type;
import echecs.model.piece.Piece.Couleur;
import echecs.model.piece.Piece;
import echecs.model.piece.Roi;
import echecs.model.piece.Tour;

/**
 * Classe système gérant le jeu
 *
 */
@objid ("e665f07f-140d-4072-a4e7-be4727192457")
public class Jeu implements Serializable {
	
    /**
     * Niveau de difficulté de l'IA, détermine la profondeur de recherche
     */
    @objid ("cc03d538-4784-4fb0-928a-35a5e3e6f342")
    private int difficulteIA = 1;

    /**
     * Couleur du jour devant jouer
     */
    @objid ("1d273a1c-5eb5-4b7c-90a3-26f3ad7c9ff7")
    private Couleur tour = Piece.Couleur.BLANC;

    /**
     * Vrai si la partie est terminé (par mat, pat ou temps...)
     */
    @objid ("5283b3e5-024d-431d-aa22-3d31a241666c")
    private boolean estTermine = false;

    /**
     * Liste des coups joués
     */
    @objid ("3c2c52e5-a409-49fd-82b3-837070a3fbab")
    private List<Coup> coups = new ArrayList<Coup> ();

    /**
     * Référence du plateau du jeu
     */
    @objid ("931b1574-4f6a-42bf-8187-7aaa71c5d42b")
    private Plateau plateau = new Plateau(this);

    /**
     * Notifieur d'évènement, notifie les vues 
     */
    @objid ("051c4010-d131-4925-bf77-711a928b7753")
    private transient EchecsEventNotifieur notifieur = new EchecsEventNotifieur();

    /**
     * Référence du joueur Blanc
     */
    @objid ("17ff48c3-8b79-4dd9-9258-6298dfc64f9a")
    private Joueur joueurBlanc;

    /**
     * Référence du joueur Noir
     */
    @objid ("01247abc-a8c2-4f1b-94b6-52f4fbb5e1ce")
    private Joueur joueurNoir;
    
    
    /**
     * Constructeur
     */
    @objid ("824891f7-ba7b-42d3-93d8-210c8d227968")
    public Jeu() {
    	
    }

    /**
     * Constructeur
     * @param plateau	Chaine de charactère représentant une position de départ dans un format inspiré par la notation Forsyth-Edwards
     */
    @objid ("a8699208-a34a-4ccc-af8f-827fb4131968")
    public Jeu(String plateau) {
        this.plateau = new Plateau(this, plateau);
    }

    /**
     * Démarre le chrono du joueur courant et l'ia
     */
    @objid ("c8fb270c-05a9-4055-9d57-056895fa81ff")
    public void demarrerPartie() {
        if(!this.estTermine) {
            //On recupère le joueur devant jouer
            Joueur j = this.getJoueur(tour);
            if (j.getChrono() != null) j.getChrono().start();
            if(j instanceof IA) ((IA)j).jouer();
        }
    }

    /**
     * Tente de déplacer une piece aux coordonnées données.
     * Cette méthode est utilisée par l'interface pour demander des déplacements. 
     * Si le coup demandé est une promotion, le notifieur diffuse un event promotion
     * @param piece	Piece à déplacer
     * @param x		Colonne
     * @param y		Ligne
     * @return Vrai si le déplacement à réussi. Faux si le déplacement est illégale
     */
    @objid ("753c025c-88bb-4e3c-a3a9-486c2aefba87")
    public boolean faireDeplacement(Piece piece, int x, int y) {
        //Si le coup fait partie de la liste des coups légaux de cette pièce
        ArrayList<Coup> coups = piece.getCoupsLegaux();
        for (Coup coup : coups) {
            if(x == coup.getCaseArrivee().getX() && y == coup.getCaseArrivee().getY()) {
                if (coup.getType()==Type.PROMOTION) this.notifieur.diffuserPromotion(coup);
                faireDeplacement(coup);
                return true;
            }
        }
        this.notifieur.diffuserDeplacementIllegal();
        return false;
    }

    /**
     * Effectue le déplacement passé en paramètre
     * @param coup	Le coup à faire
     */
    @objid ("773a849d-1db0-4b64-a4c1-e778e71099fe")
    public void faireDeplacement(Coup coup) {
        //Si il y a capture
        if(coup.getPieceCapturee() != null) {
            this.plateau.getPiecesPrises().add(coup.getPieceCapturee());
            this.plateau.removePiece(coup.getPieceCapturee().getX(), coup.getPieceCapturee().getY());
        }
        
        //On déplace la pièce
        this.plateau.removePiece(coup.getPieceDeplacee().getX(), coup.getPieceDeplacee().getY());
        this.plateau.setPiece(coup.getCaseArrivee().getX(), coup.getCaseArrivee().getY(), coup.getPieceDeplacee());
        
        //Si il s'agit d'un roque
        if (coup.getType() == Coup.Type.GRAND_ROQUE) {
            Tour tour = (Tour) this.plateau.getPiece(0, coup.getPieceDeplacee().getY());
            this.plateau.removePiece(0, tour.getY());
            this.plateau.setPiece(3, tour.getY(),tour);
        } 
        else if (coup.getType() == Coup.Type.PETIT_ROQUE) {
            Tour tour = (Tour) this.plateau.getPiece(7, coup.getPieceDeplacee().getY());
            this.plateau.removePiece(7 , tour.getY());
            this.plateau.setPiece(5, tour.getY(),tour);
        }
        else if (coup.getType() == Coup.Type.PROMOTION) {
            this.promouvoirPion(coup.getPieceDeplacee(), coup.getTypePiecePromu());
        }
        
        this.coups.add(coup);
    }

    /**
     * Annule le dernier déplacement effectué.
     */
    @objid ("76ec5f92-fb5e-4f35-a634-652721b7c682")
    public void annulerDeplacement() {
        //On recupère le dernier coup
        Coup coup = this.getDernierCoup();
        //Si il n'y a pas de coup a annuler
        if (coup == null) return;
        this.coups.remove(this.coups.size() - 1);
        
        if (coup.getType() == Coup.Type.GRAND_ROQUE) {
            Piece roi = coup.getPieceDeplacee();
            Tour tour;
            tour = (Tour) this.plateau.getPiece(3, roi.getY());
            this.plateau.removePiece(3, roi.getY());
            this.plateau.setPiece(0, roi.getY(), tour);
        }
        else if (coup.getType() == Coup.Type.PETIT_ROQUE) {
            Piece roi = coup.getPieceDeplacee();
            Tour tour;
            tour = (Tour) this.plateau.getPiece(5, roi.getY());
            this.plateau.removePiece(5, roi.getY());
            this.plateau.setPiece(7, roi.getY(), tour);
        }
        this.plateau.removePiece(coup.getCaseArrivee().getX(), coup.getCaseArrivee().getY());
        this.plateau.setPiece(coup.getCaseDepart().getX(), coup.getCaseDepart().getY(), coup.getPieceDeplacee());
        
        Piece capture = coup.getPieceCapturee();
        if (capture != null)
            this.plateau.setPiece(capture.getX(), capture.getY(), capture);
            this.plateau.getPiecesPrises().remove(capture);
    }

    /**
     * Met en pause les pendules, vérifie les échecs et change le joueur courant, démarre l'ia si necessaire
     */
    @objid ("ecd4cdb1-7bae-4b12-b611-b2591cb55255")
    public void terminerTour() {
        //Si la partie est deja fini on ne fait rien
        if(this.estTermine()) return;
        
        //On met en pause le chrono
        Joueur j = this.getJoueur(tour);
        if (j.getChrono() != null) j.getChrono().pause();
        
        //On change de tour
        tour = tour == Couleur.BLANC ? Couleur.NOIR : Couleur.BLANC;
        
        //On verifie les echecs
        Roi roi = this.plateau.getRoi(tour);
        if(roi.estEchec()) {
            if(roi.estEchecMat()) {
                // Partie terminee
                getDernierCoup().setEstEchecMat(true);
                this.notifieur.diffuserPieceDeplacee();
                this.getNotifieur().diffuserEchecMat(this.tour);
                this.estTermine = true;
            }
            else {
                getDernierCoup().setEstEchec(true);
                this.notifieur.diffuserPieceDeplacee();
                this.notifieur.diffuserEchec(this.tour);
            }
        }
        else if(roi.estPat()) {
            this.notifieur.diffuserPieceDeplacee();
            this.notifieur.diffuserNulle(this.tour);
            this.estTermine = true;
        }
        else {
            this.notifieur.diffuserPieceDeplacee();
        }
        
        if(this.plateau.getPieces().size()==2) {
            this.notifieur.diffuserNulle(this.tour);
            this.estTermine = true;
        }
        
        //Si la partie n'est pas fini
        if(!estTermine) {
            j = this.getJoueur(tour);
            if (j.getChrono() != null) j.getChrono().start();
            if (j instanceof IA) ((IA)j).jouer();
        }
    }

    /**
     * Remplace le pion passé en paramètre par une pièce du type donné
     * @param piece	Piece à promouvoir
     * @param type	Type de la pièce
     */
    @objid ("80391237-d702-4c3b-8963-9902094d1a3a")
    public void promouvoirPion(Piece piece, String type) {
        try {
            //On recupère la class
            Class<?> c = Class.forName("echecs.model.piece."+type);
            //On récupère son constructeur
            Class<?>[] parametres = {Plateau.class, Couleur.class, int.class, int.class};
            Constructor<?> constructor = c.getDeclaredConstructor(parametres);
            //On créer la nouvelle pièce
            Piece nouvelle = (Piece) constructor.newInstance(this.plateau, piece.getCouleur(), piece.getX(), piece.getY());
            this.plateau.setPiece(piece.getX(), piece.getY(), nouvelle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sérialise le jeu et l'enregistre dans le fichier donné
     * @param fichier		Fichier où écrire notre sauvegarde
     * @throws Exception	IOException
     */
    @objid ("2c037af5-18b7-4dfc-a64c-610f0cc5f67e")
    public void sauvegarder(File fichier) throws Exception {
        FileOutputStream fos = new FileOutputStream(fichier);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        oos.close();
    }

    // Getters & setters
    
    @objid ("75142215-9935-48a6-8c6b-0b3a9fd0b396")
    public void addEchecsEventListener(EchecsEventListener listener) {
        notifieur.addEchecsEventListener(listener);
    }

    @objid ("5b3be10a-3364-4b24-97f7-6c36ff7108d7")
    public void removeEchecsEventListener(EchecsEventListener listener) {
        notifieur.removeEchecsEventListener(listener);
    }

    @objid ("1c62413f-c93f-4c22-94d5-aaf613cea5b9")
    public Coup getDernierCoup() {
        if (this.coups.isEmpty()) return null;
        return this.coups.get(this.coups.size()-1);
    }

    @objid ("40e98c83-0dfc-4a22-b8d2-8ea09e4f4322")
    public int getDifficulteIA() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.difficulteIA;
    }

    @objid ("e81cbbfb-195a-4035-98fb-827715736e35")
    public void setDifficulteIA(int value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.difficulteIA = value;
    }

    @objid ("d28b3a66-f5fc-4f0d-bfbe-c411db434ec8")
    public Plateau getPlateau() {
        return plateau;
    }

    @objid ("7c28c674-e4e4-4413-b475-e316d4642214")
    public Couleur getTour() {
        return tour;
    }

    @objid ("ee1c3de3-3c30-43bc-b29f-b6d1b3b9dfd5")
    public List<Coup> getCoups() {
        return coups;
    }

    @objid ("594b9bd4-f9a0-478c-b13a-875d5108ee77")
    public Joueur getJoueur(Couleur couleur) {
        if(couleur == Couleur.BLANC) return joueurBlanc;
        return joueurNoir;
    }

    @objid ("4e1e5923-afec-4bd7-8ae6-902907a804d0")
    public void setJoueurBlanc(Joueur joueurBlanc) {
        this.joueurBlanc = joueurBlanc;
    }

    @objid ("a6b13f2d-434a-433c-be02-83f3286cfb36")
    public void setJoueurNoir(Joueur joueurNoir) {
        this.joueurNoir = joueurNoir;
    }

    @objid ("db830ae4-be1d-4c79-8828-af071dbf5fd7")
    public EchecsEventNotifieur getNotifieur() {
        return notifieur;
    }

    @objid ("def8e644-bcf7-4cb2-9463-c2031b3ae940")
    public void setNotifieur(EchecsEventNotifieur notifieur) {
        this.notifieur = notifieur;
    }

    @objid ("fcbd54de-be62-41f5-80bf-2ec739c874c3")
    public boolean estTermine() {
        return estTermine;
    }

    @objid ("825dbef6-9025-46a1-902c-86df78d0fb3a")
    public void setEstTermine(boolean estTermine) {
        this.estTermine = estTermine;
    }

    @objid ("0c76b93e-cbd1-4979-ab93-3e30c68e51de")
    public void setTour(Couleur tour) {
        this.tour = tour;
    }

}
