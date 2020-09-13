package com.eclipse.panel.dbConnect;

import com.eclipse.panel.Logs;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.sql.SQLException;

public abstract class DBLoadObject {

    public static final DBConnect dbConnect = new DBConnect();

    // Attribute
    private String tableDB;
    private Class<?> classObject;
    private Gson gson = new GsonBuilder().serializeNulls().create();

    public DBLoadObject(String tableDB, Class<?> classObject) {
        this.tableDB = tableDB;
        this.classObject = classObject;
    }

    protected Object load(String where, long whereValue) { return load(where+"=?", new String[]{whereValue+""}); }
    protected Object load(String where, String whereValue) { return load(where+"=?", new String[]{whereValue}); }
    protected Object load(String where, String[] whereValue) {
        try {
            JsonArray dbSelect = dbConnect.select(
                    this.tableDB,
                    new String[]{"*"},
                    where,
                    whereValue,
                    true
            );
            
            if (dbSelect.size() == 1) {
                JsonObject eachElem = dbSelect.get(0).getAsJsonObject();
                JsonObject jElem = dbSelect.get(0).getAsJsonObject().deepCopy();
                for (String key : eachElem.keySet()) {
                    if (eachElem.get(key) == null || eachElem.get(key).isJsonNull()) {
                        jElem.remove(key);
                    }
                }
                Object g = null;
                try {
                    g = gson.fromJson(jElem, classObject);
                } catch (Exception e) {
                }
                return g;
            } else if (dbSelect.size() > 1) {
                Logs.errorLog(this.getClass(), "TO MANY DATA");
            } else {
                Logs.errorLog(this.getClass(), "DATA NOT FOUND - "+ dbConnect.getLastQuery());
            }
        } catch (SQLException e) {
            Logs.fatalLog(this.getClass(), "FAILED to load element ["+ where +"] -> "+ e);
        }
        return null;
    }

}
