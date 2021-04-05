package echecs.model;

import java.io.Serializable;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import echecs.model.piece.Piece.Couleur;
import echecs.model.piece.Piece;

/**
 * Classe représentant un coup dans le jeu
 *
 */
@objid ("7d0689b4-069f-4033-bc3f-3a5874e3183f")
public class Coup implements Serializable {
	
    /**
     * Vrai si le coup à mis le roi adverse en echec
     */
    @objid ("04f4f51f-d992-47ee-8734-fa934b8e2a0d")
    private boolean estEchec = false;

    /**
     * Vrai si le coup à mis le roi adverse en echec et mat
     */
    @objid ("47b91810-6272-48e5-9539-7f98e169c5f0")
    private boolean estEchecMat = false;

    /**
     * Type du coup
     * @see Type
     */
    @objid ("a7a8c5dc-928c-4e47-89d5-1ce22045da6a")
    private Type type;

    /**
     * Nouveau type du pion promu, null si le coup n'est pas une promotion
     */
    @objid ("6acb6f7f-6684-48dd-b066-a8d8464fd8e8")
    private String typePiecePromu = "Reine";

    /**
     * Coordonnées de la case ou la pièce est déplacée
     */
    @objid ("ad968a7f-2644-4e7a-adba-1e3578aa27a1")
    private Coordonnees caseArrivee;

    /**
     * Coordonées de la case ou la pièce était avant le déplacement
     */
    @objid ("c19cf093-1562-4feb-be33-2d81aeddc426")
    private Coordonnees caseDepart;

    /**
     * Référence de la pièce déplacée
     */
    @objid ("184ef459-1d6b-4e62-a679-600ad1e73a83")
    private Piece pieceDeplacee;

    /**
     * Référence de la pièce capturée, null si le coup n'est pas une prise
     */
    @objid ("8f53418b-1d4b-4dce-a11e-41ee0cb3914f")
    private Piece pieceCapturee;

    /**
     * Constructeur
     * @param type			Type du coup
     * @param pieceDeplacee	Référence de la pièce déplacée
     * @param caseArrivee	Coordonnées de la case ou la pièce est déplacée
     * @param pieceCapturee Référence de la pièce déplacée
     */
    @objid ("a9af1da0-c180-4db7-8ba8-3cd70bc8774b")
    public Coup(Type type, Piece pieceDeplacee, Coordonnees caseArrivee, Piece pieceCapturee) {
        this.type = type;
        this.caseArrivee = caseArrivee;
        this.caseDepart = new Coordonnees(pieceDeplacee.getX(), pieceDeplacee.getY());
        this.pieceDeplacee = pieceDeplacee;
        this.pieceCapturee = pieceCapturee;
    }
    

    /**
     * Renvoie un coup sous la forme d'une chaine
     * @see <a href="https://fr.wikipedia.org/wiki/Notation_alg%C3%A9brique">Notation échecs</a>
     */
    @objid ("bbbfc44d-1c1e-4022-9811-770dba8d9830")
    @Override
    public String toString() {
        String result = "";
        
        result += pieceDeplacee.toString();
        
        if(pieceCapturee!=null) result += caseDepart + "x" + caseArrivee;
        else result += caseDepart + "-" + caseArrivee;
        
        
        if (this.type != null) {
            if (this.type == Type.PETIT_ROQUE) {
                return "O-O";
            }
            if (this.type == Type.GRAND_ROQUE) {
                return "O-O-O";
            }
            if (this.type == Type.PROMOTION)
            {
                if (this.getTypePiecePromu() == "Reine") result+= this.pieceDeplacee.getCouleur() == Couleur.BLANC ? "=♕" : "=♛";
                else if (this.getTypePiecePromu() == "Tour") result+= this.pieceDeplacee.getCouleur() == Couleur.BLANC ? "=♖" : "=♜";
                else if (this.getTypePiecePromu() == "Fou") result+= this.pieceDeplacee.getCouleur() == Couleur.BLANC ? "=♗" : "=♝";
                else if (this.getTypePiecePromu() == "Cavalier") result+= this.pieceDeplacee.getCouleur() == Couleur.BLANC ? "=♘" : "=♞";
            }
        }
        
        
        if(this.estEchecMat) result+= "#";
        else if(this.estEchec) result+= "+";
        return result;
    }

    // Getters & setters
    
    @objid ("3dd055d4-040c-498c-8ab5-581898c07b2a")
    public Type getType() {
        return type;
    }

    @objid ("5f9f9db3-bf2b-4836-a016-a4f0ca796443")
    public Coordonnees getCaseArrivee() {
        return caseArrivee;
    }

    @objid ("bb3ee672-7cd1-4c21-8a7c-fe6c9e77adc8")
    public Coordonnees getCaseDepart() {
        return caseDepart;
    }

    @objid ("58369c1d-f22a-420f-8c93-64573115ab39")
    public Piece getPieceDeplacee() {
        return pieceDeplacee;
    }

    @objid ("aa80f322-112d-4c3a-9e4b-2d80c7e54c25")
    public Piece getPieceCapturee() {
        return pieceCapturee;
    }

    @objid ("0fc3ffcf-1276-4d34-a7ca-762a2e05d479")
    public boolean isEstEchec() {
        return estEchec;
    }

    @objid ("be37006a-aaa2-4781-b1eb-70212ff41f51")
    public void setEstEchec(boolean estEchec) {
        this.estEchec = estEchec;
    }

    @objid ("5befba86-9671-45af-9300-ef574ce51044")
    public boolean isEstEchecMat() {
        return estEchecMat;
    }

    @objid ("09f8f0b1-38dd-4ad9-ba5e-34e023c62f1f")
    public void setEstEchecMat(boolean estEchecMat) {
        this.estEchecMat = estEchecMat;
    }

    @objid ("7df0be53-a606-4f5c-a5d3-0d611fdee59e")
    public String getTypePiecePromu() {
        return typePiecePromu;
    }

    @objid ("8fd43239-435e-482c-90fc-2d0befb3763f")
    public void setTypePiecePromu(String typePiecePromu) {
        this.typePiecePromu = typePiecePromu;
    }

    /**
     * Enumération des types de coup aux echecs
     * 
     */
    @objid ("3fb51c62-733f-4fbb-85b0-7cd645d3720f")
    public enum Type {
        GRAND_ROQUE,
        PETIT_ROQUE,
        PROMOTION,
        ENPASSANT,
        AUTRE;
    }

}
