package tz.xml;

import org.apache.log4j.Logger;

/**
* @author Dmitry Shyshkin
*/
public enum BuildingCategory {
    ARENA("1"),
    ARSENAL("2"),
    SHOP("3"),
    BANK("4"),
    HOSPITAL("5"),
    CITY_HALL("7"),
    POST_OFFICE("9"),
    PORTAL("11"),
    TRIBUNAL("14"),
    PUB("15"),
    POLICE("16"),
    MINE("17"),
    UNIVERSITY("18"),
    MIITARY_BASE("19"),
    TENT("20"),
    GREENHOUSE("23"),
    CONCENTRATION_PLANT("24"),
    LIVE_HOUSE("26"),
    LABORATORY("30"),
    RECRUIT_BASE("35"),
    PUBLIC_PLANT("36"),
    PHARMACY("38"),
    CASINO("39"),
    ORDUNG_BASE("40"),
    CLAN_CENTER("52"),
    AUCTION("56"),
    HEADQUARTES("58"),
    MANSION("60"),
    FREEDOM_BASE("64"),
    STOCK_MARKET("113"),
    INSURANCE_COMPANY("114"),
    RUINED_HOUSE_5("175"),
    RUINED_HOUSE_3("186"),
    RUINED_HOUSE_4("187"),
    RUINED_HOUSE("189"),
    RUINED_HOUSE_2("190"),
    RUINED_STOCK_EXCHANGE("193"),
    RUINED_INSURANCE_COMPANY("193"),
    RUINED_INSURANCE_COMPANY_2("194"),
    UNKNOWN("?");

    private String code;

    BuildingCategory(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
