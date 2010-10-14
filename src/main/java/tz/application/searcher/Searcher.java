package tz.application.searcher;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlRow;
import tz.domain.BattleLog;
import tz.service.Parser;
import tz.xml.BattleView;
import tz.xml.User;

import java.util.List;

/**
 * @author Dmitry Shyshkin
 *
 * rat
 * stich
 * spd spider
 * std
 * vzz
 * ant
 * dog
 * erg - ergo
 * man
 * alg als alb - ordung
 * rng - ranger
 * crs - crasher
 * rbt - robot
 * zmb - zombie
 * mts - bogomol
 * wrm - worm
 * tgr - tiger
 * turret - turret
 */
public class Searcher {
    public static void main(String[] args) {
        int i =0;
        do {
            List<BattleLog> logs = Ebean.createQuery(BattleLog.class).order().asc("id").setFirstRow(i).setMaxRows(500).findList();
            if (logs.size() == 0) {
                break;
            }
            i += logs.size();
            System.out.println(".");
            for (BattleLog log : logs) {
                BattleView battleView = Parser.parse(log.getXml());
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
                    System.out.println(name + " " + log.getId() + " [" + log.getLocationX() + "," + log.getLocationY() + "]");
                }

            }
        } while(true);
    }
}
