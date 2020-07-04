package com.eclipse.panel.gameObject.character;

import com.eclipse.panel.dbConnect.DBLoadObject;
import com.google.gson.JsonElement;

import java.util.Arrays;
import java.util.List;

public class CharacterEquipSet {

    // CharacterEquipSet DB
    public static final String TABLE_NAME = "character_equip_set";
    public static final String TABLE_KEY = "id";

    // Attributes
    private int id;
    private int character_ro_id;
    private JsonElement items_ob;

    // Internal data
    private Character character;
    private List<CharacterItemOb> itemsOb;

    public static class Builder extends DBLoadObject {

        private int id;
        public Builder(int id) {
            super(TABLE_NAME, CharacterEquipSet.class);
            this.id = id;
        }

        public CharacterEquipSet build() {
            CharacterEquipSet u = (CharacterEquipSet) load(TABLE_KEY, id);
            // Load accounts (?)
            return u;
        }

    }

    private CharacterEquipSet() {

    }

    @Override
    public String toString() {
        return "{\"_class\":\"CharacterEquipSet\", " +
                "\"id\":\"" + id + "\"" + ", " +
                "\"character_ro_id\":\"" + character_ro_id + "\"" + ", " +
                "\"items_ob\":" + (items_ob == null ? "null" : items_ob) + ", " +
                "\"character\":" + (character == null ? "null" : character) + ", " +
                "\"itemsOb\":" + (itemsOb == null ? "null" : Arrays.toString(itemsOb.toArray())) +
                "}";
    }
}
