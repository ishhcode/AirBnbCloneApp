package com.learning.AirBnb.Project.repositories;

import com.learning.AirBnb.Project.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
