package com.eclipse.panel.viewController;

import junit.framework.TestCase;

public class CharacterControllerTest extends TestCase {

    public void testRenderCharacter() {
        int bodyPalette = 0;
        int headPalette = 0;
        int accTop = 0;
        int accMid = 0;
        int accLow = 0;
        /*CharacterController.jobsNameProp.forEach((k, v) -> {
            int job = Integer.parseInt(k.toString());
            //CharacterController.headsProp.forEach((i, z) -> {
                //int head = Integer.parseInt(i.toString());
                int head = 3;
                CharacterController.CHARACTER_PATH ="RENDER_TEST/0/";
                CharacterController.renderCharacter(job, head, 0, bodyPalette, headPalette, accTop, accMid, accLow, 41,3);
                CharacterController.CHARACTER_PATH ="RENDER_TEST/1/";
                CharacterController.renderCharacter(job, head, 1, bodyPalette, headPalette, accTop, accMid, accLow, 41,3);
            //});
        });*/


        CharacterController.CHARACTER_PATH ="RENDER_TEST/0/";
        CharacterController.renderCharacter(19, 3, 0, 0, 0 , 522, 403, 0, 0,0);
        //CharacterController.renderCharacter(4018, 21, 0, 2, 6 , 224, 125, 829, 0,0);
        //CharacterController.renderCharacter(4017, 2, 0, 0, 6 , 0, 0, 0, 0,0);
        //CharacterController.renderCharacter(4017, 7, 0, 0, 6 , 283, 1528, 1579, 41,3);
        //CharacterController.renderCharacter(4014, 7, 0, 2, 0 , 1675, 12, 1691, 41,3);

    }
}