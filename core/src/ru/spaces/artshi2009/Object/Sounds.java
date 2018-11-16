package ru.spaces.artshi2009.Object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class Sounds {
    public static final Sound DROP_SOUND = Gdx.audio.newSound(Gdx.files.internal("dropSound.wav"));
    public static final Music RAIN_SOUND = Gdx.audio.newMusic(Gdx.files.internal("rainSound.mp3"));
}
