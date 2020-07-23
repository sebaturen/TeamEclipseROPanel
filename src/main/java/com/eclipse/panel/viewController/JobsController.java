package com.eclipse.panel.viewController;

import com.eclipse.panel.dbConnect.DBLoadObject;
import com.eclipse.panel.gameObject.Guild;
import com.eclipse.panel.gameObject.character.Character;
import com.eclipse.panel.gameObject.roDB.PlayableJob;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JobsController {

    public static List<PlayableJob> getJobList() {
        List<PlayableJob> playableJobs = new ArrayList<>();
        try {
            JsonArray jobs_db = DBLoadObject.dbConnect.select(
                    PlayableJob.TABLE_NAME,
                    new String[]{"id"}
            );

            for(JsonElement jobsInf : jobs_db) {
                JsonObject jobsIds = jobsInf.getAsJsonObject();
                playableJobs.add(new PlayableJob.Builder(jobsIds.get("id").getAsInt()).build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return playableJobs;
    }

    public static List<Character> getAllCharacters(int jobId) {

        List<Character> allCharacters = new ArrayList<>();

        try {
            JsonArray character_db = DBLoadObject.dbConnect.select(
                    Character.TABLE_NAME,
                    new String[]{"id"},
                    "job_id = ?",
                    new String[]{jobId+""}
            );

            for(JsonElement charInf : character_db) {
                JsonObject charIds = charInf.getAsJsonObject();
                allCharacters.add(new Character.Builder(charIds.get("id").getAsInt()).build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allCharacters;
    }


}
