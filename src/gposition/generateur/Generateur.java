package gposition.generateur;

import gposition.GCoups;
import gposition.GPosition;
import java.util.ArrayList;

public class Generateur extends AbstractGenerateur {

    public Generateur(GPosition g_position) {
        super(g_position);
        this.couleur = g_position.getTrait();
    }
    public final ArrayList<GCoups> getCoups() {
        PGenerateur pGenerateur = new PGenerateur(g_position, couleur);
        pGenerateur.pseudoCoups();
        ArrayList<GCoups> pCoups = pGenerateur.getPseudoCoups();
        // roques
        ajouterRoques(pCoups);
        coupsEp(pCoups, couleur);
        // Echecs
        ArrayList<GCoups> aRetirer = suprimerEchecs(pCoups);
        pCoups.removeAll(aRetirer);
        return pCoups;
    }
    private ArrayList<GCoups> suprimerEchecs(ArrayList<GCoups> fCoups) {
        ArrayList<GCoups> aRetirer = new ArrayList();
        int caseRoiCouleur;
        boolean estEnEchec;
        for (GCoups coups : fCoups) {
            ArrayList<GCoups> pseudoCoupsPosSimul;
            GPosition positionSimul = fPositionSimul(g_position, coups, couleur);
            caseRoiCouleur = fCaseRoi(positionSimul, couleur);
            PGenerateur pGen = new PGenerateur(positionSimul, -couleur);    // attention: -couleur
            pGen.pseudoCoups();
            pseudoCoupsPosSimul = pGen.getPseudoCoups();
            estEnEchec = fEstEnEchec(pseudoCoupsPosSimul, caseRoiCouleur);
            if (estEnEchec) {
                aRetirer.add(coups);
            }
        }

        return aRetirer;
    }
    private GPosition fPositionSimul(GPosition position, GCoups coups, int couleur) {
        GPosition positionSimul = position.copie();
        int caseO = coups.getCaseO();
        int caseX = coups.getCaseX();
        if (coups.getTypeDeCoups() == TYPE_DE_COUPS.Deplacement
                || coups.getTypeDeCoups() == TYPE_DE_COUPS.Prise) {
            positionSimul.getEtats()[caseX] = positionSimul.getEtats()[caseO];
            positionSimul.getEtats()[caseO] = VIDE;
        } else if (coups.getTypeDeCoups() == TYPE_DE_COUPS.EnPassant) {
            // caseX == caseEP
            positionSimul.getEtats()[caseX] = positionSimul.getEtats()[caseO];
            positionSimul.getEtats()[caseO] = VIDE;
            if (couleur == BLANC) {
                positionSimul.getEtats()[caseX + sud] = VIDE;
            } else if (couleur == NOIR) {
                positionSimul.getEtats()[caseX + nord] = VIDE;
            }
        } else if (coups.getTypeDeCoups() == TYPE_DE_COUPS.Promotion) {
            positionSimul.getEtats()[caseX] = coups.getPiecePromotion();
            positionSimul.getEtats()[caseO] = VIDE;
        }

        return positionSimul;
    }
    private int fCaseRoi(GPosition position, int couleur) {
        int[] pEtats = position.getEtats();
        int caseRoi = OUT;
        int etatO;
        int typeO;
        for (int caseO : CASES) {
            etatO = pEtats[caseO];
            typeO = Math.abs(etatO);
            if ((typeO == ROI) && (etatO * couleur > 0)) {
                caseRoi = caseO;
                break;
            }
        }
        return caseRoi;
    }
    private boolean fEstEnEchec(ArrayList<GCoups> pseudoCoupsPosSimul, int caseRoi) {
        boolean estEnEchec = false;
        for (GCoups coups : pseudoCoupsPosSimul) {
            if (coups.getCaseX() == caseRoi) {
                estEnEchec = true;
                break;
            }
        }
        return estEnEchec;
    }
    private void ajouterRoques(ArrayList<GCoups> fCoups) {
        // attention: -couleur
        PGenerateur pGen = new PGenerateur(g_position, -couleur);
        pGen.getPseudoCoupsAttaque();
        ArrayList<GCoups> coupsAttaque = pGen.getPseudoCoups();

        boolean possible;
        final GPosition pgPosition = pGen.getGPosition();
        final int[] pgEtats = pgPosition.getEtats();
        if (couleur == BLANC) {
            if (pgPosition.getDroitPetitRoqueBlanc()) {
                possible = ((pgEtats[f1] == VIDE)
                        && (pgEtats[g1] == VIDE));
                possible &= !(attaqueRoque(e1, f1, g1, coupsAttaque));

                if (possible) {
                    fCoups.add(new GCoups(ROI, e1, g1, 0, TYPE_DE_COUPS.Roque));
                }
            }
            if (pgPosition.getDroitGrandRoqueBlanc()) {
                possible = ((pgEtats[d1] == VIDE)
                        && (pgEtats[c1] == VIDE)
                        && (pgEtats[b1] == VIDE));
                possible &= !(attaqueRoque(e1, d1, c1, coupsAttaque));

                if (possible) {
                    fCoups.add(new GCoups(ROI, e1, c1, 0, TYPE_DE_COUPS.Roque));
                }
            }
        } else {
            if (pgPosition.getDroitPetitRoqueNoir()) {
                possible = ((pgEtats[f8] == VIDE)
                        && (pgEtats[g8] == VIDE));
                possible &= !(attaqueRoque(e8, f8, g8, coupsAttaque));

                if (possible) {
                    fCoups.add(new GCoups(ROI, e8, g8, 0, TYPE_DE_COUPS.Roque));
                }
            }
            if (pgPosition.getDroitGrandRoqueNoir()) {
                possible = ((pgEtats[d8] == VIDE)
                        && (pgEtats[c8] == VIDE)
                        && (pgEtats[b8] == VIDE));
                possible &= !(attaqueRoque(e8, d8, c8, coupsAttaque));

                if (possible) {
                    fCoups.add(new GCoups(ROI, e8, c8, 0, TYPE_DE_COUPS.Roque));
                }
            }
        }
    }
    private boolean attaqueRoque(int E1ouE8, int F1ouF8, int G1ouG8, ArrayList<GCoups> coupsAttaque) {
        boolean attaque = false;
        int caseX;
        for (GCoups coups : coupsAttaque) {
            caseX = coups.getCaseX();
            if ((caseX == E1ouE8) || (caseX == F1ouF8) || (caseX == G1ouG8)) {
                attaque = true;

                break;
            }
        }
        return attaque;
    }
    private void coupsEp(final ArrayList<GCoups> pCoups, final int couleur) {
        final int caseEP = g_position.getCaseEP();
        // prise en passant (avant recherche d'échecs)
        if (caseEP != PAS_DE_CASE) {
            if (couleur == BLANC) {
                int caseX = caseEP + sudest;
                if (pionBlanc(caseX)) {
                    pCoups.add(new GCoups(PION, caseX, caseEP, 0, TYPE_DE_COUPS.EnPassant));
                }
                caseX = caseEP + sudouest;
                if (pionBlanc(caseX)) {
                    pCoups.add(new GCoups(PION, caseX, caseEP, 0, TYPE_DE_COUPS.EnPassant));
                }
            } else {
                int caseX = caseEP + nordest;
                if (pionNoir(caseX)) {
                    pCoups.add(new GCoups(PION, caseX, caseEP, 0, TYPE_DE_COUPS.EnPassant));
                }
                caseX = caseEP + nordouest;
                if (pionNoir(caseX)) {
                    pCoups.add(new GCoups(PION, caseX, caseEP, 0, TYPE_DE_COUPS.EnPassant));
                }
            }
        }
    }
}
