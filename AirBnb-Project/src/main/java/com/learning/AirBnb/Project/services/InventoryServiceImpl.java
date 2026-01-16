package com.learning.AirBnb.Project.services;

import com.learning.AirBnb.Project.entities.Inventory;
import com.learning.AirBnb.Project.entities.Room;
import com.learning.AirBnb.Project.repositories.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService{
    private final InventoryRepository inventoryRepository;

    @Override
    public void initializeInventoryWithRoom(Room room) {
        log.info("creating inventory of a room for a year");
        LocalDate today = LocalDate.now();
        LocalDate lastDate = today.plusYears(1);
        for(;!today.isAfter(lastDate);today=today.plusDays(1)){
            Inventory inventory = Inventory.builder()
                    .date(today)
                    .hotel(room.getHotel())
                    .room(room)
                    .price(room.getBasePrice())
                    .city(room.getHotel().getCity())
                    .closed(false)
                    .bookedCount(0)
                    .totalCount(room.getTotalCount())
                    .surgeFactor(BigDecimal.ONE)
                    .build();

            inventoryRepository.save(inventory);
        }
    }

    @Override
    public void deleteFutureInventories(Room room) {
        LocalDate today = LocalDate.now();
        inventoryRepository.deleteByDateAfterAndRoom(today,room);
    }

}
