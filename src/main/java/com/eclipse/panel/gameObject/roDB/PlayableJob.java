package com.eclipse.panel.gameObject.roDB;

import com.eclipse.panel.dbConnect.DBLoadObject;

public class PlayableJob {

    // Playable Class DB
    public static final String TABLE_NAME = "jobs";
    public static final String TABLE_KEY = "id";

    // Attributes
    private int id;
    private String name;

    public static class Builder extends DBLoadObject {

        private int id;
        public Builder(int ro_id) {
            super(TABLE_NAME, PlayableJob.class);
            this.id = ro_id;
        }

        public PlayableJob build() {
            return (PlayableJob) load(TABLE_KEY, id);
        }

    }

    private PlayableJob() {

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "{\"_class\":\"PlayableJobs\", " +
                "\"id\":\"" + id + "\"" + ", " +
                "\"name\":" + (name == null ? "null" : "\"" + name + "\"") +
                "}";
    }
}
