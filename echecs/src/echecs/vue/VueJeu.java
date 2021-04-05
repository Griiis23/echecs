package echecs.vue;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import echecs.controller.ControllerJeu;
import echecs.event.EchecsEventListener;
import echecs.model.Coup;
import echecs.model.Jeu;
import echecs.model.Joueur;
import echecs.model.piece.Piece.Couleur;
import echecs.model.piece.Piece;

/**
 * Fenêtre du jeu
 *
 */
@objid ("2cdd006e-3053-4287-83d2-d3c054e74f56")
public class VueJeu extends JFrame implements EchecsEventListener {
   
	/**
     * Référence du jeu 
     */
    @objid ("7029e968-fa61-4ff6-8ff5-1978caac1434")
    private Jeu jeu;

    /**
     * Controlleur de la vue
     */
    @objid ("b584e128-97ea-4ccb-88ce-f6387aabf5cb")
    private ControllerJeu controller;

    /**
     * Vue du plateau
     */
    @objid ("14b5a28e-5fb3-4892-9333-f55bcd005eeb")
    private VuePlateau vuePlateau;

    /**
     * Label affichant les pièces capturées noires
     */
    @objid ("0e63c994-fda1-47bd-bc38-987b7ecdf9ae")
    private JLabel priseN;

    /**
     * Label affichant les pièces capturées blanches
     */
    @objid ("05c99a21-2f7a-4437-9291-89f7deef296e")
    private JLabel priseB;

    /**
     * Label affichant le chrono du joueur noir
     */
    @objid ("9355b8fb-3880-4bd8-81cd-19808e3f6e4b")
    private JLabel chronoN;

    /**
     * Label affichant le chrono du joueur blanc
     */
    @objid ("48b604d8-0f50-400c-ba8b-221e96e71311")
    private JLabel chronoB;

    /**
     * Contenu du tableau affichant la liste des coups
     */
    @objid ("f73e4972-e125-4219-aa5d-5e9c65a04d04")
    private DefaultTableModel modelTable;

