package tz.xml;

import tz.xml.adaptor.MapParametersAdaptor;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

/**
 * @author Dmitry Shyshkin
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "MMP")
public class MiniMap {
    @XmlAttribute(name = "param")
    @XmlJavaTypeAdapter(MapParametersAdaptor.class)
    private List<Integer> parameters;

    @XmlElement(name = "L")
    private List<Location> locations;

    public List<Integer> getParameters() {
        return parameters;
    }

    public void setParameters(List<Integer> parameters) {
        this.parameters = parameters;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }
}
