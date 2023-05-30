package collisionsystem;

import common.data.Entity;
import common.data.entityparts.PositionPart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CollisionDetectorTest {

    //Arrange
    private Entity testEntity1;
    private Entity testEntity2;

    @BeforeEach
    void setUp(){
        //Arrange
        testEntity1 = new Entity();
        testEntity2 = new Entity();

        //Act
        testEntity1.setRadius(20);
        testEntity2.setRadius(20);
        testEntity1.add(new PositionPart(100,100,0));
    }

    @Test
    void entitiesColliding(){
        //Act
        testEntity2.add(new PositionPart(100,139,0));

        //Assert
        assertTrue(CollisionDetector.isColliding(testEntity1,testEntity2));
    }

    @Test
    void entitiesNotColliding(){
        //Act
        testEntity2.add(new PositionPart(100,140,0));

        //Assert
        assertFalse(CollisionDetector.isColliding(testEntity1,testEntity2));
    }

}