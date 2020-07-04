package com.eclipse.panel.gameObject.character;

import com.eclipse.panel.dbConnect.DBLoadObject;

public class CharacterWantedItem {

    // Character Wanted Item DB
    public static final String TABLE_NAME = "character_wanted_item";
    public static final String TABLE_KEY = "id";

    // Attributes
    private int id;
    private int character_ro_id;
    private int item_ob;

    // Update control
    private long timestamp;

    // Internal info
    private Character character;
    private CharacterItemOb characterItemOb;

    public static class Builder extends DBLoadObject {

        private int id;
        public Builder(int id) {
            super(TABLE_NAME, CharacterWantedItem.class);
            this.id = id;
        }

        public CharacterWantedItem build() {
            CharacterWantedItem cwi = (CharacterWantedItem) load(TABLE_KEY, id);
            // Load info (?)
            return cwi;
        }
    }

    private CharacterWantedItem() {

    }

    @Override
    public String toString() {
        return "{\"_class\":\"CharacterWantedItem\", " +
                "\"id\":\"" + id + "\"" + ", " +
                "\"character_ro_id\":\"" + character_ro_id + "\"" + ", " +
                "\"item_ob\":\"" + item_ob + "\"" + ", " +
                "\"timestamp\":\"" + timestamp + "\"" + ", " +
                "\"character\":" + (character == null ? "null" : character) + ", " +
                "\"characterItemOb\":" + (characterItemOb == null ? "null" : characterItemOb) +
                "}";
    }
}
