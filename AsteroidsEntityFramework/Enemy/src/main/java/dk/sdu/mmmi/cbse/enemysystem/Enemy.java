package dk.sdu.mmmi.cbse.enemysystem;

import com.badlogic.gdx.graphics.Color;
import dk.sdu.mmmi.cbse.common.data.Entity;

/**
 *
 * @author corfixen
 */
public class Enemy extends Entity {
	public Enemy(){
		super.setColor(Color.RED);
		super.setSize(2);
	}
}
