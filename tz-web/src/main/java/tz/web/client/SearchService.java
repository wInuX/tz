package tz.web.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.core.client.GWT;
import tz.web.client.condition.Condition;

/**
 * @author Dmitry Shyshkin
 */
@RemoteServiceRelativePath("/search")
public interface SearchService extends RemoteService {
    SearchQuery createQuery(Condition condition);

    /**
     * Utility/Convenience class.
     * Use SearchService.App.getInstance() to access static instance of SearchServiceAsync
     */
    public static class App {
        private static final SearchServiceAsync ourInstance = (SearchServiceAsync) GWT.create(SearchService.class);

        public static SearchServiceAsync getInstance() {
            return ourInstance;
        }
    }
}
