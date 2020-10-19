package com.eclipse.panel.gameObject.roDB;

import com.eclipse.panel.dbConnect.DBLoadObject;

public class Item {

    // Item DB
    public static final String TABLE_NAME = "items_db";
    public static final String TABLE_KEY = "id";

    // Attributes
    private int id;
    private String name_english;
    private String name_japanese;
    private int type;
    private int price_buy;
    private int price_sell;
    private int weight;
    private int attack;
    private int defence;
    private int range;
    private int slots;
    private long equip_jobs;
    private int equip_upper;
    private int equip_genders;
    private int equip_locations;
    private int weapon_level;
    private int equip_level;
    private boolean refineable;
    private int view;
    private String script;
    private String equip_script;
    private String unequip_script;

    public static class Builder extends DBLoadObject {

        private int id;
        public Builder(int id) {
            super(TABLE_NAME, Item.class);
            this.id = id;
        }

        public Item build() {
            return (Item) load(TABLE_KEY, id);
        }

    }

    private Item() {

    }

    public int getId() {
        return id;
    }

    public String getName_english() {
        return name_english;
    }

    public String getName_japanese() {
        return name_japanese;
    }

    public int getType() {
        return type;
    }

    public int getPrice_buy() {
        return price_buy;
    }

    public int getPrice_sell() {
        return price_sell;
    }

    public int getWeight() {
        return weight;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefence() {
        return defence;
    }

    public int getRange() {
        return range;
    }

    public int getSlots() {
        return slots;
    }

    public long getEquip_jobs() {
        return equip_jobs;
    }

    public int getEquip_upper() {
        return equip_upper;
    }

    public int getEquip_genders() {
        return equip_genders;
    }

    public int getEquip_locations() {
        return equip_locations;
    }

    public int getWeapon_level() {
        return weapon_level;
    }

    public int getEquip_level() {
        return equip_level;
    }

    public boolean isRefineable() {
        return refineable;
    }

    public int getView() {
        return view;
    }

    public String getScript() {
        return script;
    }

    public String getEquip_script() {
        return equip_script;
    }

    public String getUnequip_script() {
        return unequip_script;
    }

    @Override
    public String toString() {
        return "{\"_class\":\"Item\", " +
                "\"id\":\"" + id + "\"" + ", " +
                "\"name_english\":" + (name_english == null ? "null" : "\"" + name_english + "\"") + ", " +
                "\"name_japanese\":" + (name_japanese == null ? "null" : "\"" + name_japanese + "\"") + ", " +
                "\"type\":\"" + type + "\"" + ", " +
                "\"price_buy\":\"" + price_buy + "\"" + ", " +
                "\"price_sell\":\"" + price_sell + "\"" + ", " +
                "\"weight\":\"" + weight + "\"" + ", " +
                "\"attack\":\"" + attack + "\"" + ", " +
                "\"defence\":\"" + defence + "\"" + ", " +
                "\"range\":\"" + range + "\"" + ", " +
                "\"slots\":\"" + slots + "\"" + ", " +
                "\"equip_jobs\":\"" + equip_jobs + "\"" + ", " +
                "\"equip_upper\":\"" + equip_upper + "\"" + ", " +
                "\"equip_genders\":\"" + equip_genders + "\"" + ", " +
                "\"equip_locations\":\"" + equip_locations + "\"" + ", " +
                "\"weapon_level\":\"" + weapon_level + "\"" + ", " +
                "\"equip_level\":\"" + equip_level + "\"" + ", " +
                "\"refineable\":\"" + refineable + "\"" + ", " +
                "\"view\":\"" + view + "\"" + ", " +
                "\"script\":" + (script == null ? "null" : "\"" + script + "\"") + ", " +
                "\"equip_script\":" + (equip_script == null ? "null" : "\"" + equip_script + "\"") + ", " +
                "\"unequip_script\":" + (unequip_script == null ? "null" : "\"" + unequip_script + "\"") +
                "}";
    }
}
