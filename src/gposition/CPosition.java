package gposition;

import chesspresso.move.Move;
import chesspresso.position.Position;
import java.util.ArrayList;
import java.util.Collections;

public final class CPosition {

    private final static CPosition INSTANCE = new CPosition();
    private final int NB_CASES = 64;
    private final int[] CASES = {
        0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29,
        30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57,
        58, 59, 60, 61, 62, 63
    };
    private int[] etats;
    private short[] coups;
    private int trait;
    private boolean droitPetitRoqueBlanc;
    private boolean droitGrandRoqueNoir;
    private boolean droitGrandRoqueBlanc;
    private boolean droitPetitRoqueNoir;
    private int caseEP;
    private CPosition() {
    }
    public static CPosition getInstance() {
        return INSTANCE;
    }
    public final void init(final String fen) throws IllegalArgumentException {
        etats = new int[NB_CASES];

        final Position position = new Position(fen);

        for (final int caseO : CASES) {
            // !! getStone et non getPiece
            etats[caseO] = position.getStone(caseO);
        }

        coups = position.getAllMoves();
        trait = position.getToPlay();

        // roques - cf note_tad.txt
        int roques = position.getCastles();

        setPetitRoqueNoir((8 & roques) == 8);
        setGrandRoqueNoir((4 & roques) == 4);
        setPetitRoqueBlanc((2 & roques) == 2);
        setGrandRoqueBlanc((1 & roques) == 1);
        caseEP = position.getSqiEP();
    }
    protected int[] getEtats() {
        return etats;
    }
    protected int getTrait() {
        return trait;
    }
    protected short[] getCoups() {
        return coups;
    }
    public int getCaseEP() {
        return caseEP;
    }
    public boolean getDroitPetitRoqueBlanc() {
        return droitPetitRoqueBlanc;
    }
    public boolean getDroitGrandRoqueNoir() {
        return droitGrandRoqueNoir;
    }
    public boolean getDroitGrandRoqueBlanc() {
        return droitGrandRoqueBlanc;
    }
    public boolean getDroitPetitRoqueNoir() {
        return droitPetitRoqueNoir;
    }
    private void setPetitRoqueBlanc(boolean b) {
        this.droitPetitRoqueBlanc = b;
    }
    private void setGrandRoqueNoir(boolean b) {
        this.droitGrandRoqueNoir = b;
    }
    private void setPetitRoqueNoir(boolean b) {
        this.droitPetitRoqueNoir = b;
    }
    private void setGrandRoqueBlanc(boolean b) {
        this.droitGrandRoqueBlanc = b;
    }
    protected ArrayList<String> toStringListCPCoups() {
        ArrayList<String> result = new ArrayList();
        for (short c : coups) {
            result.add(Move.getString(c));
        }
        Collections.sort(result);
        return result;
    }
}