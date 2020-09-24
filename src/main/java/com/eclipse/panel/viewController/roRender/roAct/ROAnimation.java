package com.eclipse.panel.viewController.roRender.roAct;

import java.util.ArrayList;
import java.util.List;

public class ROAnimation {

    private final List<ROFrame> roFrames = new ArrayList<>();

    public ROAnimation() {

    }

    public void addFrame(ROFrame roFrame) {
        roFrames.add(roFrame);
    }

    public ROFrame getRoFrame(int position) {
        return roFrames.get(position);
    }
}
