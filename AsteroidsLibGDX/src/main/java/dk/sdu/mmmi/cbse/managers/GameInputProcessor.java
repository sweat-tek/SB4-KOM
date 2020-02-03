package dk.sdu.mmmi.cbse.managers;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;

public class GameInputProcessor extends InputAdapter {
	
	public boolean keyDown(int k) {
		if(k == Keys.UP) {
			GameKeys.setKey(GameKeys.UP, true);
		}
		if(k == Keys.LEFT) {
			GameKeys.setKey(GameKeys.LEFT, true);
		}
		if(k == Keys.DOWN) {
			GameKeys.setKey(GameKeys.DOWN, true);
		}
		if(k == Keys.RIGHT) {
			GameKeys.setKey(GameKeys.RIGHT, true);
		}
		if(k == Keys.ENTER) {
			GameKeys.setKey(GameKeys.ENTER, true);
		}
		if(k == Keys.ESCAPE) {
			GameKeys.setKey(GameKeys.ESCAPE, true);
		}
		if(k == Keys.SPACE) {
			GameKeys.setKey(GameKeys.SPACE, true);
		}
		if(k == Keys.SHIFT_LEFT || k == Keys.SHIFT_RIGHT) {
			GameKeys.setKey(GameKeys.SHIFT, true);
		}
		return true;
	}
	
	public boolean keyUp(int k) {
		if(k == Keys.UP) {
			GameKeys.setKey(GameKeys.UP, false);
		}
		if(k == Keys.LEFT) {
			GameKeys.setKey(GameKeys.LEFT, false);
		}
		if(k == Keys.DOWN) {
			GameKeys.setKey(GameKeys.DOWN, false);
		}
		if(k == Keys.RIGHT) {
			GameKeys.setKey(GameKeys.RIGHT, false);
		}
		if(k == Keys.ENTER) {
			GameKeys.setKey(GameKeys.ENTER, false);
		}
		if(k == Keys.ESCAPE) {
			GameKeys.setKey(GameKeys.ESCAPE, false);
		}
		if(k == Keys.SPACE) {
			GameKeys.setKey(GameKeys.SPACE, false);
		}
		if(k == Keys.SHIFT_LEFT || k == Keys.SHIFT_RIGHT) {
			GameKeys.setKey(GameKeys.SHIFT, false);
		}
		return true;
	}
	
}








