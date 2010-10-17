package tz.application.searcher;

import tz.service.Parser;
import tz.xml.Battle;
import tz.xml.BattleView;
import tz.xml.UserType;

import java.io.*;

/**
 * @author Dmitry Shyshkin
 *         rat
 *         stich
 *         spd spider
 *         std
 *         vzz
 *         ant
 *         dog
 *         erg - ergo
 *         man
 *         alg als alb - ordung
 *         rng - ranger
 *         crs - crasher
 *         rbt - robot
 *         zmb - zombie
 *         mts - bogomol
 *         wrm - worm
 *         tgr - tiger
 *         turret - turret
 */
public class Searcher {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("/tmp/battles"), "UTF-8"));
        System.out.println(".");
        String xml;
        while ((xml = reader.readLine()) != null) {
            while (!xml.endsWith("</VIEW>")) {
                xml += reader.readLine();
            }
            BattleView battleView = null;
            try {
                battleView = Parser.parseBattle(xml);
                Battle battle = battleView.getBattle();
                if (battleView.getId() == 301967643649L) {
                    //System.out.println(xml);
                }
                BattleContext context = new BattleContext(battleView);
//                if (battle.getLocationX() >= 1 && battle.getLocationX() <= 1 &&
//                         battle.getLocationY() >= 0 && battle.getLocationY() <= 0) {
//                    System.out.println(context);
//                }
                if (context.hasBot(UserType.ERGO, 1, 30)) {
                    System.out.println(context);
                }
            } catch (Throwable t) {
                if (battleView != null) {
                    System.err.println(battleView.getId());
                }
                System.err.println(xml);
                t.printStackTrace();
                break;
            }
        }
    }
}
