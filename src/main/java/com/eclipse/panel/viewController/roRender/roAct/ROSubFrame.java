package com.eclipse.panel.viewController.roRender.roAct;

import java.util.Arrays;

public class ROSubFrame {

    private int offSetX;
    private int offSetY;
    private int image;
    private int direction;
    private byte[] color;
    private float scaleX;
    private float scaleY;
    private int rotation;
    private int dontJump;
    private int sizeX;
    private int sizeY;
    private int soundNo;

    public ROSubFrame() {

    }

    public int getOffSetX() {
        return offSetX;
    }

    public void setOffSetX(int offSetX) {
        this.offSetX = offSetX;
    }

    public int getOffSetY() {
        return offSetY;
    }

    public void setOffSetY(int offSetY) {
        this.offSetY = offSetY;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public float getScaleX() {
        return scaleX;
    }

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public int getDontJump() {
        return dontJump;
    }

    public void setDontJump(int dontJump) {
        this.dontJump = dontJump;
    }

    public int getSizeX() {
        return sizeX;
    }

    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }

    public int getSoundNo() {
        return soundNo;
    }

    public void setSoundNo(int soundNo) {
        this.soundNo = soundNo;
    }

    public byte[] getColor() {
        return color;
    }

    public void setColor(byte[] color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "{\"_class\":\"ROSubFrame\", " +
                "\"offSetX\":\"" + offSetX + "\"" + ", " +
                "\"offSetY\":\"" + offSetY + "\"" + ", " +
                "\"image\":\"" + image + "\"" + ", " +
                "\"direction\":\"" + direction + "\"" + ", " +
                "\"color\":" + Arrays.toString(color) + ", " +
                "\"scaleX\":\"" + scaleX + "\"" + ", " +
                "\"scaleY\":\"" + scaleY + "\"" + ", " +
                "\"rotation\":\"" + rotation + "\"" + ", " +
                "\"dontJump\":\"" + dontJump + "\"" + ", " +
                "\"sizeX\":\"" + sizeX + "\"" + ", " +
                "\"sizeY\":\"" + sizeY + "\"" + ", " +
                "\"soundNo\":\"" + soundNo + "\"" +
                "}";
    }
}
