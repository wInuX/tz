package tz.web.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import tz.web.client.SearchQuery;
import tz.web.client.SearchService;
import tz.web.client.condition.Condition;

/**
 * @author Dmitry Shyshkin
 */
public class SearchServiceImpl extends RemoteServiceServlet implements SearchService {
    public SearchQuery createQuery(Condition condition) {
        return null;
    }
}