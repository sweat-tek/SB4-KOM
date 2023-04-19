import dk.sdu.mmmi.cbse.collisionsystem.CollisionDetection;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

module Collision {
    requires Common;
    provides IPostEntityProcessingService with CollisionDetection;
}