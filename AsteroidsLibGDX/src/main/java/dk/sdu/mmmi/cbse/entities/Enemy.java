package dk.sdu.mmmi.cbse.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import dk.sdu.mmmi.cbse.main.Game;

import java.util.ArrayList;

public class Enemy extends SpaceObject{
    private ArrayList<Bullet> enemyBullets;
    private int type;
    public static final int LARGE = 0;
    public static final int SMALL = 1;
    //private int score;
    private float fireTimer;
    private float fireTime;
    private Player player;
    private float pathTimer;
    private float pathTime1;
    private float pathTime2;
    private int direction;
    public static final int left = 0;
    public static final int right = 1;

    private boolean remove;

    public Enemy(int type, int direction, Player player, ArrayList<Bullet> enemyBullets){
        this.type = type;
        this.direction = direction;
        this.player = player;
        this.enemyBullets = enemyBullets;

        speed = 70;
        if(direction == left){
            dx = -speed;
            x = Game.WIDTH;
        }
        else if (direction == right){
            dx = speed;
            x = 0;
        }
        y = MathUtils.random(Game.HEIGHT);

        shapex = new float[6];
        shapey = new float[6];
        setShape();

        /*if (type == LARGE){
            score = 200;
        }
        else if(type == SMALL){
            score = 1000;
        }*/

        fireTimer = 0;
        fireTime = 1;

        pathTimer = 0;
        pathTime1 = 2;
        pathTime2 = pathTime1 + 2;
    }

    private void setShape(){
        if (type == LARGE) {
            shapex[0] = x - 10;
            shapey[0] = y;
            shapex[1] = x - 3;
            shapey[1] = y - 5;
            shapex[2] = x + 3;
            shapey[2] = y - 5;
            shapex[3] = x + 10;
            shapey[3] = y;
            shapex[4] = x + 3;
            shapey[4] = y + 5;
            shapex[5] = x - 3;
            shapey[5] = y + 5;
        }
        else if (type == SMALL){
            shapex[0] = x - 6;
            shapey[0] = y;
            shapex[1] = x - 2;
            shapey[1] = y - 3;
            shapex[2] = x + 2;
            shapey[2] = y - 3;
            shapex[3] = x + 6;
            shapey[3] = y;
            shapex[4] = x + 2;
            shapey[4] = y + 3;
            shapex[5] = x - 2;
            shapey[5] = y + 3;
        }

    }

    //public int getScore(){return score;}

    public boolean shouldRemove(){return remove;}
    public void update(float dt){

        //fire
        fireTimer += dt;
        if (fireTimer > fireTime){
            fireTimer = 0;
            if (type == LARGE || type == SMALL){
                radians = MathUtils.random(2 * 3.1415f);
            }

        }
        enemyBullets.add(new Bullet(x,y,radians));

        //move along path
        pathTimer += dt;
        if(pathTimer < pathTime1){
            dy = 0;
        }
        if (pathTimer > pathTime1 && pathTimer < pathTime2){
            dy = -speed;
        }
        if (pathTimer > pathTime1 + pathTime2){
            dy = 0;
        }

        x += dx * dt;
        y += dy * dt;

        //screen wrap
        if (y < 0){ y = Game.HEIGHT;}

        setShape();

        //check if remove
        if ((direction == right && x > Game.WIDTH) || (direction == left && x<0)){
            remove = true;
        }
    }
    public void draw(ShapeRenderer sr){
        sr.setColor(1,1,1,1);
        sr.begin(ShapeRenderer.ShapeType.Line);
        for (int i = 0, j = shapex.length - 1;
             i < shapex.length;
             j = i++) {

            sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);

        }
        sr.line(shapex[0], shapey[0], shapex[3], shapey[3]);
        sr.end();

    }
}
