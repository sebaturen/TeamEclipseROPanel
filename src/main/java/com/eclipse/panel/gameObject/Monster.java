package com.eclipse.panel.gameObject;

import java.util.Date;

public class Monster {

    private int id;
    private int monster_id;
    private String monster_name;
    private int x;
    private int y;
    private String map_name;
    private final Date timeStamp = new Date();

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

    public Date getTimeStamp() {
        return timeStamp;
    }

    @Override
    public String toString() {
        return "{\"_class\":\"Monster\", " +
                "\"id\":\"" + id + "\"" + ", " +
                "\"monster_id\":\"" + monster_id + "\"" + ", " +
                "\"x\":\"" + x + "\"" + ", " +
                "\"y\":\"" + y + "\"" + ", " +
                "\"map_name\":" + (map_name == null ? "null" : "\"" + map_name + "\"") + ", " +
                "\"timeStamp\":" + (timeStamp == null ? "null" : timeStamp) +
                "}";
    }
}
