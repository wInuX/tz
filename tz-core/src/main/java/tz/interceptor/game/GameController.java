package tz.interceptor.game;

import tz.BattleParserException;
import tz.interceptor.BlockLink;
import tz.interceptor.BlockLinkControl;
import tz.interceptor.BlockLinkListener;
import tz.service.Normalizer;
import tz.service.Parser;
import tz.xml.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmitry Shyshkin
 */
public class GameController {
    private static String[] CODES = {"\b", " name=\"b", "\u0007", " txt=\"", "\u0006", "00\" ", "\u0005", "      ", "\u0004", "><O id=\"", "\u0003", ">\n<O id=\"", "\u0002", "\"/><a sf=\"", "\u0001G", "zzzzzz", "\u0001R", "\" p=\"\"/></L><L X=\"", "\u0001P", "\" ODratio=\"1\" ", "\u0001}", "<BATTLE t=\"45\" t2=\"45\" turn=\"1\" cl=\"0\" ", "\u0001|", ".1\" name=\"b2-s3\" txt=\"Polymers\" massa=\"30\" ", "\u0001z", ".1\" name=\"b2-s6\" txt=\"Radioactive materials\" massa=\"800\" ", "\u0001y", ".1\" name=\"b2-s7\" txt=\"Gems\" massa=\"80\" ", "\u0001x", ".1\" name=\"b2-s2\" txt=\"Precious metals\" massa=\"500\" ", "\u0001w", ".1\" name=\"b2-s4\" txt=\"Organic\" massa=\"30\" ", "\u0001Q", ".1\" name=\"b2-s8\" txt=\"Venom\" massa=\"70\" ", "\u0001k", ".1\" name=\"b2-s5\" txt=\"Silicon\" massa=\"50\" ", "\u0001i", " psy=\"0\" man=\"3\" maxPsy=\"0\" ODratio=\"1\" img=\"rat\" group=\"2\" battleid=\"", "\u0001X", "\" slot=\"", "\u0001v", ".1\" slot=\"GH\" name=\"b", "\u0001{", ".1\" slot=\"F\" name=\"b", "\u0001g", ".1\" slot=\"E\" name=\"b", "\u0001e", ".1\" slot=\"D\" name=\"b", "\u0001h", ".1\" slot=\"C\" name=\"b", "\u0001d", ".1\" slot=\"B\" name=\"b", "\u0001b", ".1\" slot=\"A\" name=\"b", "\u0001a", "\" range=\"", "\u0001_", "\" shot=\"", "\u0001O", "\" build_in=\"", "\u0001]", "\" count=\"", "\u0001\\", "\" calibre=\"", "\u0001[", "\" max_count=\"", "\u0001Z", "\" ne=\",,,,,\" ne2=\",,,,,\" nark=\"0\" gluk=\"0\" ", "\u0001Y", "\" virus=\"0\" login=\"", "\u0001U", " freeexchange=\"1\" ", "\u0001T", " psy=\"0\" man=\"1\" maxHP=\"", "\u0001S", ".1\" name=\"b1-g2\" txt=\"Boulder\" massa=\"5\" st=\"G,H\" made=\"AR$\" section=\"0\" damage=\"S2-5\" shot=\"7-1\" nskill=\"4\" OD=\"1\" type=\"9.1\"/>", "\u0001L", " cost=\"0\" ", "\u0001K", "\" txt=\"Coins\" massa=\"1\" ", "\u0001J", " txt=\"BankCell Key (copy) #", "\u0001I", " txt=\"ammo ", "\u0001H", " min=\"", "\u0001F", "level=", "\u0001E", "\"/>\n<O id=\"", "\u0001D", "\"/>\n</O>\n<O id=\"", "\u0001V", " ODratio=\"1\" loc_time=\"", "\u0001C", "protect=\"S", "\u0001A", "=\"1\" type=\"", "\u0001", "\" section=\"", "\u0001~", "section=\"0\" damage=\"", "\u0001u", "\" type=\"1\"", "\u0001t", "\" st=\"G,H\" ", "\u0001s", "\" nskill=\"", "\u0001q", "\" made=\"AR$\" ", "\u0001p", "\" damage=\"S", "\u0001o", "/><a sf=\"6\" t=\"", "\u0001B", "><a sf=\"6\" t=\"2\"/><a ", "\u0001n", "\" maxquality=\"", "\u0001m", "\" massa=\"1", "\u0001l", "\" quality=\"", "\u0001j", ".1\" slot=\"", "\u0001c", "\" t=\"5\" xy=\"", "\u0001`", "><a sf=\"0\" t=\"", "\u0001N", "\" t=\"1\" direct=\"", "\u0001M", "\" t=\"2\"/><a sf=\"", "\u0001f", "\"/><MAP v=\"", "\u0001W", "></USER><USER login=\"", "\u0001r", "<TURN><USER login=\"", "\u0001^", "t=\"2\"/></USER><USER login=\""};

