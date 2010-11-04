package tz.logging;

import tz.ParserException;
import tz.service.Normalizer;
import tz.service.Parser;
import tz.xml.Message;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
/**
 * @author Dmitry Shyshkin
 */
public class SequenceLogger  {
    private PrintStream stream;

    public SequenceLogger(String session, String context) {
        setup("./sessions/" + session + "/" + context + ".xml");
    }

    private void setup(String path) {
        try {
            File file = new File(path);
            file.getParentFile().mkdirs();
            stream = new PrintStream(new FileOutputStream(file), false, "UTF-8");
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public void append(String name, String message, Object parsed) {
        if (message == null) {
            message = Parser.create2(parsed, name);
        }
        Normalizer normalizer = new Normalizer("<" + name.toUpperCase() + ">" + message + "</" + name.toUpperCase() + ">");
        try {
            normalizer.normalize();
        } catch (ParserException e) {
            // suppress
            stream.format("<%s>\n%s\n</%s>\n", name.toUpperCase(), message, name.toUpperCase());
            stream.flush();
            return;
        }
        stream.format("%s\n", normalizer.getNormalized());
        stream.flush();
    }

    public void close() {
        stream.close();
    }
}
