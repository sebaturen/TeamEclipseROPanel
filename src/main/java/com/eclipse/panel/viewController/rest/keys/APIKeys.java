package com.eclipse.panel.viewController.rest.keys;

import com.eclipse.panel.GeneralConfig;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class APIKeys {

    private static List<APIKey> keys = new ArrayList<>();

    static {
        String keysS = GeneralConfig.getStringConfig("API_KEYS");
        JsonObject keysContext = new Gson().fromJson(keysS, JsonObject.class);

        keysContext.entrySet().forEach(entry -> {
            APIKeysType type = APIKeysType.UNAUTHORIZED;
            JsonObject info = entry.getValue().getAsJsonObject();
            if (info.get("type").getAsString().equals("char_update")) type = APIKeysType.CHAR_UPDATE;
            if (info.get("type").getAsString().equals("woe_update")) type = APIKeysType.WOE_UPDATE;
            keys.add(new APIKey(entry.getKey(), info.get("key").getAsString(), type));
        });

    }

    public static APIKey getAPIKey(String val) {
        AtomicReference<APIKey> keyInfo = new AtomicReference<>(new APIKey("UNKNOWN", "-1", APIKeysType.UNAUTHORIZED));
        keys.forEach(eKeys -> {
            if (eKeys.getKey().equals(val)) {
                keyInfo.set(eKeys);
            }
        });
        return keyInfo.get();
    }
}