package tz.xml;

import org.apache.log4j.Logger;

/**
 * @author Dmitry Shyshkin
 */
public class BuildingType {
    public static final Logger LOG = Logger.getLogger(BuildingType.class);

    private String code;
    private BuildingCategory category;

    public BuildingType(String code) {
        this.code = code;
        this.category = getBuildingCategoryFromCode(code);
    }

    public BuildingType(BuildingCategory category) {
        this.category = category;
        this.code = category.getCode();
    }

    public String getCode() {
        return code;
    }

    public BuildingCategory getCategory() {
        return category;
    }

    public static BuildingCategory getBuildingCategoryFromCode(String code) {
        for (BuildingCategory type : BuildingCategory.values()) {
            if (type.getCode().equals(code)){
                return type;
            }
        }
        LOG.debug("Unknown building category: " + code);
        return BuildingCategory.UNKNOWN;
    }
}
