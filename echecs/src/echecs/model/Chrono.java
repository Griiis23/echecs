package echecs.model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import javax.swing.Timer;
import com.modeliosoft.modelio.javadesigner.annotations.objid;

/**
 * Classe gérant les pendules du jeu
 *
 */
@objid ("3a08ea65-2e7f-48c0-932a-8496861c4c66")
public class Chrono implements Serializable {
	
    /**
     * Temps restant sur la pendule en ms
     */
    @objid ("2e563bbc-0a0d-4f73-9154-a748abfbdaed")
    private long restant;
    
    /**
     * Temps ajouté à chaque fin de tour en ms
     */
    @objid ("6fabcf39-92ff-43ed-bb05-bcb3d18a2373")
    private final int fisher;

    /**
     * Horodatage de la dernière fois ou le temps restant à été mis à jour 
     * en ms depuis le 1er Janvier 1970
     */
    @objid ("8f373864-6094-4194-8db2-612bf5e5cd0c")
    private long dernierTic;

    /**
     * Java swing timer émettant un event tous les 1000 ms
     */
    @objid ("eeace519-9f5a-484c-baf8-66d5f9fe1b2a")
    private transient Timer timer;

    /**
     * Référence du joueur propréitaire du pendule 
     */
    @objid ("a713a5cc-2369-43ea-bb60-809657aeff0f")
    private Joueur joueur;

    
    /**
     * Constructeur
     * 
     * @param joueur Référence du joueur propréitaire du pendule 
     * @param cadence Temps de reflexion donné au joueur en ms
     * @param fisher Temps ajouté à chaque fin de tour en ms
     */
    @objid ("d7744900-2964-4c35-aa7f-8a96312615cb")
    public Chrono(Joueur joueur, int cadence, int fisher) {
        this.restant = cadence * 60 * 1000;
        this.fisher = fisher;
        this.joueur = joueur;
    }

    /**
     * Démarre le pendule et met à jour le temps restant tous les 1000 ms
     */
    @objid ("d757cd98-bf3b-4157-80e3-dcb262700f25")
    public void start() {
        this.dernierTic = System.currentTimeMillis();
        
        if (this.timer == null) {
            this.timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    long maintenant = System.currentTimeMillis();
                    long diff = maintenant - dernierTic;
                   
                    restant = restant - diff;
                    dernierTic = maintenant;
                    
                    joueur.getJeu().getNotifieur().diffuserTicChrono(joueur);
                    
                    if (restant <= 0) {
                        joueur.getJeu().setEstTermine(true);
                        joueur.getJeu().getNotifieur().diffuserPerdTemps(joueur.getCouleur());
                        timer.stop();
                    }
                }
            });
        }
        
        timer.start();
    }

    /**
     * Ajoute le fisher et stop le timer
     */
    @objid ("3bb6af09-9c40-4e7b-adc2-2aa96ac7b327")
    public void pause() {
        this.restant += this.fisher * 1000;
        this.timer.stop();
        this.joueur.getJeu().getNotifieur().diffuserTicChrono(joueur);
    }

    /**
     * Renvoie le chrono sous forme d'une chaine
     */
    @objid ("4f242afc-ca0a-40e9-83d1-2a879e7d2e97")
    public String toString() {
        long time = restant;
        int minutes = (int) (time / (60 * 1000));
        time = Math.round(time % (60 * 1000));
        int secondes = (int) (time / 1000);
        return minutes + ":" + (secondes < 10 ? "0" + secondes : secondes);
    }

}
