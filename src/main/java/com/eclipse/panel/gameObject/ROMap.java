package com.eclipse.panel.gameObject;

import com.eclipse.panel.dbConnect.DBLoadObject;

public class ROMap {

    // Map DB
    public static final String TABLE_NAME = "maps";
    public static final String TABLE_KEY = "id";

    // Attribute
    private int id;
    private String slug;
    private String name;

    public static class Builder extends DBLoadObject {

        private int id;
        public Builder(int castle_id) {
            super(TABLE_NAME, ROMap.class);
            this.id = castle_id;
        }

        public ROMap build() {
            ROMap c = (ROMap) load(TABLE_KEY, id);
            return c;
        }

    }

    private ROMap() {

    }

    public int getId() {
        return id;
    }

    public String getSlug() {
        return slug;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "{\"_class\":\"ROMap\", " +
                "\"id\":\"" + id + "\"" + ", " +
                "\"slug\":" + (slug == null ? "null" : "\"" + slug + "\"") + ", " +
                "\"name\":" + (name == null ? "null" : "\"" + name + "\"") +
                "}";
    }
}
