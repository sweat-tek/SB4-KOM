package dk.sdu.mmmi.cbse.gamestates;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.mmmi.cbse.entities.Bullet;
import dk.sdu.mmmi.cbse.entities.Player;
import dk.sdu.mmmi.cbse.managers.GameKeys;
import dk.sdu.mmmi.cbse.managers.GameStateManager;

import java.util.ArrayList;

public class PlayState extends GameState {
	
	private ShapeRenderer sr;
	
	private Player player;
	private ArrayList<Bullet> bullets;
	
	public PlayState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void init() {
		
		sr = new ShapeRenderer();

		this.bullets = new ArrayList<>();
		
		player = new Player(this.bullets);
		
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
		
	}
	
	public void draw() {
		// draw player
		player.draw(sr);

		// draw bullets
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).draw(sr);
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









