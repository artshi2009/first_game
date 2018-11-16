package ru.spaces.artshi2009.Logic;

import com.badlogic.gdx.graphics.Texture;
import ru.spaces.artshi2009.Object.Textures;

public class GameLogic {
    private int posX;
    private int posY;
    private int score = 0;
    private static final int dropX = 54;
    private static final int dropY = 92;
    private static final int drop_smallX = 30;
    private static final int drop_smallY = 51;

    private float drop_left = 200;
    private float drop_top = -100;
    private float drop_velocity = 200;
    private float replay_left = 760;
    private float replay_top = 22;

    Textures tex = new Textures();


    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getPosY() {
        return posY;
    }

    public float getDrop_velocity() {
        return drop_velocity;
    }

    public void setDrop_velocity(float drop_velocity) {
        this.drop_velocity = drop_velocity;
    }

    public void plusDrop_velocity(int x){
        this.drop_velocity += x;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void plusScore (int x){
        this.score += x;
    }

    public int getDropX_Drop_SmallX() {
        return getFallSmall() == tex.DROP_SMALL ? drop_smallX:dropX;
    }

    public int getDropY_Drop_SmallY(){
        return getFallSmall() == tex.DROP_SMALL ? drop_smallY:dropY;
    }

    public Texture getFallSmall(){
        return score > 9 ? tex.DROP_SMALL: tex.DROP;
    }

}
