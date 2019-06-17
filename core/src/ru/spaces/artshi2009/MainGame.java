package ru.spaces.artshi2009;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
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
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Vector3 touchPos;
	private Array<Rectangle>raindrops;
	private Texture background;
	private Texture background2;
	private Texture background3;
	private Texture drop;
	private Texture dropSmall;
	private Texture gameOver;
	private Texture replay;
	private Sound dropSound;
	private Music rainSound;
	private GameLogic method;
	private BitmapFont font;
	private BitmapFont font1;
	private Boolean gOver = false;


	@Override
	public void create() {
		batch = new SpriteBatch();
		touchPos = new Vector3();
		method = new GameLogic();
		font = new BitmapFont();
		font1 = new BitmapFont();
		camera = new OrthographicCamera();

		camera.setToOrtho(false, 800, 480);

		font.setColor(Color.LIME);
		font1.setColor(Color.RED);

        background = Textures.BACKGROUND;
        background2 = Textures.BACKGROUND2;
        background3 = Textures.BACKGROUND3;
        drop = Textures.DROP;
        dropSmall = Textures.DROP_SMALL;
        gameOver = Textures.GAME_OVER;
        replay = Textures.REPLAY;

        dropSound = Sounds.DROP_SOUND;
        rainSound = Sounds.RAIN_SOUND;

		rainSound.setLooping(true);
		rainSound.play();

		raindrops = new Array<Rectangle>();
		spawnRaindrop();
	}

	private void spawnRaindrop(){
		Rectangle raindrop = new Rectangle();
		raindrop.x = MathUtils.random(0, method.screenWidth() - method.replacedDropX());
		raindrop.y = method.screenHeight();
		raindrop.width = method.replacedDropX();
		raindrop.height = method.replacedDropY();
		raindrops.add(raindrop);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

		camera.update();

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		drawBackground();
		for (Rectangle raindrop:raindrops){
			batch.draw(method.getFallSmall(), raindrop.x, raindrop.y);
		}
		font.draw(batch, "SCORE: " + method.getScore(), 10, method.screenHeight()-10);
		font1.draw(batch, "Pre-ALPHA", method.screenWidth()-90, method.screenHeight()-10);
		gameOver();
		batch.end();

		onClick();

		Iterator<Rectangle> iter = raindrops.iterator();
		creatingDrop(iter);
	}

	private void drawBackground() {
		if (method.getScore() < 51){
			batch.draw(background, 0, method.screenHeight()-457);
		} else if (method.getScore() > 50 && method.getScore() < 101){
			batch.draw(background2, 0, method.screenHeight()-510);
		} else if (method.getScore() > 100) {
			batch.draw(background3, 0, method.screenHeight()-480);
		}
	}

	private void creatingDrop(Iterator<Rectangle> iter) {
		while (iter.hasNext()){
			Rectangle raidrop = iter.next();
			raidrop.y -= method.getDropVelocity() * Gdx.graphics.getDeltaTime();
			if (raidrop.y + 92 < 0) {
				dropSound.play();
				rainSound.stop();
				iter.remove();
				gOver = true;
			}
			clickOnDrop(iter, raidrop);
		}
	}

	private void clickOnDrop(Iterator<Rectangle> iter, Rectangle raidrop) {
		if (raidrop.y <= method.getPosY() && method.getPosY() <= (raidrop.y + method.replacedDropY()) &&
				raidrop.x <= method.getPosX() && method.getPosX() <= (raidrop.x + method.replacedDropX())) {
			dropSound.play();
			iter.remove();
			spawnRaindrop();
			method.plusScore(1);
			method.plusDropVelocity(1);
		}
	}

	private void onClick() {
		if (Gdx.input.isTouched()){
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			method.setPosX((int) touchPos.x);
			method.setPosY((int) touchPos.y);
		}
	}

	@Override
	public void dispose () {
		background.dispose();
		drop.dispose();
		dropSmall.dispose();
		dropSound.dispose();
		rainSound.dispose();
		font.dispose();
		font1.dispose();
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

	private void gameOver(){
		if (gOver){
			batch.draw(gameOver, (float)method.screenWidth()/2-157,(float)method.screenHeight()/2);
			batch.draw(replay, (float)method.screenWidth()/2-45, (float)method.screenHeight()/2-45);
		}
	}
}
