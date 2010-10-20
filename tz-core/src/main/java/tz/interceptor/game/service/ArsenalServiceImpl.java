package tz.interceptor.game.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import tz.interceptor.game.Intercept;
import tz.interceptor.game.InterceptionType;
import tz.xml.GoBuilding;
import tz.xml.Item;
import tz.xml.Post;
import tz.xml.Search;

/**
 * @author Dmitry Shyshkin
 */
@Singleton
public class ArsenalServiceImpl extends AbstractService {
    private boolean isActive = false;

    @Inject
    private GameState state;

    private String name;
    private int count;

    @Intercept(InterceptionType.SERVER)
    boolean onGoBuilding(GoBuilding goBuilding) {
        if (!isActive) {
            return false;
        }
        if (goBuilding.getN() == 0) {
            GoBuilding gb = new GoBuilding();
            gb.setN(2);
            server(gb);
        }
        if (goBuilding.getN() == 2) {
            server(new Search());
        }
        return false;
    }

    @Intercept(InterceptionType.SERVER)
    boolean onSearch(Search search) {
        if (!isActive) {
            return false;
        }
        for (Item item : search.getItems()) {
            if (item.getName().equals(name)) {
                int lcount = Math.min(item.getCount(), count);
                count -= lcount;
                Search take = new Search();
                take.setId(item.getId());
                take.setCount(lcount);
                take.setS(0);
                server(take);
                if (count > 0) {
                    GoBuilding gb = new GoBuilding();
                    gb.setN(0);
                    server(gb);
                } else {
                    isActive = false;
                }
                break;
            }
        }
        return true;
    }

    
    @Intercept(InterceptionType.CHAT_CLIENT)
    boolean onChatMessage(String original, Post post) {
        if (post.isPrivate() && post.getLogin().equals(state.getLogin()) && post.getMessage().startsWith("collect")) {
            isActive = true;
            String v[] = post.getMessage().split(" ");
            name = v[1];
            count = Integer.parseInt(v[2]);

            server(new Search());
            return true;
        }
        return false;
    }

}
