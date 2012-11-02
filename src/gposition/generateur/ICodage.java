package gposition.generateur;

public interface ICodage {

    final static int PAS_DE_CASE = -1;    // e.p.
    final String fen_initiale = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    final int BLANC = -1,
            NOIR = 1;
    final int a1 = 26,
            h1 = 33,
            a8 = 110,
            h8 = 117;
    final int e1 = 30,
            f1 = 31,
            g1 = 32,
            d1 = 29,
            c1 = 28,
            b1 = 27;
    final int e8 = 114,
            f8 = 115,
            g8 = 116,
            d8 = 113,
            c8 = 112,
            b8 = 111;
    final int[] CASES = {
        26, 27, 28, 29, 30, 31, 32, 33,
        38, 39, 40, 41, 42, 43, 44, 45,
        50, 51, 52, 53, 54, 55, 56, 57, 62, 63, 64, 65,
        66, 67, 68, 69, 74, 75, 76, 77, 78, 79, 80, 81, 86, 87, 88, 89, 90, 91, 92, 93, 98, 99, 100, 101, 102, 103, 104,
        105, 110, 111, 112, 113, 114, 115, 116, 117
    };
    final int[] INDICECASES = {
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2,
        3, 4, 5, 6, 7, -1, -1, -1, -1, 8, 9, 10, 11, 12, 13, 14, 15, -1, -1, -1, -1, 16, 17, 18, 19, 20, 21, 22, 23, -1,
        -1, -1, -1, 24, 25, 26, 27, 28, 29, 30, 31, -1, -1, -1, -1, 32, 33, 34, 35, 36, 37, 38, 39, -1, -1, -1, -1, 40,
        41, 42, 43, 44, 45, 46, 47, -1, -1, -1, -1, 48, 49, 50, 51, 52, 53, 54, 55, -1, -1, -1, -1, 56, 57, 58, 59, 60,
        61, 62, 63, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1
    };
  
    final String[] STRING_CASES = {
        "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1",
        "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2",
        "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3", "a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4", "a5", "b5", "c5", "d5",
        "e5", "f5", "g5", "h5", "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6", "a7", "b7", "c7", "d7", "e7", "f7",
        "g7", "h7", "a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8"
    };
    /**
     * @aggregation composite
     */
    final String[] STRING_PIECE = {"", "N", "B", "R", "Q"};
    // Types pièces codés comme dans CP
    final int ROI = 6,
            CAVALIER = 1,
            TOUR = 3,
            FOU = 2,
            DAME = 4,
            PION = 5;
    //
    final int NB_CASES = 64,
            NB_CELLULES = 144;
    final int VIDE = 0,
            OUT = 9;
    final int nord = +12,
            est = -1,
            sud = -12,
            ouest = +1;
    final int nordest = nord + est;
    final int nordouest = nord + ouest;
    final int sudest = sud + est;
    final int sudouest = sud + ouest;
    final int[] DIR_CAVALIER = {
        2 * nord + est, 2 * nord + ouest, 2 * est + nord, 2 * est + sud, 2 * sud + est, 2 * sud + ouest,
        2 * ouest + nord, 2 * ouest + sud
    };
    final int[] DIR_DAME = {
        nord, nordest, est, sudest, sud, sudouest, ouest, nordouest
    };
    final int[] DIR_FOU = {nordest, sudest, sudouest, nordouest};
    final int[] DIR_ROI = {
        nord, nordest, est, sudest, sud, sudouest, ouest, nordouest
    };
    final int[] DIR_TOUR = {nord, est, sud, ouest};

    enum TYPE_DE_COUPS {

        Roque, EnPassant, Promotion, Deplacement, Prise, Attaque;
    }
}