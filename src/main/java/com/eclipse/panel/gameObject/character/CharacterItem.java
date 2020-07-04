package com.eclipse.panel.gameObject.character;

import com.eclipse.panel.dbConnect.DBLoadObject;

public class CharacterItem {

    // Character item DB
    public static final String TABLE_NAME = "character_item";
    public static final String TABLE_KEY = "id";

    // Attributes
    private int id;
    private int character_ro_id;
    private int item_ob;

    // Internal data
    private Character character;
    private CharacterItemOb characterItemOb;

    public static class Builder extends DBLoadObject {

        private int id;
        public Builder(int id) {
            super(TABLE_NAME, CharacterItem.class);
            this.id = id;
        }

        public CharacterItem build() {
            CharacterItem ci = (CharacterItem) load(TABLE_KEY, id);
            // Load accounts (?)
            return ci;
        }

    }

    private CharacterItem() {

    }

    @Override
    public String toString() {
        return "{\"_class\":\"CharacterItem\", " +
                "\"id\":\"" + id + "\"" + ", " +
                "\"character_ro_id\":\"" + character_ro_id + "\"" + ", " +
                "\"item_ob\":\"" + item_ob + "\"" + ", " +
                "\"character\":" + (character == null ? "null" : character) + ", " +
                "\"characterItemOb\":" + (characterItemOb == null ? "null" : characterItemOb) +
                "}";
    }
}
