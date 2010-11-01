package tz.game.scenario;

import com.google.inject.Inject;
import tz.game.scenario.manager.LocationManager;
import tz.xml.LocationDirection;

/**
 * @author Dmitry Shyshkin
 */
public class ExplorationScenatio extends Scenario {
    @Inject
    private LocationManager locationManager;

    @Override
    public void execute() throws InterruptedException {
        while (true) {
            locationManager.move(LocationDirection.N);
            locationManager.move(LocationDirection.E);
            locationManager.move(LocationDirection.S);
            locationManager.move(LocationDirection.W);
        }
    }
}
