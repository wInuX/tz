package tz.interceptor;

import java.util.HashMap;
import java.util.Map;

/**
* @author Dmitry Shyshkin
*/
enum LinkType {
    INIT_CHAT, LOGIN, GAME, CHAT;
    private static Map<String, LinkType> map = new HashMap<String, LinkType>();

    static {
        map.put("188.93.63.180", INIT_CHAT);
        map.put("188.93.63.175", GAME);
        map.put("188.93.63.168", GAME);
        //map.put("188.93.63.180", CHAT);
    }

    public static LinkType getLinkType(String ip) {
        return  map.get(ip);
    }
}
