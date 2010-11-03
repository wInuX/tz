package tz.application.loader;

import tz.ParserException;
import tz.service.Parser;

import java.io.*;

/**
 * @author Dmitry Shyshkin
 */
public class ErrorLoader {
    public static void main(String[] args) throws IOException {
        for (File file : new File("/tmp").listFiles()) {
            if (!file.getName().endsWith(".xml")) {
                continue;
            }
            try {
                load(file);
            } catch (Throwable t) {
                t.printStackTrace();
                System.out.println(file.getName());
            }
        }
    }

    private static void load(File file) throws IOException, ParserException {
        InputStream is = new FileInputStream(file);
        Reader reader = new InputStreamReader(is, "UTF-8");
        StringBuilder sb = new StringBuilder();
        char[] buf = new char[0x1000];
        int read;
        while ((read = reader.read(buf)) > 0) {
            sb.append(buf, 0, read);
        }
        String content = BattleLoader.normalize(sb.toString());
        Parser.parseBattle(content);
        is.close();
    }
}
