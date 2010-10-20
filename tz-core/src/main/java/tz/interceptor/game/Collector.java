package tz.interceptor.game;

import com.google.inject.Inject;
import tz.xml.*;

/**
 * @author Dmitry Shyshkin
 */
public class Collector implements Controller {
    private boolean isActive = false;

    @Inject
    private GameState state;

    @Inject
    private GameModule game;
    private String name;
    private int count;

    public void attach() {
        game.registerInterceptors(this);
    }

    public void detach() {
        game.unregisterIntegerceptors(this);
    }

    @Intercept(InterceptionType.SERVER)
    boolean onGoBuilding(String original, GoBuilding goBuilding) {
        if (!isActive) {
            return false;
        }
        if (goBuilding.getN() == 0) {
            GoBuilding gb = new GoBuilding();
            gb.setN(2);
            game.getGameControl().server(new Message(gb));
        }
        if (goBuilding.getN() == 2) {
            game.getGameControl().server(new Message(new Search()));
        }
        return false;
    }

    @Intercept(InterceptionType.SERVER)
    boolean onSearch(String original, Search search) {
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
                game.getGameControl().server(new Message(take));
                if (count > 0) {
                    GoBuilding gb = new GoBuilding();
                    gb.setN(0);
                    game.getGameControl().server(new Message(gb));
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

            game.getGameControl().server(new Message(new Search()));
            return true;
        }
        return false;
    }

}
