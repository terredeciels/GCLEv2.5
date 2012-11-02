package gposition;

import chesspresso.*;
import gposition.generateur.*;
import java.util.*;
import org.apache.commons.collections.iterators.*;

public class GPosition {

    private final static GPosition INSTANCE = new GPosition();
    private final int NB_CELLULES = 144;
    private final int PAS_DE_CASE = -1;
    private final int[] CASES = {
        26, 27, 28, 29, 30, 31, 32, 33, 38, 39, 40, 41, 42, 43, 44, 45, 50, 51, 52, 53, 54, 55, 56, 57, 62, 63, 64, 65,
        66, 67, 68, 69, 74, 75, 76, 77, 78, 79, 80, 81, 86, 87, 88, 89, 90, 91, 92, 93, 98, 99, 100, 101, 102, 103, 104,
        105, 110, 111, 112, 113, 114, 115, 116, 117
    };
    private final int BLANC = -1;
    private final int NOIR = 1;
    private int OUT = 9;
    private String fen;
    private int[] etats;
    private int trait;
    private ArrayList<GCoups> pseudocoups;
    private CPosition cp_position;
    private boolean droitPetitRoqueBlanc;
    private boolean droitGrandRoqueNoir;
    private boolean droitGrandRoqueBlanc;
    private boolean droitPetitRoqueNoir;
    private int caseEP;
    private GPosition() {
        etats = new int[NB_CELLULES];
    }
    public static GPosition getInstance() {
        return INSTANCE;
    }
    public final void init(final String fen) throws IllegalArgumentException {
        this.fen = fen;
        cp_position = CPosition.getInstance();
        cp_position.init(fen);

        for (int caseO = 0; caseO < NB_CELLULES; caseO++) {
            etats[caseO] = OUT;
        }

        ArrayIterator itetats = new ArrayIterator(cp_position.getEtats());
        int indice = 0;

        while (itetats.hasNext()) {
            Integer e = (Integer) itetats.next();

            etats[CASES[indice]] = e;
            indice++;
        }

        if (cp_position.getTrait() == Chess.WHITE) {
            trait = BLANC;
        } else {
            trait = NOIR;
        }

        droitPetitRoqueBlanc = cp_position.getDroitPetitRoqueBlanc();
        droitPetitRoqueNoir = cp_position.getDroitPetitRoqueNoir();
        droitGrandRoqueBlanc = cp_position.getDroitGrandRoqueBlanc();
        droitGrandRoqueNoir = cp_position.getDroitGrandRoqueNoir();

        if (cp_position.getCaseEP() == PAS_DE_CASE) {
            caseEP = -1;
        } else {
            caseEP = CASES[cp_position.getCaseEP()];
        }

        pseudocoups = new Generateur(this).getCoups();
    }
    public int[] getEtats() {
        return etats;
    }
    public int getTrait() {
        return trait;
    }
    public int getCaseEP() {
        return caseEP;
    }
    public boolean getDroitPetitRoqueBlanc() {
        return droitPetitRoqueBlanc;
    }
    public boolean getDroitPetitRoqueNoir() {
        return droitPetitRoqueNoir;
    }
    public boolean getDroitGrandRoqueNoir() {
        return droitGrandRoqueNoir;
    }
    public boolean getDroitGrandRoqueBlanc() {
        return droitGrandRoqueBlanc;
    }
    private ArrayList<String> toStringListGCoups() {
        ArrayList<String> result = new ArrayList();

        for (GCoups c : pseudocoups) {
            result.add(GCoups.getString(c));
        }

        Collections.sort(result);

        return result;
    }
    public GPosition copie() {
        GPosition position = new GPosition();

        System.arraycopy(etats, 0, position.etats, 0, NB_CELLULES);

        return position;
    }
    @Override
    public String toString() {
        ArrayList<String> diff = getTest().getDiffStringList();
        if (!diff.isEmpty()) {
            return fen + '\n' + "Coups ChessPresso:" + "\n" + cp_position.toStringListCPCoups() + '\n' + "Coups GCLE:"
                    + "\n" + toStringListGCoups() + "\n" + "Diff:" + "\n" + diff + "\n";
        } else {
            return "";
        }
    }
    private GPositionTest getTest() {
        GPositionTest valid = new GPositionTest();
        ArrayList<String> lg_coups = toStringListGCoups();
        ArrayList<String> lcp_coups = cp_position.toStringListCPCoups();

        if (lg_coups.size() <= lcp_coups.size()) {
            valid.setDiffStringList(getDiff(lg_coups, lcp_coups));
        } else {
            valid.setDiffStringList(getDiff(lcp_coups, lg_coups));
        }

        return valid;
    }
    private ArrayList<String> getDiff(ArrayList<String> L1, ArrayList<String> L2) {
        L2.removeAll(L1);
        return L2;

    }
}
