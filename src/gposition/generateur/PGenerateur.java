package gposition.generateur;

import gposition.GCoups;
import gposition.GPosition;
import java.util.ArrayList;

public class PGenerateur extends AbstractGenerateur implements IGcle {

    public PGenerateur(GPosition g_position, int couleur) {
        super(g_position, couleur);
        pseudoCoups = new ArrayList<>();
    }
    @Override
    public ArrayList<GCoups> getPseudoCoups() {
        return pseudoCoups;
    }
    @Override
    public void getPseudoCoupsAttaque() {
        recherchePionAttaqueRoque = true;
        pseudoCoups();
    }
    @Override
    public void pseudoCoups() {

        for (int caseO : CASES)
            if (pieceQuiALeTrait(caseO))
                switch (typeDePiece(caseO)) {
                    case PION:
                        pseudoCoupsPion(caseO, recherchePionAttaqueRoque);
                        break;
                    case ROI:
                        pseudoCoupsNonGlissant(caseO, DIR_ROI);
                        break;
                    case CAVALIER:
                        pseudoCoupsNonGlissant(caseO, DIR_CAVALIER);
                        break;
                    case TOUR:
                        pseudoCoupsGlissant(caseO, DIR_TOUR);
                        break;
                    case FOU:
                        pseudoCoupsGlissant(caseO, DIR_FOU);
                        break;
                    case DAME:
                        pseudoCoupsGlissant(caseO, DIR_DAME);
                        break;
                    default:
//                        return null; //erreur, à traiter
                }
    }
    @Override
    public void pseudoCoupsDirection(int caseO, int direction) {
        int caseX = caseO + direction;
        int etatX = etats[caseX];
        while (etatX == VIDE) {
            ajouterCoups(caseO, caseX, TYPE_DE_COUPS.Deplacement);
            caseX = caseX + direction;
            etatX = etats[caseX];
        }
        if (pieceAdverse(caseX))
            ajouterCoups(caseO, caseX, TYPE_DE_COUPS.Prise);
    }
    @Override
    public void pseudoCoupsGlissant(int caseO, int[] directionsPiece) {
        for (int directionX : directionsPiece)
            pseudoCoupsDirection(caseO, directionX);
    }
    @Override
    public void pseudoCoupsNonGlissant(int caseO, int[] directionsPiece) {
        int caseX;
        for (int directionX : directionsPiece) {
            caseX = caseO + directionX;
            if (estVide(caseX))
                ajouterCoups(caseO, caseX, TYPE_DE_COUPS.Deplacement);
            else if (pieceAdverse(caseX))
                    ajouterCoups(caseO, caseX, TYPE_DE_COUPS.Prise);
        }
    }
    @Override
    public void pseudoCoupsPion(int caseO, boolean recherchePionAttaqueRoque) {
        int NordSudSelonCouleur = couleur == BLANC ? nord : sud;
        // avances du pion
        int caseX = caseO + NordSudSelonCouleur;
        if (estVide(caseX)) {
            if (rangFinal(caseX))
                pseudoCoupsPromotion(caseO, caseX, 0);
            else
                add(new GCoups(PION, caseO, caseX, 0, TYPE_DE_COUPS.Deplacement));
            if (rangInitial(caseO)) {
                caseX = caseO + 2 * NordSudSelonCouleur;
                if (estVide(caseX))
                    add(new GCoups(PION, caseO, caseX, 0, TYPE_DE_COUPS.Deplacement));
            }
        }
        // diagonales du pion (prise)
        if (!recherchePionAttaqueRoque) {
            diagonalePionPrise(caseO, NordSudSelonCouleur, est);
            diagonalePionPrise(caseO, NordSudSelonCouleur, ouest);
        } else {
            // diagonales du pion (attaque)
            diagonalePionAttaqueRoque(caseO, NordSudSelonCouleur, est);
            diagonalePionAttaqueRoque(caseO, NordSudSelonCouleur, ouest);
        }
    }
    @Override
    public void diagonalePionAttaqueRoque(int caseO, int NordSudSelonCouleur, int estOuOuest) {
        int caseX = caseO + NordSudSelonCouleur + estOuOuest;
        if (existe(caseX))
            add(new GCoups(PION, caseO, caseX, etats[caseX], TYPE_DE_COUPS.Attaque));
    }
    @Override
    public void diagonalePionPrise(int caseO, int NordSudSelonCouleur, int estOuOuest) {
        int caseX = caseO + NordSudSelonCouleur + estOuOuest;
        if (pieceAdverse(caseX))
            if (rangFinal(caseX))
                pseudoCoupsPromotion(caseO, caseX, etats[caseX]);
            else
                add(new GCoups(PION, caseO, caseX, etats[caseX], TYPE_DE_COUPS.Prise));
    }
    @Override
    public void pseudoCoupsPromotion(int caseO, int caseX, int pieceprise) {
        add(new GCoups(PION, caseO, caseX, pieceprise, TYPE_DE_COUPS.Promotion, couleur * FOU));
        add(new GCoups(PION, caseO, caseX, pieceprise, TYPE_DE_COUPS.Promotion, couleur * CAVALIER));
        add(new GCoups(PION, caseO, caseX, pieceprise, TYPE_DE_COUPS.Promotion, couleur * DAME));
        add(new GCoups(PION, caseO, caseX, pieceprise, TYPE_DE_COUPS.Promotion, couleur * TOUR));
    }
}