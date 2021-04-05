package echecs.vue;

import java.awt.*;
import javax.swing.*;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import echecs.controller.ControllerMenu;

/**
 * Fenêtre du menu principal
 *
 */
@objid ("4acffc95-e3de-42a6-a10a-fb5beb6fa060")
public class VueMenu extends JFrame {
    /**
     * Groupe de coche pour le choix de couleur des pièces contre l'ia
     */
    @objid ("0efca88c-d7f2-49a8-ac5b-34d40ef5ea74")
    private ButtonGroup choixCouleur;

    /**
     * Menu déroullant pour le choix du chrono
     */
    @objid ("53e1ccf8-9d21-4b99-b81c-4178793b63d1")
    private JComboBox<String> chrono;

    /**
     * Menu déroullant pour le choix de la difficulté contre l'ia
     */
    @objid ("2f5895cc-e88b-4567-8e6f-efddfb086a6e")
    private JComboBox<String> choixDifficulte;

    /**
     * Boutton démarrer une partie contre l'ia
     */
    @objid ("10eb02e8-0581-479e-9e98-3da92449f8f1")
    private JButton button1;

    /**
     * Boutton démarrer une partie contre un joueur humain
     */
    @objid ("2ed1ce3b-ac78-4131-997a-e0e9e0121531")
    private JButton button2;

    /**
     * Boutton démarrer un défi
     */
    @objid ("fe4ecccc-b59a-4056-a954-7855fe04fb7d")
    private JButton button3;

    /**
     * Controlleur du menu
     */
    @objid ("f9431b5b-06b0-46ef-8512-b4364c0b0d5f")
    private ControllerMenu controller;

    /**
     * Boutton charger une partie
     */
    @objid ("3e54339c-5ed9-46e2-ae23-fc249428c7a7")
    private JButton button4;

    /**
     * Construteur
     */
    @objid ("77a6cca1-011e-4cb1-871e-1109aebf30a9")
    public VueMenu() {
        super("Echecs");
        
        this.controller = new ControllerMenu();
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.init();
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /**
     * Initialise la vue
     */
    @objid ("c54a905d-308a-4eee-a4f2-6ee38b1b016e")
    private void init() {
        JPanel contenu = new JPanel();
        this.setContentPane(contenu);
        contenu.setLayout(new GridBagLayout());
        contenu.setBorder(BorderFactory.createEmptyBorder(50,100,50,100));
        
        JLabel titre = new JLabel("Jeu d'échecs");
        titre.setFont(new Font("Verdana", Font.PLAIN, 32));
        
        choixCouleur = new ButtonGroup();
        JRadioButton coche1 = new JRadioButton("Blanc");
        coche1.setActionCommand("Blanc");
        choixCouleur.add(coche1);
        coche1.setSelected(true);
        JRadioButton coche2 = new JRadioButton("Noir");
        coche2.setActionCommand("Noir");
        choixCouleur.add(coche2);
        
        String[] items = {"5 min","10 min","30 min", "5 | 5", "10 | 2", "15 | 2", "Aucune"};
        chrono = new JComboBox<String>(items);
               
        String[] items2 = {"Facile","Normale","Difficile"};
        choixDifficulte = new JComboBox<String>(items2);
        choixDifficulte.setSelectedIndex(1);
        
        
        button1 = new JButton("Démarrer une partie contre l'IA");
        button1.setActionCommand("IA");
        button1.addActionListener(controller);
        
        button2 = new JButton("Démarrer une partie 2 joueurs");
        button2.setActionCommand("2joueurs");
        button2.addActionListener(controller);
        
        button3 = new JButton("Démarrer un défi");
        button3.setActionCommand("defi");
        button3.addActionListener(controller);
        
        button4 = new JButton("Charger une partie");
        button4.setActionCommand("charger");
        button4.addActionListener(controller);
        
        //Placement des composants
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.insets = new Insets(5, 5, 30, 5);
        
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        contenu.add(titre,gbc);
        
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.anchor = GridBagConstraints.BASELINE_LEADING;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        contenu.add(button1,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        contenu.add(button2,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        contenu.add(button3,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        contenu.add(button4,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        contenu.add(new JLabel("Couleur de vos pièces contre l'IA"),gbc);
        
        gbc.gridx = 1; 
        gbc.gridy = 5;
        contenu.add(coche1,gbc);
        gbc.gridx = 2; 
        gbc.gridy = 5;
        contenu.add(coche2,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 6; 
        contenu.add(new JLabel("Pendule"),gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.BOTH;
        contenu.add(chrono,gbc);
        
        
        gbc.gridx = 0;
        gbc.gridy = 7; 
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        contenu.add(new JLabel("Difficulté de l'IA"),gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.BOTH;
        contenu.add(choixDifficulte,gbc);
    }

    // Getters & setters
    
    @objid ("cfb8e7d2-955b-4d8a-87ef-ac802e536960")
    public ButtonGroup getChoixCouleur() {
        return choixCouleur;
    }

    @objid ("5a1eeb3b-5597-4ee0-bfc7-271a7f7a7295")
    public JComboBox<String> getChoixChrono() {
        return chrono;
    }

    @objid ("af667abe-5f6f-433c-9325-e896687527c9")
    public JComboBox<String> getChoixDifficulte() {
        return choixDifficulte;
    }

    @objid ("adf8a4c7-0db5-48fc-b9b2-5e20cabdda90")
    public ControllerMenu getController() {
        return controller;
    }

}
