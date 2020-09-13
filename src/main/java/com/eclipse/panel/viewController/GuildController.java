package com.eclipse.panel.viewController;

import com.eclipse.panel.Logs;
import com.eclipse.panel.dbConnect.DBLoadObject;
import com.eclipse.panel.gameObject.Guild;
import com.eclipse.panel.gameObject.woe.CastleBreaker;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GuildController {

    public static List<Guild> getGuildList() {
        List<Guild> guilds = new ArrayList<>();
        try {
            JsonArray guilds_db = DBLoadObject.dbConnect.select(
                    Guild.TABLE_NAME,
                    new String[]{"id"},
                    "1=? AND name is not null order by id",
                    new String[] {"1"}
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

    public static List<CastleBreaker> getBreakers(String time) throws ParseException {
        List<CastleBreaker> castleBreakersList = new ArrayList<>();
        SimpleDateFormat sFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            Date woeDayDate;
            if (time.length() == 8) {
                woeDayDate = sFormat.parse(time);
            } else {
                Calendar c = Calendar.getInstance();
                while (c.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
                    c.add(Calendar.DAY_OF_MONTH, -1);
                }
                woeDayDate = c.getTime();
            }
            Calendar cal = Calendar.getInstance();
            cal.setTime(woeDayDate);
            cal.add(Calendar.DAY_OF_MONTH, 1);
            Date woeNexDayDate = cal.getTime();
            long timeWoeFrom = woeDayDate.getTime();
            long timeWoeTo = woeNexDayDate.getTime();
            JsonArray breakers_db = DBLoadObject.dbConnect.select(
                    CastleBreaker.TABLE_NAME,
                    new String[]{CastleBreaker.TABLE_KEY},
                    "timestamp BETWEEN ? AND ? ORDER BY timestamp ASC",
                    new String[]{ timeWoeFrom+"", timeWoeTo+""}
            );

            for(JsonElement breakInf : breakers_db) {
                castleBreakersList.add(
                        new CastleBreaker.Builder(
                                breakInf.getAsJsonObject().get(CastleBreaker.TABLE_KEY).getAsInt()
                        ).build()
                );
            }

        } catch (SQLException e) {
            Logs.errorLog(GuildController.class, "Failed to get breakers time...");
        }

        return castleBreakersList;
    }

}



















