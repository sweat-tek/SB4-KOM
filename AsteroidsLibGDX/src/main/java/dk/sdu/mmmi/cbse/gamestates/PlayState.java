package dk.sdu.mmmi.cbse.gamestates;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import dk.sdu.mmmi.cbse.entities.*;
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
	private ArrayList<Particle> particles;

	private FlyingSaucer flyingSaucer;
	private float fsTimer;
	private float fsTime;
	private ArrayList<Bullet> enemyBullets;

	private int level;
	private int totalAsteroids;
	private int numAsteroidsLeft;
	
	public PlayState(GameStateManager gsm) {
		super(gsm);
	}

	public void init() {

		// Set start level
		level = 1;

		// Generate a ShapeRender
		sr = new ShapeRenderer();

		// Populate Arrays
		this.bullets = new ArrayList<>();
		this.asteroids = new ArrayList<>();
		this.particles = new ArrayList<>();

		// Create player
		this.player = new Player(this.bullets);

		// Spawn Asteroids
		spawnAsteroids();

		fsTimer = 0;
		fsTime = 3;
		enemyBullets = new ArrayList<>();


	}

	private void createParticles( float x, float y ) {
		for (int i = 0; i < 6; i++) {
			particles.add(new Particle(x,y));
		}
	}

	private void splitAsteroids(Asteroid a) {
		createParticles(a.getX(), a.getY());
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

		// next level
		if( asteroids.size() == 0 ) {
			level++;
			spawnAsteroids();
		}

		// update player
		player.update(dt);
		if( player.isDead() ) {
			player.reset();
			flyingSaucer = null;
			//Jukebox.stop("smallsaucer");
			//Jukebox.stop("largesaucer");
			return;
		}

		// update player bullets
		for( int i = 0 ; i < bullets.size() ; i++ ) {
			bullets.get(i).update(dt);
			if( bullets.get(i).shouldRemove()) {
				bullets.remove(i);
				i--;
			}
		}

		// update flying saucer
		if( flyingSaucer == null ) {
			fsTimer += dt;
			if( fsTimer > fsTime ) {
				fsTimer = 0;
				int type = MathUtils.random() < 0.5 ? FlyingSaucer.LARGE : FlyingSaucer.SMALL;
				int direction = MathUtils.random() < 0.5 ? FlyingSaucer.RIGHT : FlyingSaucer.LEFT;
				flyingSaucer = new FlyingSaucer(type, direction, player, enemyBullets);
			}
		} else {
			flyingSaucer.update(dt);
			if( flyingSaucer.shouldRemove()) {
				flyingSaucer = null;
				//Jukebox.stop("smallsaucer");
				//Jukebox.stop("largesaucer");
			}
		}

		// update flyingsaucer bullets
		for( int i = 0 ; i < enemyBullets.size() ; i++ ) {
			enemyBullets.get(i).update(dt);
			if( enemyBullets.get(i).shouldRemove()) {
				enemyBullets.remove(i);
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

		// update particles
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).update(dt);
			if( particles.get(i).shouldRemove() ) {
				particles.remove(i);
				i--;
			}
		}

		// check collision
		checkCollision();
		
	}

	private void checkCollision() {
		// player-asteroid collision
		if( !player.isHit() ) {
			for (int i = 0; i < asteroids.size(); i++) {
				Asteroid a = asteroids.get(i);

				if( a.intersects(player)) {
					player.hit();
					asteroids.remove(i);
					splitAsteroids(a);
					break;
				}
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

		// flyingsaucer - player
		if( flyingSaucer != null ) {
			if( player.intersects(flyingSaucer) ) {
				player.hit();
				createParticles(player.getX(), player.getY());
				createParticles(flyingSaucer.getX(), flyingSaucer.getY());
				flyingSaucer = null;
				//Jukebox.stop("smallsaucer");
				//Jukebox.stop("largesaucer");
				//Jukebox.play("explode");
			}
		}

		// enemybullet - player
		if( !player.isHit() ) {
			for (int i = 0; i < enemyBullets.size(); i++) {
				Bullet b = enemyBullets.get(i);
				if( player.contains(b.getX(), b.getY())) {
					player.hit();
					enemyBullets.remove(i);
					i--;
					//Jukebox.play("explode");
					break;
				}
			}
		}

		// enemybullet - asteroids
		for (int i = 0; i < enemyBullets.size(); i++) {
			Bullet b = enemyBullets.get(i);
			for (int j = 0; j < asteroids.size(); j++) {
				Asteroid a = asteroids.get(i);
				if( a.contains(b.getX(), b.getY())) {
					asteroids.remove(j);
					j--;
					splitAsteroids(a);
					enemyBullets.remove(i);
					i--;
					createParticles(a.getX(), a.getY());
					//Jukebox.play("explode");
					break;
				}
			}
		}

		// playerbullet - flyingsaucer
		if( flyingSaucer != null ) {
			for (int i = 0; i < bullets.size(); i++) {
				Bullet b = bullets.get(i);
				if( flyingSaucer.contains(b.getX(), b.getY())) {
					bullets.remove(i);
					i--;
					createParticles(flyingSaucer.getX(), flyingSaucer.getY());
					//player.incrementScore(flyingSaucer.getScore());
					flyingSaucer = null;
					//Jukebox.stop("smallsaucer");
					//Jukebox.stop("largesaucer");
					//Jukebox.play("explode");

					break;
				}
			}
		}

		// asteroids - flyingsaucer
		if( flyingSaucer != null ) {
			for (int i = 0; i < asteroids.size(); i++) {
				Asteroid a = asteroids.get(i);
				if( a.intersects(flyingSaucer) ) {
					asteroids.remove(i);
					i--;
					splitAsteroids(a);
					createParticles(a.getX(), a.getY());
					createParticles(flyingSaucer.getX(), flyingSaucer.getY());
					flyingSaucer = null;
					//Jukebox.stop("smallsaucer");
					//Jukebox.stop("largesaucer");
					//Jukebox.play("explode");

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

		// draw flying saucer
		if( flyingSaucer != null ) {
			flyingSaucer.draw(sr);
		}

		// draw flyingsaucer bullets
		for (int i = 0; i < enemyBullets.size(); i++) {
			enemyBullets.get(i).draw(sr);
		}

		// draw asteroids
		for (int i = 0; i < asteroids.size(); i++) {
			asteroids.get(i).draw(sr);
		}

		// draw particles.
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).draw(sr);
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









