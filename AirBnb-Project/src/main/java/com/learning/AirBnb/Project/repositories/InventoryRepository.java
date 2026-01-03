package com.learning.AirBnb.Project.repositories;

import com.learning.AirBnb.Project.entities.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
