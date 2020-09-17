package com.eclipse.panel.viewController;

import junit.framework.TestCase;

public class CharacterControllerTest extends TestCase {

    public void testRenderCharacter() {
        int bodyPalette = 0;
        int headPalette = 0;
        int accTop = 0;
        int accMid = 0;
        int accLow = 0;
        CharacterController.jobsNameProp.forEach((k, v) -> {
            int job = Integer.parseInt(k.toString());
            CharacterController.headsProp.forEach((i, z) -> {
                int head = Integer.parseInt(i.toString());
                CharacterController.CHARACTER_PATH ="RENDER_TEST/0/";
                CharacterController.renderCharacter(job, head, 0, bodyPalette, headPalette, accTop, accMid, accLow, 0);
                CharacterController.CHARACTER_PATH ="RENDER_TEST/1/";
                CharacterController.renderCharacter(job, head, 1, bodyPalette, headPalette, accTop, accMid, accLow, 0);
            });
        });
    }
}