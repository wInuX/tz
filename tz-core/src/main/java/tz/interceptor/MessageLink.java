package tz.interceptor;

import org.apache.log4j.Logger;
import tz.ParserException;
import tz.service.Normalizer;
import tz.service.Parser;
/**
 * @author Dmitry Shyshkin
 */
public class MessageLink {
    private static final Logger LOG = Logger.getLogger(MessageLink.class);

    private LinkListener fromServer = new BlockListener("server") {
        @Override
        public void read(String block, Object message) {
            listener.server(block, message);
        }

        public void closed() {
            listener.close();
        }
    };

    private LinkListener fromClient = new BlockListener("client") {
        @Override
        public void read(String block, Object message) {
            listener.client(block, message);
        }

        public void closed() {
            listener.close();
        }
    };

    private MessageControl control = new MessageControl() {
        public void server(String content) {
            System.out.printf("--> %s\n", content);
            server.write(create(content), content.length() + 1);
        }

        public void client(String content) {
            client.write(create(content), content.length() + 1);
        }

        public void server(Object message) {
            server(Parser.marshall(message, "client"));
        }

        public void client(Object message) {
            client(Parser.marshall(message, "server"));
        }

        private char[] create(String content) {
            char[] chars = new char[content.length() + 1];
            System.arraycopy(content.toCharArray(), 0, chars, 0, content.length());
            return chars;
        }
    };

    private LinkControl client;
    private LinkControl server;
    private MessageListener listener;
    private InterceptedConnection connection;
    private boolean decode = false;

    public MessageLink(InterceptedConnection connection) {
        this.connection = connection;
        server = connection.getSlaveMasterControl();
        client = connection.getMasterSlaveControl();

        connection.setMasterSlaveListener(fromServer);
        connection.setSlaveMasterListener(fromClient);
    }

    public MessageListener getListener() {
        return listener;
    }

    public void setListener(MessageListener listener) {
        this.listener = listener;
    }

    public MessageControl getControl() {
        return control;
    }

    public boolean isDecode() {
        return decode;
    }

    public void setDecode(boolean decode) {
        this.decode = decode;
    }

