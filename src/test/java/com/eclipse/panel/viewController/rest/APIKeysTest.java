package com.eclipse.panel.viewController.rest;

import com.eclipse.panel.viewController.rest.keys.APIKey;
import com.eclipse.panel.viewController.rest.keys.APIKeys;
import junit.framework.TestCase;
import org.junit.Test;

public class APIKeysTest extends TestCase {

    @Test
    public void testAPIKeyInfo() {
        String keyIn = "rgR+oq9u}$poKY])+HF?3dG,DNQl-|";
        APIKey user = APIKeys.getAPIKey(keyIn);
        System.out.println(APIKeys.getAPIKey(keyIn));
        System.out.println(user);
    }
}