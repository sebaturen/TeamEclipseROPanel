package com.eclipse.panel.gameObject.roDB;

import com.eclipse.panel.dbConnect.DBLoadObject;

public class Item {

    // Item DB
    public static final String TABLE_NAME = "items_db";
    public static final String TABLE_KEY = "ro_id";

    // Attributes
    private int ro_id;
    private String type;
    private String equip_position;
    private String name;
    private String description;
    private int total_cart;
    private boolean enchant_enable;
    private boolean is_custom;

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

    @Override
    public String toString() {
        return "{\"_class\":\"Item\", " +
                "\"ro_id\":\"" + ro_id + "\"" + ", " +
                "\"type\":" + (type == null ? "null" : "\"" + type + "\"") + ", " +
                "\"equip_position\":" + (equip_position == null ? "null" : "\"" + equip_position + "\"") + ", " +
                "\"name\":" + (name == null ? "null" : "\"" + name + "\"") + ", " +
                "\"description\":" + (description == null ? "null" : "\"" + description + "\"") + ", " +
                "\"total_cart\":\"" + total_cart + "\"" + ", " +
                "\"enchant_enable\":\"" + enchant_enable + "\"" + ", " +
                "\"is_custom\":\"" + is_custom + "\"" +
                "}";
    }
}
