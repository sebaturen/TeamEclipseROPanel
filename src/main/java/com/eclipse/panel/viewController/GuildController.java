package com.eclipse.panel.viewController;

import com.eclipse.panel.dbConnect.DBLoadObject;
import com.eclipse.panel.gameObject.Guild;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GuildController {

    public static List<Guild> getGuildList() {
        List<Guild> guilds = new ArrayList<>();
        try {
            JsonArray guilds_db = DBLoadObject.dbConnect.select(
                    Guild.TABLE_NAME,
                    new String[]{"id"}
            );

            for(JsonElement guildInf : guilds_db) {
                JsonObject guildIds = guildInf.getAsJsonObject();
                guilds.add(new Guild.Builder(guildIds.get("id").getAsInt()).build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return guilds;
    }

    public static Guild getGuild(int id) {
        return new Guild.Builder(id).fullLoad(true).build();
    }

}
