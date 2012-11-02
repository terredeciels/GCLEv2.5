package client;

import gposition.GPosition;
import main.Fen;
import main.Ui;

public class Client {

    public static void main(String[] args) {

//      String fen_initiale = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        GPosition position = GPosition.getInstance();

//      position.init(fen_initiale);
//      System.out.println(position);

        //
        String[] command = new String[3];

        command[0] = "-cli";
       // command[1] = "F:/ProgmEchecsNotes/shirov.pgn";//814 parties
//        command[1] = "F:/ProgmEchecsNotes/paulsen.pgn";//322
//        command[1] = "F:/ProgmEchecsNotes/ashley.pgn";//414
          command[1] = "F:/ProgmEchecsNotes/bird.pgn";//353 
//        command[1] = "F:/ProgmEchecsNotes/Tartakower.pgn";//1290
//            command[1] = "F:/ProgmEchecsNotes/Capablanca.pgn";//597
//        command[1] = "F:/ProgmEchecsNotes/Boleslavsky.pgn";//651
//        command[1] = "F:/ProgmEchecsNotes/Soltis.pgn";//370
//        command[1] = "F:/ProgmEchecsNotes/Motylev.pgn";//1169
        Ui.main(command);

//      ArrayList<String> fenliste = Fen.getFenList();
        for (String f : Fen.getFenList()) {
            position.init(f);
            System.out.print(position);
        }
//   String fen = "8/1pR1P3/6b1/1k1p2p1/pP1r2P1/8/4KR2/8 w - - 1 57";
//     
//        position = GPosition.getInstance();
//        position.init(fen);
//        System.out.println(position);
    }
}
