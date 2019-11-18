package ru.spaces.artshi2009.Logic;

import com.badlogic.gdx.graphics.Texture;
import ru.spaces.artshi2009.Object.Textures;

public class GameLogic {
    private int posX = 0;
    private int posY = 0;
    private int score = 0;

    //    private float dropLeft = 200;
//    private float dropTop = -100;
    private float dropVelocity = 200;
//    private float replayLeft = 760;
//    private float replayTop = 22;



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

    public float getDropVelocity() {
        return dropVelocity;
    }

//    public void setDropVelocity(float dropVelocity) {
//        this.dropVelocity = dropVelocity;
//    }

    public void plusDropVelocity(int x){
        this.dropVelocity += x;
    }

    public int getScore() {
        return score;
    }

//    public void setScore(int score) {
//        this.score = score;
//    }

    public void plusScore (int x){
        this.score += x;
    }

    public int replacedDropX() {
        int dropX = 54;
        int dropSmallX = 30;
        return getFallSmall() == Textures.DROP_SMALL ? dropSmallX : dropX;
    }

    public int replacedDropY(){
        int dropY = 92;
        int dropSmallY = 51;
        return (getFallSmall() == Textures.DROP_SMALL) ? dropSmallY : dropY;
    }

    public Texture getFallSmall(){
        return score > 9 ? Textures.DROP_SMALL : Textures.DROP;
    }

    public int screenWidth() {
        return 800;
    }

    public int screenHeight() {
        return 480;
    }
}
