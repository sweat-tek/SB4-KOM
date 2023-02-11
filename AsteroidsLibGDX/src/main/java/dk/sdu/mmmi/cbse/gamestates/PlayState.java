package dk.sdu.mmmi.cbse.gamestates;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import dk.sdu.mmmi.cbse.entities.Asteroid;
import dk.sdu.mmmi.cbse.entities.Bullet;
import dk.sdu.mmmi.cbse.entities.Player;
import dk.sdu.mmmi.cbse.main.Game;
import dk.sdu.mmmi.cbse.managers.GameKeys;
import dk.sdu.mmmi.cbse.managers.GameStateManager;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PlayState extends GameState {
	
	private ShapeRenderer sr;
	
	private Player player;
	private ArrayList<Bullet> bullets;
	private ArrayList<Asteroid> asteroids;

	private int level;
	private int totalAsteroids;
	private int numAsteroidsLeft;
	
	public PlayState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void init() {
		
		sr = new ShapeRenderer();

		this.bullets = new ArrayList<>();

		this.player = new Player(this.bullets);

		asteroids = new ArrayList<>();

		level = 1;
		spawnAsteroids();

	}

	private void splitAsteroids(Asteroid a) {
		numAsteroidsLeft--;
		if( a.getType() == Asteroid.LARGE) {
			asteroids.add(new Asteroid(a.getX(), a.getY(), Asteroid.MEDIUM));
			asteroids.add(new Asteroid(a.getX(), a.getY(), Asteroid.MEDIUM));
		}
		if( a.getType() == Asteroid.MEDIUM) {
			asteroids.add(new Asteroid(a.getX(), a.getY(), Asteroid.SMALL));
			asteroids.add(new Asteroid(a.getX(), a.getY(), Asteroid.SMALL));
		}

	}

	private void spawnAsteroids() {
		asteroids.clear();
		int numToSpawn = 4 + level -1;
		totalAsteroids = numToSpawn*7;
		numAsteroidsLeft = totalAsteroids;

		for (int i = 0; i < numToSpawn; i++) {
			float x = MathUtils.random(Game.WIDTH);
			float y = MathUtils.random(Game.HEIGHT);
			float dx = x - player.getX();
			float dy = y - player.getY();
			float dist = (float) Math.sqrt(dx * dx + dy * dy);

			while( dist < 100 ) {
				x = MathUtils.random(Game.WIDTH);
				y = MathUtils.random(Game.HEIGHT);
				dx = x - player.getX();
				dy = y - player.getY();
				dist = (float) Math.sqrt(dx * dx + dy * dy);
			}

			asteroids.add(new Asteroid(x,y,Asteroid.LARGE));

		}
	}
	
	public void update(float dt) {

		// get user input
		handleInput();

		// update player
		player.update(dt);

		// update player bullets
		for( int i = 0 ; i < bullets.size() ; i++ ) {
			bullets.get(i).update(dt);
			if( bullets.get(i).shouldRemove()) {
				bullets.remove(i);
				i--;
			}

		}

		// update asteroids
		for (int i = 0; i < asteroids.size(); i++) {
			asteroids.get(i).update(dt);
			if( asteroids.get(i).shouldRemove() ) {
				asteroids.remove(i);
				i--;
			}
		}

		// check collision
		checkCollision();
		
	}

	private void checkCollision() {
		// player-asteroid collision
		for (int i = 0; i < asteroids.size(); i++) {
			Asteroid a = asteroids.get(i);

			if( a.intersects(player)) {
				player.hit();
				asteroids.remove(i);
				splitAsteroids(a);
				break;
			}
		}


		// bullet-asteroid
		for (int i = 0; i < bullets.size(); i++) {
			Bullet b = bullets.get(i);
			for (int j = 0; j < asteroids.size(); j++) {
				Asteroid a = asteroids.get(j);

				// If a contains b
				if(a.contains(b.getX(), b.getY()) ) {
					bullets.remove(i);
					i--;
					asteroids.remove(j);
					j--;
					splitAsteroids(a);
					break;
				}
			}
		}
	}
	
	public void draw() {
		// draw player
		player.draw(sr);

		// draw bullets
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).draw(sr);
		}

		// draw asteroids
		for (int i = 0; i < asteroids.size(); i++) {
			asteroids.get(i).draw(sr);
		}
	}
	
	public void handleInput() {
		player.setLeft(GameKeys.isDown(GameKeys.LEFT));
		player.setRight(GameKeys.isDown(GameKeys.RIGHT));
		player.setUp(GameKeys.isDown(GameKeys.UP));
		if( GameKeys.isPressed(GameKeys.SPACE) ) {
			player.shoot();
		}
	}
	
	public void dispose() {}
	
}









