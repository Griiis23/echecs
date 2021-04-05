package echecs.controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.xml.parsers.*;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import echecs.event.EchecsEventNotifieur;
import echecs.model.*;
import echecs.model.piece.Piece.Couleur;
import echecs.vue.VueJeu;
import echecs.vue.VueMenu;
import org.w3c.dom.*;

/**
 * Controller du menu principal
 *
 */
@objid ("c4b05d04-e6c8-4c14-ba94-1b9a0f9f941f")
public class ControllerMenu implements ActionListener {
	
	
    /**
     * Action à déclencher
     */
    @objid ("35b80a73-d5ba-45cf-a4b8-88fea5865690")
    @Override
    public void actionPerformed(ActionEvent e) {
    	// On récupère la fenêtre de la source
        JComponent source = (JComponent) e.getSource();
        VueMenu vueMenu = (VueMenu) SwingUtilities.getWindowAncestor(source);
        
        // Clique sur charger
        if(e.getActionCommand() == "charger") { 
        	// Demande le fichier
            JFileChooser chooser = new JFileChooser();
        
            int retour = chooser.showOpenDialog((Component) e.getSource());
            if(retour == JFileChooser.APPROVE_OPTION) {    
                // On tente de charger
                try {
                    FileInputStream fis = new FileInputStream(chooser.getSelectedFile());
                    ObjectInputStream ios = new ObjectInputStream(fis);
                    Jeu jeu = (Jeu)ios.readObject();
                    ios.close();
                    
                    jeu.setNotifieur(new EchecsEventNotifieur());
                    
                    new VueJeu(jeu);
                    vueMenu.dispose();
                    jeu.demarrerPartie();
                    
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog((Component) e.getSource(),
                            "Impossible de charger " + chooser.getSelectedFile().getName(),
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                    exception.printStackTrace();
                }
            }
            
        }
        // Clique sur défi
        else if(e.getActionCommand() == "defi") {
            // On tente de lire le fichier XML
            try {
                File file = new File(getClass().getResource("/res/defi.xml").getFile());
                
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(file);
                
                //On lit les défis
                NodeList defis = doc.getElementsByTagName("defi");
                
                //On choisi un defi au hazard
                Element defi = (Element) defis.item((int) (Math.random() * defis.getLength()));
                
                String message = defi.getElementsByTagName("message").item(0).getTextContent();
                String plateau = defi.getElementsByTagName("plateau").item(0).getTextContent();
                String tour = defi.getElementsByTagName("tour").item(0).getTextContent();
                
                //On créer notre modèle
                Jeu jeu = new Jeu(plateau);
        
                if (tour.equals("w")) {
                    jeu.setTour(Couleur.BLANC);
                    jeu.setJoueurBlanc(new JoueurHumain(jeu, Couleur.BLANC));
                    jeu.setJoueurNoir(new IA(jeu, Couleur.NOIR));
                } else {
                    jeu.setTour(Couleur.NOIR);
                    jeu.setJoueurBlanc(new IA(jeu, Couleur.BLANC));
                    jeu.setJoueurNoir(new JoueurHumain(jeu, Couleur.NOIR));
                }
            
                vueMenu.dispose();
        
                VueJeu vue = new VueJeu(jeu);
                JOptionPane.showMessageDialog(vue, message);
        
                jeu.demarrerPartie();
                
            } catch (Exception exception) {
                JOptionPane.showMessageDialog((Component) e.getSource(),
                        "Impossible de charger les défis",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                exception.printStackTrace();
            }
            
        }
        // Clique sur démarrer
        else {
            //On determine le chrono
            int choixChrono = vueMenu.getChoixChrono().getSelectedIndex();
            
            //On créer notre modèle
            Jeu jeu = new Jeu();
            
            if(e.getActionCommand() == "IA") {
                //On configure la difficulte
                int choixDifficulte = vueMenu.getChoixDifficulte().getSelectedIndex();
                jeu.setDifficulteIA(choixDifficulte);
                
                //On configure la couleur du joueur humain contre l'ia
                String choixCouleur = vueMenu.getChoixCouleur().getSelection().getActionCommand();
                if (choixCouleur == "Blanc") {
                    jeu.setJoueurBlanc(new JoueurHumain(jeu, Couleur.BLANC));
                    jeu.setJoueurNoir(new IA(jeu, Couleur.NOIR));
                } else {
                    jeu.setJoueurBlanc(new IA(jeu, Couleur.BLANC));
                    jeu.setJoueurNoir(new JoueurHumain(jeu, Couleur.NOIR));
                }
            }
            else {
                jeu.setJoueurBlanc(new JoueurHumain(jeu, Couleur.BLANC));
                jeu.setJoueurNoir(new JoueurHumain(jeu, Couleur.NOIR));
            }
            
            Joueur jb = jeu.getJoueur(Couleur.BLANC);
            Joueur jn = jeu.getJoueur(Couleur.NOIR);
            
            switch (choixChrono) {
            case 0:
                jb.setChrono(new Chrono(jb,5,0));
                jn.setChrono(new Chrono(jn,5,0));
                break;
            case 1:
                jb.setChrono(new Chrono(jb,10,0));
                jn.setChrono(new Chrono(jn,10,0));
                break;
            case 2:
                jb.setChrono(new Chrono(jb,30,0));
                jn.setChrono(new Chrono(jn,30,0));
                break;
            case 3:
                jb.setChrono(new Chrono(jb,5,5));
                jn.setChrono(new Chrono(jn,5,5));
                break;
            case 4:
                jb.setChrono(new Chrono(jb,10,2));
                jn.setChrono(new Chrono(jn,10,2));
                break;
            case 5:
                jb.setChrono(new Chrono(jb,15,2));
                jn.setChrono(new Chrono(jn,15,2));
                break;
            default:
                break;
            }            
            
            vueMenu.dispose();
            new VueJeu(jeu);
            jeu.demarrerPartie();
        }
    }

}
