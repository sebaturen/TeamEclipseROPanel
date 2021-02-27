package com.eclipse.panel.viewController.rest.keys;

public class APIKey {

    private String name;
    private String key;
    private APIKeysType type;

    public APIKey(String name, String key, APIKeysType type) {
        this.name = name;
        this.key = key;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public APIKeysType getType() {
        return type;
    }
}