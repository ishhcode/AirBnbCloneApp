package com.learning.AirBnb.Project.services;

import com.learning.AirBnb.Project.entities.Room;

public interface InventoryService {

    void initializeInventoryWithRoom(Room room);

    void deleteFutureInventories(Room room);
}
