package dk.sdu.mmmi.cbse.common.data;

import com.badlogic.gdx.graphics.Color;
import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Entity implements Serializable {
    private final UUID ID = UUID.randomUUID();

    private float[] shapeX = new float[4];
    private float[] shapeY = new float[4];
    private float hitBoxRadius = 1;
    private float sqrtHitBoxRadius = (float) Math.sqrt(hitBoxRadius);
    private float size = 1;

    private boolean bCollidable = true;

    private Color color = Color.PINK;
    private Map<Class, EntityPart> parts;
    
    public Entity() {
        parts = new ConcurrentHashMap<>();
    }
    
    public void add(EntityPart part) {
        parts.put(part.getClass(), part);
    }
    
    public void remove(Class partClass) {
        parts.remove(partClass);
    }
    
    public <E extends EntityPart> E getPart(Class partClass) {
        return (E) parts.get(partClass);
    }
    
    public void setHitBoxRadius(float r){
        this.hitBoxRadius = r;
        this.sqrtHitBoxRadius = (float) Math.sqrt(r);
    }
    
    public float getHitBoxRadius(){
        return hitBoxRadius;
    }

    public String getID() {
        return ID.toString();
    }

    public float[] getShapeX() {
        return shapeX;
    }

    public void setShapeX(float[] shapeX) {
        this.shapeX = shapeX;
    }

    public float[] getShapeY() {
        return shapeY;
    }

    public void setShapeY(float[] shapeY) {
        this.shapeY = shapeY;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isbCollidable() {
        return bCollidable;
    }

    public void setbCollidable(boolean bCollidable) {
        this.bCollidable = bCollidable;
    }

    public float getSqrtHitBoxRadius() {
        return sqrtHitBoxRadius;
    }
}
