package com.eclipse.panel.viewController.roRender.roAct;

import java.util.ArrayList;
import java.util.List;

public class ROFrame {

    private int extraInfo;
    private int extraX;
    private int extraY;
    private int soundNo;
    private final List<ROSubFrame> subFrames = new ArrayList<>();

    public ROFrame() {

    }

    public void addSubFrame(ROSubFrame roSubFrame) {
        subFrames.add(roSubFrame);
    }

    public int getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(int extraInfo) {
        this.extraInfo = extraInfo;
    }

    public int getExtraX() {
        return extraX;
    }

    public void setExtraX(int extraX) {
        this.extraX = extraX;
    }

    public int getExtraY() {
        return extraY;
    }

    public void setExtraY(int extraY) {
        this.extraY = extraY;
    }

    public int getSoundNo() {
        return soundNo;
    }

    public void setSoundNo(int soundNo) {
        this.soundNo = soundNo;
    }

    public List<ROSubFrame> getSubFrames() {
        return subFrames;
    }
}
