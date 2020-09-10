package com.eclipse.panel.viewController.rest;

import junit.framework.TestCase;
import org.junit.Test;

public class APIKeysTest extends TestCase {

    @Test
    public void testAPIKeyInfo() {
        String keyIn = "rgR+oq9u}$poKY])+HF?3dG,DNQl-|";
        APIKeys user = APIKeys.getValue(keyIn);
        System.out.println(APIKeys.getValue(keyIn));
        System.out.println(user);
    }
}