    /**
     * Constructeur
     * @param jeu	Référence au jeu
     */
    @objid ("d70fd5e2-7477-41e1-ad6a-a4b7e35f588c")
    public VueJeu(Jeu jeu) {
        super("Echecs");
        
        this.jeu = jeu;
        this.controller = new ControllerJeu(jeu);
        jeu.addEchecsEventListener(this);
        
        
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.init();
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /**
     * Initialise la vue
     */
    @objid ("3ca9ec5e-3dd4-4977-a0ba-1415c91278ac")
    private void init() {
        JPanel contenu = new JPanel();
        this.setContentPane(contenu);
        contenu.setLayout(new GridBagLayout());
        contenu.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        
        //Vue Plateau
        vuePlateau = new VuePlateau(this);
        
        //Table des coups
        modelTable = new DefaultTableModel();
        modelTable.addColumn(this.jeu.getJoueur(Couleur.BLANC).toString());
        modelTable.addColumn(this.jeu.getJoueur(Couleur.NOIR).toString());
         
        JTable table= new JTable(modelTable);
        table.setRowHeight(30);
        table.setFillsViewportHeight(true);
        table.setEnabled(false);
        JScrollPane jsp = new JScrollPane(table);
        jsp.setPreferredSize(new Dimension(300,200));
        
        //Labels
        priseN = new JLabel();
        priseB = new JLabel();
        chronoN = new JLabel();
        chronoB = new JLabel();
        if (this.jeu.getJoueur(Couleur.NOIR).getChrono() != null) {
            chronoN.setText(this.jeu.getJoueur(Couleur.NOIR).getChrono().toString());
            chronoB.setText(this.jeu.getJoueur(Couleur.BLANC).getChrono().toString());
        }
        
        //Reconstruction en cas de chargement de sauvegarde
        List<Coup> coups = this.jeu.getCoups();
        for (int i = 0; i < coups.size(); i++) {
            Coup coup = coups.get(i);
            
            if (i >= this.modelTable.getRowCount()*2) this.modelTable.addRow(new String[] {"",""});
            
            if (coup.getPieceDeplacee().getCouleur() == Couleur.BLANC) {
                this.modelTable.setValueAt(coup.toString(), this.modelTable.getRowCount()-1, 0);
                
            } else {
                this.modelTable.setValueAt(coup.toString(), this.modelTable.getRowCount()-1, 1);
            }
        
            if (coups.get(i).getPieceCapturee() != null) {
                Piece p = coup.getPieceCapturee();
                if (p.getCouleur() == Couleur.BLANC) priseB.setText(priseB.getText() + p.toString());
                else priseN.setText(priseN.getText() + p.toString());
            }
        }
               
        
        //Barre de Menu
        JMenuBar menuBar=new JMenuBar();
        
        JMenu menu=new JMenu("Fichier");
               
        JMenuItem item = new JMenuItem("Sauvegarder");
        item.setActionCommand("save");
        item.addActionListener(controller);
        menu.add(item);
        item = new JMenuItem("Retourner au menu principal");
        item.setActionCommand("menu");
        item.addActionListener(controller);
        menu.add(item);
        menuBar.add(menu);
        
        menu = new JMenu("Options");
        menuBar.add(menu);
        JMenu subMenu = new JMenu("Difficulté");
        menu.add(subMenu);
        item = new JMenuItem("Facile");
        item.setActionCommand("facile");
        item.addActionListener(controller);
        subMenu.add(item);
        item = new JMenuItem("Normale");
        item.setActionCommand("normale");
        item.addActionListener(controller);
        subMenu.add(item);
        item = new JMenuItem("Difficile");
        item.setActionCommand("difficile");
        item.addActionListener(controller);
        subMenu.add(item);
        
        
        this.setJMenuBar(menuBar);
        
        
        //Placement des composants
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.LINE_START;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        contenu.add(new JLabel(this.jeu.getJoueur(Couleur.NOIR).toString()),gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        contenu.add(new JLabel(this.jeu.getJoueur(Couleur.BLANC).toString()),gbc);
        
        gbc.weightx = 1;
        gbc.gridx = 1;
        gbc.gridy = 0;
        contenu.add(priseB,gbc);
        
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        contenu.add(priseN,gbc);
        
        gbc.weightx = 0;
        gbc.gridx = 2;
        gbc.gridy = 0;
        contenu.add(chronoN,gbc);
        
        gbc.gridx = 2;
        gbc.gridy = 2;
        contenu.add(chronoB,gbc);
               
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridheight = GridBagConstraints.REMAINDER;
        contenu.add(jsp,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        contenu.add(vuePlateau,gbc);
    }

    
    /**
     * Action à déclencher lors d'un évènement promotion
     */
    @objid ("bb1a5eb3-5f00-4b69-a90e-ad68d16ee7cc")
    @Override
    public void promotion(Coup coup) {
        Object[] possibilities = {"Reine", "Tour", "Cavalier", "Fou"};
        String s = (String)JOptionPane.showInputDialog(
                            this,
                            "En quoi promouvoir votre pion ?",
                            "Promotion",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            possibilities,
                            "Reine");
        if ((s != null) && (s.length() > 0)) {            
            coup.setTypePiecePromu(s);
        }
    }

    
    /**
     * Action à déclencher lors d'un évènement déplacement
     */
    @objid ("9c87a540-1892-4851-87a6-07550c388f42")
    @Override
    public void pieceDeplacee() {
        // Met à jour le plateau
    	vuePlateau.update();
        
        // Met à jour la liste de coups
        List<Coup> coups = this.jeu.getCoups();
        Coup coup = coups.get(coups.size()-1);
        
        if (coups.size() > this.modelTable.getRowCount()*2) this.modelTable.addRow(new String[] {"",""});
        
        if (coup.getPieceDeplacee().getCouleur() == Couleur.BLANC) {
            this.modelTable.setValueAt(coup.toString(), this.modelTable.getRowCount()-1, 0);
        
        } else {
            this.modelTable.setValueAt(coup.toString(), this.modelTable.getRowCount()-1, 1);
        }
        
        // Met à jour la liste des pièces capturées
        if (coup.getPieceCapturee() != null) {
            Piece p = coup.getPieceCapturee();
            if (p.getCouleur() == Couleur.BLANC) priseB.setText(priseB.getText() + p.toString());
            else priseN.setText(priseN.getText() + p.toString());
        }
    }

    
    /**
     * Action à déclencher lors d'un évènement déplacement illégal
     */
    @objid ("1fd6d5f1-eb6a-4253-9d42-f0ad3aa4f6af")
    @Override
    public void deplacementIllegal() {
        JOptionPane.showMessageDialog(this, "Déplacement illégal.");
    }

    /**
     * Action à déclencher lors d'un évènement mat
     */
    @objid ("73ed5533-2a32-43c2-88bb-afe92140dd5f")
    @Override
    public void echecMat(Couleur couleur) {
        JOptionPane.showMessageDialog(this, this.jeu.getJoueur(couleur) + " est échec et mat.");
    }

    
    /**
     * Action à déclencher lors d'un évènement echec
     */
    @objid ("b2d0e95a-fb59-40ce-ab12-1d5fca625ca6")
    @Override
    public void echec(Couleur couleur) {
        JOptionPane.showMessageDialog(this, this.jeu.getJoueur(couleur) + " est échec.");
    }

    /**
     * Action à déclencher lors d'un évènement nulle
     */
    @objid ("eada126f-cdb5-4e65-a061-27f52c113bca")
    @Override
    public void nulle(Couleur couleur) {
        JOptionPane.showMessageDialog(this, "Partie nulle");
    }

    /**
     * Action à déclencher lors d'un évènement tic chrono
     */
    @objid ("8e65bc1a-16b5-444b-8cbd-c6c560d57f7d")
    @Override
    public void ticChrono(Joueur joueur) {
        if (joueur.getCouleur() == Couleur.NOIR) {
            chronoN.setText(joueur.getChrono().toString());
        }
        else {
            chronoB.setText(joueur.getChrono().toString());
        }
    }

    /**
     * Action à déclencher lors d'un évènement perd au temps
     */
    @objid ("6838a005-9b0e-4b03-89f9-bfa5671b4a96")
    @Override
    public void perdTemps(Couleur couleur) {
        JOptionPane.showMessageDialog(this, this.jeu.getJoueur(couleur) + " perd au temps.");
    }
    
    // Getters & setters

    @objid ("d2a8d533-f281-40e7-aa19-9f3291cf9ca2")
    public Jeu getJeu() {
        return jeu;
    }

    @objid ("6d3fdd94-c710-44c5-843f-00f3237b0066")
    public ControllerJeu getController() {
        return controller;
    }

}
