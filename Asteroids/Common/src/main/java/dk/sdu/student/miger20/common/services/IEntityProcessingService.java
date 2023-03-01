package dk.sdu.student.miger20.common.services;

import dk.sdu.student.miger20.common.data.GameData;
import dk.sdu.student.miger20.common.data.World;

public interface IEntityProcessingService {

    void process(GameData gameData, World world);
}
