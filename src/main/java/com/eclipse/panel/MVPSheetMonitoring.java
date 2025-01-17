package com.eclipse.panel;

import com.eclipse.panel.gameObject.Monster;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.*;
import com.google.gson.JsonObject;

import java.io.*;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MVPSheetMonitoring implements Runnable {

    private static final String APPLICATION_NAME = "Eclipse-MVPReport";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final String GOOGLE_SHEET_ID = "1z7j6Xr01P5gZRAfTtOkeNAzXG1IlKO-6MkO7BIqCtXs";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);
    private static final String CREDENTIALS_FILE_PATH = "tokens/google_credentials.json";

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        ClassLoader classLoader = MVPSheetMonitoring.class.getClassLoader();
        InputStream in = new FileInputStream(Objects.requireNonNull(classLoader.getResource(CREDENTIALS_FILE_PATH)).getFile());
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));


        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(Objects.requireNonNull(classLoader.getResource(TOKENS_DIRECTORY_PATH)).getFile())))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    /**
     * Prints the names and majors of students in a sample spreadsheet:
     */
    public List<Monster> getMonsterTimes() {
        try {
            // Build a new authorized API client service.
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            final String range = "Tiempos!A3:E50";
            Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                    .setApplicationName(APPLICATION_NAME)
                    .build();
            ValueRange response = service.spreadsheets().values()
                    .get(GOOGLE_SHEET_ID, range)
                    .execute();
            List<List<Object>> values = response.getValues();
            List<Monster> spawnMonsterList = new ArrayList<>();
            if (values == null || values.isEmpty()) {
                Logs.infoLog(this.getClass(), "No data found - monster time");
            } else {
                for (List row : values) {
                    // Print columns A and E, which correspond to indices 0 and 4.
                    if (row.size() >= 3 && !row.get(4).toString().equals("--")) {
                        // Get an information
                        int sheetId = Integer.parseInt(row.get(0).toString());
                        String name = row.get(1).toString();
                        String spawnTime = row.get(4).toString();
                        Calendar c = Calendar.getInstance();
                        c.add(Calendar.HOUR_OF_DAY, Integer.parseInt(spawnTime.split(":")[0]));
                        c.add(Calendar.MINUTE, Integer.parseInt(spawnTime.split(":")[1]));
                        //  Calculate differences
                        Date n = new Date();
                        long diff = c.getTime().getTime() - n.getTime();
                        // Save current information
                        Monster spawnInfo = new Monster();
                        spawnInfo.setId(sheetId);
                        spawnInfo.setMonster_name(name);
                        spawnInfo.setTimestamp(diff);
                        spawnMonsterList.add(spawnInfo);
                    }
                }
            }
            return spawnMonsterList;
        } catch (IOException | GeneralSecurityException e) {
            Logs.fatalLog(this.getClass(), "FAILED to inspect mvp sheets - "+ e);
        }
        return null;
    }

    private void inspectTime() {
        // Sort and check if need notification
        List<Monster> spawnMonsterList = getMonsterTimes();
        if (spawnMonsterList != null && spawnMonsterList.size() != 0) {
            DiscordBot.shared.reportDelayTime(spawnMonsterList);
        }
    }

    private void updateTimers() {
        try {
            // Build a new authorized API client service.
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            final String range = "Tiempos!A1";
            Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                    .setApplicationName(APPLICATION_NAME)
                    .build();
            ValueRange response = service.spreadsheets().values()
                    .get(GOOGLE_SHEET_ID, range)
                    .setValueRenderOption("FORMULA")
                    .execute();
            List<List<Object>> values = response.getValues();
            if (values == null || values.isEmpty()) {
                Logs.infoLog(this.getClass(), "No data found - update time");
            } else {
                String sFormula = String.valueOf(values.get(0).get(0));
                if (sFormula.startsWith("=")) {
                    // PUT the formula back, getting the value
                    UpdateValuesResponse updateResponse;
                    ValueRange content = new ValueRange();

                    content.setRange(range);
                    content.setValues(Collections.singletonList(Collections.singletonList((Object) sFormula)));

                    service.spreadsheets().values()
                            .update(GOOGLE_SHEET_ID, range, content)
                            .setValueInputOption("USER_ENTERED")
                            .setIncludeValuesInResponse(Boolean.TRUE)
                            .setResponseValueRenderOption("FORMATTED_VALUE")
                            .execute();
                }
            }
        } catch (IOException | GeneralSecurityException e) {
            Logs.fatalLog(this.getClass(), "FAILED to inspect mvp sheets - "+ e);
        }
    }

    public void reportMonsterDied(JsonObject monsterInf) {
        final String range = "TiemposLupita!A:D";
        Monster monster = DiscordBot.shared.getMonsterReport(monsterInf.get("id").getAsInt());
        if (monster != null) {
            try {
                Calendar dieTime = Calendar.getInstance();
                dieTime.setTimeInMillis(monsterInf.get("timestamp").getAsLong());
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                SimpleDateFormat dateSdf = new SimpleDateFormat("dd/MM/yyyy");
                sdf.setTimeZone(TimeZone.getTimeZone("GMT-7"));
                dateSdf.setTimeZone(TimeZone.getTimeZone("GMT-7"));

                // Build a new authorized API client service.
                final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

                List<List<Object>> infInsert = new ArrayList<>();
                List<Object> cellData = new ArrayList<>();
                cellData.add(monster.getId()+"");
                cellData.add(monster.getMonster_id()+"");
                cellData.add(monster.getMonster_name()+"");
                if (monster.getMonster_name() != null) {
                    cellData.add(monster.getMap_name());
                } else {
                    cellData.add("unknown");
                }
                cellData.add(sdf.format(dieTime.getTime()));
                cellData.add(dateSdf.format(dieTime.getTime()));
                infInsert.add(cellData);

                ValueRange content = new ValueRange();
                content.setRange(range);
                content.setValues(infInsert);

                Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                        .setApplicationName(APPLICATION_NAME)
                        .build();

                AppendValuesResponse response = service.spreadsheets().values()
                        .append(GOOGLE_SHEET_ID, range, content)
                        .setValueInputOption("RAW")
                        .execute();

            } catch (GeneralSecurityException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        updateTimers();
        inspectTime();
    }

}