    private BlockLinkListener chatListener = new BlockLinkListener() {
        public void server(String content) {
//            System.out.printf("C <- %s\n", content);
            String decoded = content;
            for (int i = 0; i < CODES.length; i += 2) {
                decoded = decoded.replace(CODES[i], CODES[i + 1]);
            }
            try {
                String normalized = new Normalizer(decoded).normalize();
                System.out.printf("G <- %s\n", normalized);
                Message message;
                if (normalized.startsWith("<")) {
                    message =  Parser.parseMessage("<MESSAGE>" + normalized + "</MESSAGE>");
                } else {
                    message = new Message(normalized);
                }
                if (execute(InterceptionType.CHAT_SERVER, content, message.getValue())) {
                    return;
                }
            } catch (BattleParserException e) {
                e.printStackTrace();
            }

            chatControl.client(content);
        }

        public void client(String content) {
//            System.out.printf("C -> %s\n", content);
            chatControl.server(content);
        }
    };

    private BlockLinkListener gameListener = new BlockLinkListener() {
        public void server(String content) {
//            System.out.printf("G <- %s\n", content);
            String decoded = content;
            for (int i = 0; i < CODES.length; i += 2) {
                decoded = decoded.replace(CODES[i], CODES[i + 1]);
            }
            try {
                String normalized = new Normalizer(decoded).normalize();
                System.out.printf("G <- %s\n", normalized);
                Message message = Parser.parseMessage("<MESSAGE>" + normalized + "</MESSAGE>");
                if (execute(InterceptionType.SERVER, content, message.getValue())) {
                    return;
                }
            } catch (BattleParserException e) {
                e.printStackTrace();
            }
            gameControl.client(content);

        }

        public void client(String content) {
            try {
                String normalized = new Normalizer(content).normalize();
                System.out.printf("G -> %s\n", normalized);
                Message message = Parser.parseMessage("<MESSAGE>" + normalized + "</MESSAGE>");
                if (execute(InterceptionType.CLIENT, content, message.getValue())) {
                    return;
                }
            } catch (BattleParserException e) {
                e.printStackTrace();
            }

            gameControl.server(content);
        }
    };

    private BlockLinkControl gameControl;

    private BlockLinkControl chatControl;

    private List<IntercetorDefinition> interceptors = new ArrayList<IntercetorDefinition>();

    public void start(BlockLink gameLink) {
        this.gameControl = gameLink.getControl();
        gameLink.start(gameListener);
    }

    public void setChatControl(BlockLink chatLink) {
        this.chatControl = chatLink.getControl();
        chatLink.start(chatListener);
    }

    public BlockLinkListener getChatListener() {
        return chatListener;
    }

    public BlockLinkListener getGameListener() {
        return gameListener;
    }

    public void addInterceptor(InterceptionType type, Class<?> messageType, Interceptor interceptor) {
        interceptors.add(new IntercetorDefinition(type, messageType, interceptor));
    }

    private boolean execute(InterceptionType type, String original, Object message) {
        for (IntercetorDefinition definition : interceptors) {
            if (definition.getType() != type) {
                continue;
            }
            if (definition.getMessageType() != message.getClass()) {
                continue;
            }
            if (definition.getInterceptor().intercept(original, message)) {
                return true;
            }
        }
        return false;
    }

    private static class IntercetorDefinition {
        private InterceptionType type;
        private Class<?> messageType;
        private Interceptor interceptor;

        private IntercetorDefinition(InterceptionType type, Class<?> messageType, Interceptor interceptor) {
            this.type = type;
            this.messageType = messageType;
            this.interceptor = interceptor;
        }

        public InterceptionType getType() {
            return type;
        }

        public void setType(InterceptionType type) {
            this.type = type;
        }

        public Class<?> getMessageType() {
            return messageType;
        }

        public void setMessageType(Class<?> messageType) {
            this.messageType = messageType;
        }

        public Interceptor getInterceptor() {
            return interceptor;
        }

        public void setInterceptor(Interceptor interceptor) {
            this.interceptor = interceptor;
        }
    }
}
