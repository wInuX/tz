@XmlAccessorType(value = XmlAccessType.NONE)
@XmlJavaTypeAdapters({
        @XmlJavaTypeAdapter(BuildingTypeAdapter.class),
        @XmlJavaTypeAdapter(ShotTypeAdapter.class),
        @XmlJavaTypeAdapter(LocationDirectionAdaptor.class),
        @XmlJavaTypeAdapter(ShotDefinitionSetAdapter.class),
        @XmlJavaTypeAdapter(SlotTypeAdapter.class),
        @XmlJavaTypeAdapter(IdAdapter.class)
})
package tz.xml;

import tz.xml.adaptor.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;

