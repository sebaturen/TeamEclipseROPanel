package com.eclipse.panel.viewController.rest;

import java.util.Arrays;
import java.util.Optional;

public enum APIKeys {
    UNKNOWN("-1"),
    CLEIN("rgR+oq9u}$poKY])+HF?3dG,DNQl-|"),
    GUILDER("Xuoy0rA<Zur0Gv4(>a-fKQW0,FsP*="),
    TURKO("ucXR2GitnokQ96xjIHZN3v33UROJj86W"),
    WOE_KEY_AUTH("GriPd5rXzRTjS3ZO4xw8ekVTQMgipSUX");

    private final String ident;

    APIKeys(String ident) {
        this.ident = ident;
    }

    public static APIKeys getValue(String ident) {
        Optional<APIKeys> opt =  Arrays.stream(values())
                .filter(APIKeys -> APIKeys.ident.equals(ident))
                .findFirst();

        return opt.orElse(APIKeys.UNKNOWN);
    }

}
