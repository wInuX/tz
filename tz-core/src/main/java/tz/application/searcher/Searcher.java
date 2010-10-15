package tz.application.searcher;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlRow;
import tz.domain.BattleLog;
import tz.service.Parser;
import tz.xml.Battle;
import tz.xml.BattleView;
import tz.xml.User;

import java.io.*;
import java.util.List;

/**
 * @author Dmitry Shyshkin
 *         <p/>
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
            BattleView battleView = Parser.parse(xml);
            Battle battle = battleView.getBattle();
            for (User user : battleView.getBattle().getUsers()) {
                if (!user.getLogin().startsWith("$")) {
                    continue;
                }
                String name = user.getLogin().substring(1).replaceAll("\\d+", "");
                if (name.equals("rat")) {
                    continue;
                }
                if (name.equals("stich")) {
                    continue;
                }
                if (name.equals("spd")) {
                    continue;
                }
                if (name.equals("std")) {
                    continue;
                }
                if (name.equals("vzz")) {
                    continue;
                }
                if (name.equals("dog")) {
                    continue;
                }
                if (name.equals("man")) {
                    continue;
                }
                if (name.equals("erg")) {
                    continue;
                }
                if (name.equals("bmine")) {
                    continue;
                }
                if (name.equals("ant")) {
                    continue;
                }
                if (name.equals("rbt")) {
                    continue;
                }
                if (name.equals("zmb")) {
                    continue;
                }
                if (name.equals("mts")) {
                    continue;
                }
                if (name.equals("tgr")) {
                    continue;
                }
                if (name.equals("crs")) {
                    continue;
                }
                if (name.equals("rng")) {
                    continue;
                }
                if (name.equals("wrm")) {
                    continue;
                }
                if (name.equals("turret")) {
                    continue;
                }
                if (name.equals("alg") || name.equals("als") || name.equals("alb")) {
                    continue;
                }
                System.out.println(name + " " + battleView.getId() + " [" + battle.getLocationX() + "," + battle.getLocationY() + "]");
            }

        }
    }
}
