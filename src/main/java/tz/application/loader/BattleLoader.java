package tz.application.loader;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlRow;
import org.apache.log4j.Logger;
import org.xml.sax.SAXParseException;
import tz.BattleParserException;
import tz.domain.BattleLog;
import tz.service.Parser;
import tz.xml.Battle;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Dmitry Shyshkin
 */
public class BattleLoader {
    public static final Logger LOG = Logger.getLogger(BattleLoader.class);

    private long lastId;

    private final Object monitor = new Object();

    private Battle lastBattle;

    private int density = 100;

    public BattleLoader(long lastId) {
        this.lastId = lastId;
        SqlRow row = Ebean.createSqlQuery("select max(id) id from battle").findUnique();
        if (row != null && row.getLong("id") != null) {
            this.lastId = Math.max(lastId, row.getLong("id"));
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new BattleLoader(301967577089L).run();
    }

    public LoaderTask createTask() {
        synchronized (monitor) {
            if (lastBattle != null &&  lastBattle.getDate().getTime() + 60 * 60 * 1000 > System.currentTimeMillis()) {
                return null;
            }
            lastId += 256 * density;
            return new LoaderTask(lastId);
        }
    }

    public void run() throws InterruptedException {
        for (int i = 0; i < 5; ++i) {
            new Thread() {
                @Override
                public void run() {
                    while (true) {
                        LoaderTask task = createTask();
                        if (task == null) {
                            break;
                        }
                        task.run();
                    }
                }
            }.start();
        }
    }

    private class LoaderTask implements Runnable {
        private long id;

        private LoaderTask(long id) {
            this.id = id;
        }

        private String load(String url) throws IOException {
            URLConnection connection = new URL(url).openConnection();
            connection.setConnectTimeout(20000);
            connection.setReadTimeout(10000);
            InputStream is = connection.getInputStream();
            try {
                Reader reader = new InputStreamReader(is, "UTF-8");
                StringBuilder sb = new StringBuilder();
                char[] buf = new char[0x1000];
                int read;
                while ((read = reader.read(buf)) > 0) {
                    sb.append(buf, 0, read);
                }
                return sb.toString();
            } finally {
                is.close();
            }
        }

        private void save(String name, String content) {
            try {
                FileOutputStream os = new FileOutputStream(name);
                os.write(("<VIEW>" + content + "</VIEW>").getBytes("UTF-8"));
                os.close();
            } catch (IOException e) {
                LOG.error("Error creating file", e);
            }
        }

        public void run() {
            String content = null;

            try {
                String raw = null;
                int retry = 3;
                do {
                    try {
                        raw = load("http://city1.timezero.ru/getbattle?id=" + id);
                    } catch (IOException e) {
                        if (retry == 1) {
                            e.printStackTrace();
                            throw new IllegalStateException("Load error", e);
                        }
                    }
                } while (--retry >= 0);
                if (raw.startsWith("<HTML>")) {
                    LOG.warn("Error loading " + id + " : " + raw.replaceAll("<[^>]+>", "").replaceAll("<.*+", ""));
                    return;
                }
                try {
                    content = normalize(raw);
                } catch (BattleParserException e) {
                    e.printStackTrace();
                    save("/tmp/" + id + ".raw.xml", raw);
                }
                Battle battle = Parser.parse(content).getBattle();
                synchronized (monitor) {
                    lastBattle = battle;
                }
                BattleLog log = new BattleLog();
                log.setId(id);
                log.setDate(battle.getDate());
                log.setLocationX(battle.getLocationX());
                log.setLocationY(battle.getLocationY());
                log.setXml(content);
                
                Ebean.beginTransaction();
                try {
                    Ebean.save(log);
                    Ebean.commitTransaction();
                } finally {
                    Ebean.endTransaction();
                }
                LOG.info("Battle " + id + " loaded. Location [" + battle.getLocationX() + "," + battle.getLocationY() + "] at " + log.getDate());
            } catch (Throwable t) {
                LOG.error("load error. id " + id + "\n", t);
                if (t.getCause() != null && t.getCause().getCause() instanceof SAXParseException){
                    SAXParseException sax = (SAXParseException) t.getCause().getCause();
                    LOG.error("line: " + sax.getLineNumber() + " char:" + sax.getColumnNumber());
                    LOG.error("content:\n " + content);
                    save("/tmp/" + id + ".xml", content);
                }
            }
        }

    }

    public static String normalize(String content) throws BattleParserException {
        return new Normalizer(content).normalize();
    }
}
