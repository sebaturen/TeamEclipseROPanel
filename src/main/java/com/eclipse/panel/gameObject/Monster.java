package com.eclipse.panel.gameObject;

import java.util.Date;

public class Monster {

    private int id;
    private int monster_id;
    private String monster_name;
    private int x;
    private int y;
    private String map_name;
    private long timestamp;

    public int getId() {
        return id;
    }

    public int getMonster_id() {
        return monster_id;
    }

    public String getMonster_name() {
        return monster_name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getMap_name() {
        return map_name;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "{\"_class\":\"Monster\", " +
                "\"id\":\"" + id + "\"" + ", " +
                "\"monster_id\":\"" + monster_id + "\"" + ", " +
                "\"monster_name\":" + (monster_name == null ? "null" : "\"" + monster_name + "\"") + ", " +
                "\"x\":\"" + x + "\"" + ", " +
                "\"y\":\"" + y + "\"" + ", " +
                "\"map_name\":" + (map_name == null ? "null" : "\"" + map_name + "\"") + ", " +
                "\"timestamp\":\"" + timestamp + "\"" +
                "}";
    }
}
