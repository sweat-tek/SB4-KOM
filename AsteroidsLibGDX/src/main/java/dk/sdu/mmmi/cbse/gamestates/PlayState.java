package dk.sdu.mmmi.cbse.gamestates;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import dk.sdu.mmmi.cbse.entities.Asteroid;
import dk.sdu.mmmi.cbse.entities.Bullet;
import dk.sdu.mmmi.cbse.entities.Enemy;
import dk.sdu.mmmi.cbse.entities.Player;
import dk.sdu.mmmi.cbse.managers.GameKeys;
import dk.sdu.mmmi.cbse.managers.GameStateManager;

import java.util.ArrayList;

public class PlayState extends GameState {
	
	private ShapeRenderer sr;
	
	private Player player;
	private ArrayList<Bullet> bullets;
	private ArrayList<Bullet> enemyBullets;
	private ArrayList<Asteroid> asteroids;
	private Enemy enemy;
	private float enemyTimer;
	private float enemyTime;

	public PlayState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void init() {
		
		sr = new ShapeRenderer();

		bullets = new ArrayList<>();
		player = new Player(bullets);


		asteroids = new ArrayList<Asteroid>();
		//for testing
		asteroids.add(new Asteroid(100,100, Asteroid.LARGE));
		asteroids.add(new Asteroid(200,100, Asteroid.MEDIUM));
		asteroids.add(new Asteroid(300,100, Asteroid.SMALL));

		enemyTimer = 0;
		enemyTime = 5;
		enemyBullets = new ArrayList<Bullet>();


	}
	
	public void update(float dt) {
		
		handleInput();
		
		player.update(dt);

		//update player bullets
		for (int i=0; i < bullets.size(); i++){
			bullets.get(i).update(dt);
			if (bullets.get(i).shouldRemove()){
				bullets.remove(i);
				i--;
			}
		}

		//update asteroids
		for(int i = 0; i < asteroids.size(); i++){
			asteroids.get(i).update(dt);
			if(asteroids.get(i).shouldRemove()){
				asteroids.remove(i);
				i--;
			}
		}

		//update enemySpaceShip
		if (enemy == null){
			enemyTimer += dt;
			if (enemyTimer >= enemyTime){
				enemyTimer = 0;
				int type = MathUtils.random() < 0.5 ? Enemy.SMALL : Enemy.LARGE;
				int direction = MathUtils.random() < 0.5 ? Enemy.right : Enemy.left;
				enemy = new Enemy(type, direction, player, enemyBullets);
			}
		}
		else {
			enemy.update(dt);
			if (enemy.shouldRemove()){
				enemy = null;
			}
		}

		//update enemybullets
		for (int i=0; i < enemyBullets.size(); i++){
			enemyBullets.get(i).update(dt);
			if (enemyBullets.get(i).shouldRemove()){
				enemyBullets.remove(i);
				i--;
			}
		}




	}
	
	public void draw() {
		player.draw(sr);

		//draw enemySpaceShip
		if(enemy != null){
			enemy.draw(sr);
		}

		//draw enemyBullets
		for(int i = 0; i < enemyBullets.size(); i++){
			enemyBullets.get(i).draw(sr);
		}

		//draw bullets
		for(int i = 0; i < bullets.size(); i++){
			bullets.get(i).draw(sr);
		}
		//draw asteroids
		for(int i = 0; i< asteroids.size(); i++){
			asteroids.get(i).draw(sr);
		}


	}
	
	public void handleInput() {
		player.setLeft(GameKeys.isDown(GameKeys.LEFT));
		player.setRight(GameKeys.isDown(GameKeys.RIGHT));
		player.setUp(GameKeys.isDown(GameKeys.UP));
		if (GameKeys.isPressed(GameKeys.SPACE)){
			player.shoot();
		}
	}
	
	public void dispose() {}
	
}









