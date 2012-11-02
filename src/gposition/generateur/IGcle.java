package gposition.generateur;

import gposition.GCoups;
import java.util.*;

public interface IGcle {

    void diagonalePionAttaqueRoque(int caseO, int NordSudSelonCouleur, int estOuOuest);
    void diagonalePionPrise(int caseO, int NordSudSelonCouleur, int estOuOuest);
    ArrayList<GCoups> getPseudoCoups();
    void getPseudoCoupsAttaque();
    void pseudoCoups();
    void pseudoCoupsDirection(int caseO, int direction);
    void pseudoCoupsGlissant(int caseO, int[] directionsPiece);
    void pseudoCoupsNonGlissant(int caseO, int[] directionsPiece);
    void pseudoCoupsPion(int caseO, boolean recherchePionAttaqueRoque);
    void pseudoCoupsPromotion(int caseO, int caseX, int pieceprise);
}
