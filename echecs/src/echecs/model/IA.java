package echecs.model;

import java.util.ArrayList;
import javax.swing.SwingUtilities;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import echecs.model.piece.Piece.Couleur;
import echecs.model.piece.Piece;

/**
 * Classe gérant l'intelligence artificielle
 *
 */
@objid ("41e65045-0f72-4471-b17d-63200621de48")
public class IA extends Joueur {
	
    /**
     * Constructeur
     * @param jeu		Référence du jeu
     * @param couleur	Couleur du joueur
     */
    @objid ("caf41e2e-ea7c-4f59-b130-dbf04868a732")
    public IA(Jeu jeu, Couleur couleur) {
        super(jeu, couleur);
    }

    /**
     * Démarre l'ia dans un nouveau thread
     */
    @objid ("f8c04f70-c93b-4d7d-85f4-cd348baf7f75")
    public void jouer() {
        IAthread thread = new IAthread();
        thread.start();
    }

    /**
     * Premier appel de l'algorithme alpha-beta, lance alpha-beta sur tous les coups possibles 
     * pour trouver le meilleur, celui qui obtient le meilleur score
     * @return	Le meilleur coup
     */
    @objid ("5544ebf1-0cbc-4db4-bb87-f9bff519364d")
    public Coup alphabeta() {
        Coup bestCoup = null;
        int max = Integer.MIN_VALUE;
        int prof = this.getJeu().getDifficulteIA() + 2;
        ArrayList<Coup> coups = this.getJeu().getPlateau().getTousCoupsLegaux(this.getCouleur());
        
        for(Coup coup : coups) {
            this.getJeu().faireDeplacement(coup);
            int score = alphabeta(prof, Integer.MIN_VALUE, Integer.MAX_VALUE, this.getCouleur() == Couleur.BLANC ? Couleur.NOIR : Couleur.BLANC);
            this.getJeu().annulerDeplacement();
        
            if (score >= max) {
                max = score;
                bestCoup = coup;
            }
        }
        return bestCoup;
    }

    /**
     * Algorithme minmax avec élagage alpha-beta
     * @param prof		Pronfondeur de recherche dans l'arbre de jeu
     * @param alpha		Score le plus grand remontée des successeurs
     * @param beta		Score le plus petit remontée des successeurs
     * @param couleur	Couleur du joueur devant jouée sur le noeud courant, permet de déterminer si il s'agit d'un noeud min ou max
     * @return le score du noeud courant
     */
    @objid ("59adc365-a7b7-4b81-81bc-bd6e55fe68d1")
    private int alphabeta(int prof, int alpha, int beta, Couleur couleur) {
        if (prof == 0) return evaluer();
        int m = 0;
        ArrayList<Coup> coups = this.getJeu().getPlateau().getTousCoupsLegaux(couleur);
        // Noeud min
        if(this.getCouleur() != couleur) {
            m = Integer.MAX_VALUE;
            for(Coup coup : coups) {
                this.getJeu().faireDeplacement(coup);
                m = Math.min(m, alphabeta(prof-1, alpha, beta, couleur == Couleur.BLANC ? Couleur.NOIR : Couleur.BLANC));
                this.getJeu().annulerDeplacement();
                if (alpha >= m) return m;
                beta = Math.min(beta, m);
            }
        } 
        // Noeud max
        else {
            m = Integer.MIN_VALUE;
            for(Coup coup : coups) {
                this.getJeu().faireDeplacement(coup);
                m = Math.max(m, alphabeta(prof-1, alpha, beta, couleur == Couleur.BLANC ? Couleur.NOIR : Couleur.BLANC));
                this.getJeu().annulerDeplacement();
                if (m >= beta) return m;
                alpha = Math.max(alpha, m);
            }
        }
        return m;
    }

    /**
     * Evalue la situation actuelle selon 3 critères, le matériel, 
     * la mobilité (le nombre de coup jouables) et la qualité du placement des pièces
     * @return Le score de la situation actuelle
     */
    @objid ("3e8e55e9-57da-4a50-ab11-10b7c8e7fb6c")
    public int evaluer() {
        int score = 0;
        
        for(Piece p : this.getJeu().getPlateau().getPieces()) {
            if(p.getCouleur() == this.getCouleur()) {
                score += p.getValeur() + p.getCoupsPossibles().size() + p.getBonus();
            }
            else {
                score -= p.getValeur() + p.getCoupsPossibles().size() + p.getBonus();
            }
        }
        return score;
    }

    /**
     * Renvoie une chaine de la forme "IA couleur" 
     */
    @objid ("4c5f7f41-961e-4a3c-b208-ac8896b4ddd7")
    public String toString() {
        return "IA " + this.getCouleur().toString().toLowerCase();
    }
    
    
    /**
     * Thread exécutant les calculs de l'ia
     *
     */
    @objid ("50d30ea4-2faf-4d9c-97db-c0a9b337e5b9")
    public class IAthread extends Thread {
        @objid ("543d8b8a-31ef-47be-ba40-b3816f7eab25")
        public void run() {
        	// Détermine le meilleur coup
            Coup coup = alphabeta();
            // Joue le coups si la partie n'est pas terminée
            if(!getJeu().estTermine()) {
                getJeu().faireDeplacement(coup);
                
                // Appel terminerTour dans le thread de l'interface graphique
                SwingUtilities.invokeLater( new Runnable() {
                    public void run() { getJeu().terminerTour(); }
                });
            }
        }

    }

}
