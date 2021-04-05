package echecs;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import echecs.vue.VueMenu;

/**
 * Classe main
 */
@objid ("f7672c2a-612b-43ab-b4f2-eb043b348bc3")
public class EchecsGUI {
	
    /**
     * Lance le menu principal dans le thread graphique
     * @param args
     */
    @objid ("e0f87a7a-ad06-4bc4-be75-044c2b6fbf1b")
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                
                
              new VueMenu(); 
            }
        });
    }

}
