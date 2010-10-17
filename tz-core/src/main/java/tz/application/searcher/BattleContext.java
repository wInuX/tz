package tz.application.searcher;

import tz.xml.*;

import java.util.*;

/**
 * @author Dmitry Shyshkin
 */
public class BattleContext {
    private static Map<String, Double> prices = new HashMap<String, Double>();
    private BattleView view;
    private Battle battle;

    public BattleContext(BattleView view) {
        this.view = view;
        this.battle = view.getBattle();

    }

    public boolean hasBot(UserType type, int minLevel, int maxLevel) {
        for (User user : battle.getUsers()) {
            if (user.getUserType() == type && user.getLevel() >= minLevel && user.getLevel() <= maxLevel) {
                return true;
            }
        }
        return false;
    }

    public List<Item> getNewItems() {
        List<Item> r = new ArrayList<Item>();
        for (Item item : battle.getItems()) {
            r.add(item);
        }
        for (Turn turn : view.getTurns()) {
            for (Item item : turn.getItems()) {
                if (item.getText() == null) {
                    continue;
                }
                r.add(item);
            }
        }
        return r;
    }

    public double getPrice() {
        double price = 0;
        for (Item item : getNewItems()) {
            Double p = prices.get(item.getText());
            if (p != null) {
                price += p;
            }
        }
        return price;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(view.getId());
        sb.append(" [").append(battle.getLocationX()).append(",").append(battle.getLocationY()).append("]");
        sb.append(" ").append(battle.getBattleType().name().toLowerCase());
        List<User> users = new ArrayList<User>(battle.getUsers());
        Collections.sort(users, new Comparator<User>() {
            public int compare(User l, User r) {
                int ol = l.getUserType().ordinal();
                int or = r.getUserType().ordinal();
                if (ol != or) {
                    return ol - or;
                }
                return l.getLevel() - r.getLevel();
            }
        });

        Map<UserType, UserGroup> groups = new LinkedHashMap<UserType, UserGroup>();
        for (int i = 0, usersSize = users.size(); i < usersSize; i++) {
            User user = users.get(i);
            UserType type = user.getUserType();
            if (groups.containsKey(type)) {
                groups.get(type).add(user);
            } else {
                groups.put(type, new UserGroup(user));
            }
        }
        sb.append("\n");
        for (Map.Entry<UserType, UserGroup> entry : groups.entrySet()) {
            UserGroup group = entry.getValue();
            sb.append(" ");
            if (group.count > 1) {
                sb.append(group.count).append("*");
            }
            sb.append(entry.getKey().name().toLowerCase());
            sb.append("@");
            if (group.minLevel == group.maxLevel) {
                sb.append(group.minLevel);
            } else {

                sb.append("[").append(group.minLevel).append("-").append(group.maxLevel).append("]");
            }
        }
        sb.append("\n");
        Map<String, ItemGroup> itemGroups = new LinkedHashMap<String, ItemGroup>();
        List<Item> items = new ArrayList<Item>(getNewItems());
        double price = 0;
        boolean morePrices = false;
        for (Item item : items) {
            Double p = prices.get(item.getText());
            if (p != null) {
                price += p;
            } else {
                morePrices = true;
            }
        }
        Collections.sort(items, new Comparator<Item>() {
            public int compare(Item l, Item r) {
                if (l.getName() == null) {
                    throw new IllegalStateException(l.getText());
                }
                if (r.getName() == null) {
                    throw new IllegalStateException(r.getText());
                }
                return l.getName().compareTo(r.getName());
            }
        });
        for (Item item : items) {
            if (itemGroups.containsKey(item.getText())) {
                itemGroups.get(item.getText()).add(item);
            } else {
                itemGroups.put(item.getText(), new ItemGroup());
            }
        }
        sb.append((int) price);
        if (morePrices) {
            sb.append("+");
        }
        for (Map.Entry<String, ItemGroup> itemEntry : itemGroups.entrySet()) {
            ItemGroup group = itemEntry.getValue();
            sb.append(" ");
            if (group.count > 1) {
                sb.append(group.count).append("*");
            }
            sb.append(itemEntry.getKey());
            sb.append(";");
        }
        return sb.toString();
    }

    private static class UserGroup {
        private int count;
        private int minLevel = Integer.MAX_VALUE;
        private int maxLevel = Integer.MIN_VALUE;

        public UserGroup(User user) {
            add(user);
        }

        public UserGroup add(User user) {
            minLevel = Math.min(minLevel, user.getLevel());
            maxLevel = Math.max(maxLevel, user.getLevel());
            ++count;
            return this;
        }
    }

    private static class ItemGroup {
        private int count = 1;

        public void add(Item item) {
            ++count;
        }
    }

    static {
        prices.put("Metals", 1.3);
        prices.put("Precious metals", 1.1);
        prices.put("Polymers", 2.2);
        prices.put("Organic", 2.29);
        prices.put("Silicon", 1.2);
        prices.put("Radioactive materials", 3.4);
        prices.put("Gems", 2.1);
        prices.put("Venom", 0.85);
        
        prices.put("Rat Fang", 0.14);
        prices.put("Stich Blood", 0.1);
        prices.put("Stich Claw", 0.02);
        prices.put("Stich Skin", 0.5);
        prices.put("Stich Bone Marrow", 0.1);
        prices.put("Stich Meat", 0.07);

        prices.put("Rat Brain", 0.02);
        prices.put("Rat Skin", 0.02);
        prices.put("Rat Eyes", 0.05);
        prices.put("Rat Fang", 0.15);

        prices.put("Vzzik Wings", 0.05);
        prices.put("Vzzik Egg Fragments", 0.2);
        prices.put("Vzzik Shell Powder", 4.5);

        prices.put("Arachnid Silk", 0.08);

    }
}
