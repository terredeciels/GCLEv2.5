package gposition.generateur;

import gposition.GCoups;
import gposition.*;
import java.util.*;

public abstract class AbstractGenerateur extends ArrayList<GCoups> implements ICodage {

    protected ArrayList<GCoups> pseudoCoups;
    protected int[] etats;
    protected int couleur;
    protected boolean recherchePionAttaqueRoque = false;
    protected GPosition g_position;
    public AbstractGenerateur(GPosition g_position) {
        this.g_position = g_position;
        this.etats = g_position.getEtats();

    }
    public AbstractGenerateur(GPosition g_position, int couleur) {
        this.g_position = g_position;
        this.couleur = couleur;
        this.etats = g_position.getEtats();

    }
    @Override
    public boolean add(GCoups coups) {
        return pseudoCoups.add(coups);
    }
    protected int couleurPiece(int caseO) {
        return (etats[caseO] < 0) ? BLANC : NOIR;
    }
    protected boolean estVide(int caseX) {
        return etats[caseX] == VIDE;
    }
    protected boolean existe(int caseX) {
        return etats[caseX] != OUT;
    }
    protected boolean rangFinal(int caseX) {
        if ((caseX >= a1) && (caseX <= h1) && (couleur == NOIR)) {
            return true;
        } else if ((caseX >= a8) && (caseX <= h8) && (couleur == BLANC)) {
            return true;
        } else {
            return false;
        }
    }
    protected boolean rangInitial(int caseX) {
        if ((caseX >= 98) && (caseX <= 105) && (couleur == NOIR)) {
            return true;
        } else if ((caseX >= 38) && (caseX <= 45) && (couleur == BLANC)) {
            return true;
        } else {
            return false;
        }
    }
    protected boolean pieceQuiALeTrait(int caseO) {
        return !estVide(caseO) && couleurPiece(caseO) == couleur;
    }
    protected int typeDePiece(int caseO) {
        return (etats[caseO] < 0) ? -etats[caseO] : etats[caseO];
    }
    protected int[] getEtats() {
        return etats;
    }
    protected GPosition getGPosition() {
        return g_position;
    }
    protected boolean ajouterCoups(int caseO, int caseX, TYPE_DE_COUPS type_de_coups) {
        return add(new GCoups(etats[caseO], caseO, caseX, etats[caseX], type_de_coups));
    }
    protected boolean pieceAdverse(int caseX) {
        return existe(caseX) && etats[caseX] * couleur < 0;
    }
    protected boolean pionBlanc(int caseX) {
        return (typeDePiece(caseX) == PION) && (couleurPiece(caseX) == BLANC);
    }
    protected boolean pionNoir(int caseX) {
        return (typeDePiece(caseX) == PION) && (couleurPiece(caseX) == NOIR);
    }
}
