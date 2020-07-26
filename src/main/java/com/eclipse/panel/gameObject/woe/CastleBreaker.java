package com.eclipse.panel.gameObject.woe;

import com.eclipse.panel.dbConnect.DBLoadObject;
import com.eclipse.panel.gameObject.Guild;

public class CastleBreaker {

    // WoE Breaker DB
    public static final String TABLE_NAME = "castle_breaker";
    public static final String TABLE_KEY = "id";

    // Attribute
    private int id;
    private int cast_id;
    private int guild_id;
    private String guild_temp_name;

    // Internal data
    private Castle castle;
    private Guild guild;

    // Timers
    private long timestamp;

    public static class Builder extends DBLoadObject {

        private int id;

        public Builder(int break_id) {
            super(TABLE_NAME, CastleBreaker.class);
            this.id = break_id;
        }

        public CastleBreaker build() {
            CastleBreaker cb = (CastleBreaker) load(TABLE_KEY, id);
            cb.castle = new Castle.Builder(cb.cast_id).build();
            if (cb.guild_id != 0) {
                cb.guild = new Guild.Builder(cb.guild_id).build();
            }
            return cb;
        }

    }

    private CastleBreaker() {

    }

    public String getGuild_temp_name() {
        return guild_temp_name;
    }

    public Castle getCastle() {
        return castle;
    }

    public Guild getGuild() {
        return guild;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "{\"_class\":\"CastleBreaker\", " +
                "\"id\":\"" + id + "\"" + ", " +
                "\"cast_id\":\"" + cast_id + "\"" + ", " +
                "\"guild_id\":\"" + guild_id + "\"" + ", " +
                "\"guild_temp_name\":\"" + guild_temp_name + "\"" + ", " +
                "\"timestamp\":\"" + timestamp + "\"" +
                "}";
    }
}