    private static String[] CODES = {"\b", " name=\"b", "\u0007", " txt=\"", "\u0006", "00\" ", "\u0005", "      ", "\u0004", "><O id=\"", "\u0003", ">\n<O id=\"", "\u0002", "\"/><a sf=\"", "\u0001G", "zzzzzz", "\u0001R", "\" p=\"\"/></L><L X=\"", "\u0001P", "\" ODratio=\"1\" ", "\u0001}", "<BATTLE t=\"45\" t2=\"45\" turn=\"1\" cl=\"0\" ", "\u0001|", ".1\" name=\"b2-s3\" txt=\"Polymers\" massa=\"30\" ", "\u0001z", ".1\" name=\"b2-s6\" txt=\"Radioactive materials\" massa=\"800\" ", "\u0001y", ".1\" name=\"b2-s7\" txt=\"Gems\" massa=\"80\" ", "\u0001x", ".1\" name=\"b2-s2\" txt=\"Precious metals\" massa=\"500\" ", "\u0001w", ".1\" name=\"b2-s4\" txt=\"Organic\" massa=\"30\" ", "\u0001Q", ".1\" name=\"b2-s8\" txt=\"Venom\" massa=\"70\" ", "\u0001k", ".1\" name=\"b2-s5\" txt=\"Silicon\" massa=\"50\" ", "\u0001i", " psy=\"0\" man=\"3\" maxPsy=\"0\" ODratio=\"1\" img=\"rat\" group=\"2\" battleid=\"", "\u0001X", "\" slot=\"", "\u0001v", ".1\" slot=\"GH\" name=\"b", "\u0001{", ".1\" slot=\"F\" name=\"b", "\u0001g", ".1\" slot=\"E\" name=\"b", "\u0001e", ".1\" slot=\"D\" name=\"b", "\u0001h", ".1\" slot=\"C\" name=\"b", "\u0001d", ".1\" slot=\"B\" name=\"b", "\u0001b", ".1\" slot=\"A\" name=\"b", "\u0001a", "\" range=\"", "\u0001_", "\" shot=\"", "\u0001O", "\" build_in=\"", "\u0001]", "\" count=\"", "\u0001\\", "\" calibre=\"", "\u0001[", "\" max_count=\"", "\u0001Z", "\" ne=\",,,,,\" ne2=\",,,,,\" nark=\"0\" gluk=\"0\" ", "\u0001Y", "\" virus=\"0\" login=\"", "\u0001U", " freeexchange=\"1\" ", "\u0001T", " psy=\"0\" man=\"1\" maxHP=\"", "\u0001S", ".1\" name=\"b1-g2\" txt=\"Boulder\" massa=\"5\" st=\"G,H\" made=\"AR$\" section=\"0\" damage=\"S2-5\" shot=\"7-1\" nskill=\"4\" OD=\"1\" type=\"9.1\"/>", "\u0001L", " cost=\"0\" ", "\u0001K", "\" txt=\"Coins\" massa=\"1\" ", "\u0001J", " txt=\"BankCell Key (copy) #", "\u0001I", " txt=\"ammo ", "\u0001H", " min=\"", "\u0001F", "level=", "\u0001E", "\"/>\n<O id=\"", "\u0001D", "\"/>\n</O>\n<O id=\"", "\u0001V", " ODratio=\"1\" loc_time=\"", "\u0001C", "protect=\"S", "\u0001A", "=\"1\" type=\"", "\u0001", "\" section=\"", "\u0001~", "section=\"0\" damage=\"", "\u0001u", "\" type=\"1\"", "\u0001t", "\" st=\"G,H\" ", "\u0001s", "\" nskill=\"", "\u0001q", "\" made=\"AR$\" ", "\u0001p", "\" damage=\"S", "\u0001o", "/><a sf=\"6\" t=\"", "\u0001B", "><a sf=\"6\" t=\"2\"/><a ", "\u0001n", "\" maxquality=\"", "\u0001m", "\" massa=\"1", "\u0001l", "\" quality=\"", "\u0001j", ".1\" slot=\"", "\u0001c", "\" t=\"5\" xy=\"", "\u0001`", "><a sf=\"0\" t=\"", "\u0001N", "\" t=\"1\" direct=\"", "\u0001M", "\" t=\"2\"/><a sf=\"", "\u0001f", "\"/><MAP v=\"", "\u0001W", "></USER><USER login=\"", "\u0001r", "<TURN><USER login=\"", "\u0001^", "t=\"2\"/></USER><USER login=\""};

    private abstract class BlockListener implements LinkListener {
        private StringBuilder sb = new StringBuilder();
        private String context;

        protected BlockListener(String context) {
            this.context = context;
        }

        public void read(char[] buf, int length) {
            String chunk = new String(buf, 0, length);
            int index;
            while ((index = chunk.indexOf('\u0000')) >= 0) {
                String subchunk = chunk.substring(0, index);
                chunk = chunk.substring(index + 1);
                if (subchunk.equals("<SH ")) { //workaround for sh bug
                    continue;
                }
                sb.append(subchunk);
                String content = sb.toString();
                String decoded = content;
                sb = new StringBuilder();
                if (decode) {
                    for (int i = 0; i < CODES.length; i += 2) {
                        decoded = decoded.replace(CODES[i], CODES[i + 1]);
                    }
                }
                mloop:
                do {
                    if (!decoded.startsWith("<")) {
                        read(decoded, decoded);
                        break;
                    }
                    Normalizer normalizer = new Normalizer(decoded);
                    Normalizer.Status status;
                    try {
                        status = normalizer.normalize();
                    } catch (ParserException e) {
                        LOG.error(String.format("Error normalizing:\n %s", decoded));
                        read(decoded, null);
                        break;
                    }
                    switch (status) {
                        case OK:
                        case PARTIAL:
                            String normalized = normalizer.getNormalized();
                            Object message;
                            try {
                                message = Parser.unmarshall(normalized, context);
                            } catch (ParserException e) {
                                LOG.error(String.format("Error parsing\n %s", normalized), e);
                                read(normalizer.getParsed(), null);
                                break mloop;
                            }
                            read(normalizer.getParsed(), message);
                            if (status == Normalizer.Status.OK) {
                                break mloop;
                            } else {
                                decoded = normalizer.getUnparsed();
                            }
                            break;
                        case NEEDMORE:
                            sb.append(content);
                            break mloop;
                    }
                } while (true);
            }
            sb.append(chunk);
        }

        public abstract void read(String block, Object message);
    }
}
