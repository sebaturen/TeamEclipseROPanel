package com.eclipse.panel.gameObject.woe;

import com.eclipse.panel.dbConnect.DBLoadObject;
import com.eclipse.panel.gameObject.ROMap;

public class Castle {

    // Castle DB
    public static final String TABLE_NAME = "castles";
    public static final String TABLE_KEY = "id";

    // Attribute
    private int id;
    private int cast_number;
    private String name;
    private int map_id;
    private String castle_map_name;

    // Internal data
    private ROMap map;

    public static class Builder extends DBLoadObject {

        private int id;
        public Builder(int castle_id) {
            super(TABLE_NAME, Castle.class);
            this.id = castle_id;
        }

        public Castle build() {
            Castle c = (Castle) load(TABLE_KEY, id);
            c.map = new ROMap.Builder(c.map_id).build();
            return c;
        }

    }

    private Castle() {

    }

    public ROMap getMap() {
        return map;
    }

    public String getCastle_map_name() {
        return castle_map_name;
    }

    public void setCastle_map_name(String castle_map_name) {
        this.castle_map_name = castle_map_name;
    }

    public int getId() {
        return id;
    }

    public int getCast_number() {
        return cast_number;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "{\"_class\":\"Castle\", " +
                "\"id\":\"" + id + "\"" + ", " +
                "\"cast_number\":\"" + cast_number + "\"" + ", " +
                "\"name\":" + (name == null ? "null" : "\"" + name + "\"") + ", " +
                "\"map_id\":\"" + map_id + "\"" + ", " +
                "\"castle_map_name\":" + (castle_map_name == null ? "null" : "\"" + castle_map_name + "\"") +
                "}";
    }
}
