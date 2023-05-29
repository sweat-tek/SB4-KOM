import collisionsystem.CollisionDetector;
import common.services.IPostEntityProcessingService;

module Collision {
    requires Common;

    provides IPostEntityProcessingService with CollisionDetector;
}