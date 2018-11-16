package ru.spaces.artshi2009;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

import ru.spaces.artshi2009.Logic.GameLogic;
import ru.spaces.artshi2009.Object.Sounds;
import ru.spaces.artshi2009.Object.Textures;


public class MainGame extends ApplicationAdapter {
	OrthographicCamera camera;
	SpriteBatch batch;
	Vector3 touchPos;
	Array<Rectangle>raindrops;
    Texture background;
    Texture drop;
    Texture drop_small;
    Texture game_over;
    Texture replay;
    Sound drop_sound;
    Music rain_sound;
    GameLogic met;
	BitmapFont font;
	BitmapFont font1;
	Boolean g_over = false;

	
	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		batch = new SpriteBatch();
		touchPos = new Vector3();
		met = new GameLogic();
		font = new BitmapFont();
		font1 = new BitmapFont();

		font.setColor(Color.LIME);
		font1.setColor(Color.RED);

        background = Textures.BACKGROUND;
        drop = Textures.DROP;
        drop_small = Textures.DROP_SMALL;
        game_over = Textures.GAME_OVER;
        replay = Textures.REPLAY;

        drop_sound = Sounds.DROP_SOUND;
        rain_sound = Sounds.RAIN_SOUND;

		rain_sound.setLooping(true);
		rain_sound.play();

		raindrops = new Array<Rectangle>();
		spawnRaindrop();
	}

	public void spawnRaindrop(){
		Rectangle raindrop = new Rectangle();
		raindrop.x = MathUtils.random(0, 800-met.getDropX_Drop_SmallX());
		raindrop.y = 480;
		raindrop.width = met.getDropX_Drop_SmallX();
		raindrop.height = met.getDropY_Drop_SmallY();
		raindrops.add(raindrop);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(background, 0, 480-457);
		for (Rectangle raindrop:raindrops){
			batch.draw(met.getFallSmall(), raindrop.x, raindrop.y);
		}
		font.draw(batch, "SCORE: " + met.getScore(), 10, 480-10);
		font1.draw(batch, "Pre-ALPHA", 800-90, 480-10);
		GameOver();
		batch.end();

		if (Gdx.input.isTouched()){
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			met.setPosX((int) touchPos.x);
			met.setPosY((int) touchPos.y);
		}

		Iterator<Rectangle> iter = raindrops.iterator();
		while (iter.hasNext()){
			Rectangle raidrop = iter.next();
			raidrop.y -= met.getDrop_velocity() * Gdx.graphics.getDeltaTime();
			if (raidrop.y + 92 < 0) {
				drop_sound.play();
				rain_sound.stop();
				iter.remove();
				g_over = true;
			}

			if (raidrop.y <= met.getPosY() && met.getPosY() <= (raidrop.y + met.getDropY_Drop_SmallY()) && raidrop.x <= met.getPosX() && met.getPosX() <= (raidrop.x + met.getDropX_Drop_SmallX())) {
				drop_sound.play();
				System.out.println("коорд капли" + raidrop.x + " " + raidrop.y + "| Коорд клика" + touchPos);
				iter.remove();
				spawnRaindrop();
				met.plusScore(1);
				met.plusDrop_velocity(1);
			}
		}
	}
	
	@Override
	public void dispose () {
		background.dispose();
		drop.dispose();
		drop_small.dispose();
		drop_sound.dispose();
		rain_sound.dispose();
		font.dispose();
		batch.dispose();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

	private void GameOver(){
		if (g_over == true){
			batch.draw(game_over, 800/2-157,480/2);
			batch.draw(replay, 800/2-45, 480/2-45);
		}
	}
}
