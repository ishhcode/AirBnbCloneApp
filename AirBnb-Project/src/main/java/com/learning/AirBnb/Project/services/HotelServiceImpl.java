package com.learning.AirBnb.Project.services;

import com.learning.AirBnb.Project.dto.HotelDto;
import com.learning.AirBnb.Project.entities.Hotel;
import com.learning.AirBnb.Project.entities.Room;
import com.learning.AirBnb.Project.exceptions.ResourceNotFoundException;
import com.learning.AirBnb.Project.repositories.HotelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class HotelServiceImpl implements HotelService{
    private final HotelRepository hotelRepository;  //constructor dependency injection
    private final ModelMapper modelMapper;
    private final InventoryService inventoryService;

    @Override
    public HotelDto createNewHotel(HotelDto hotelDto) {
        log.info("creating a new hotel with name: {}", hotelDto.getName());
        Hotel hotel =  modelMapper.map(hotelDto, Hotel.class);
        hotel.setActive(false);
        log.info("created a new hotel with id: {}", hotel.getId());
        return modelMapper.map(hotel, HotelDto.class);

    }

    @Override
    public HotelDto getHotelById(Long id) {
        log.info("getting hotel info with id: {}", id);
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel with matching Id does not exists!"));
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public HotelDto updateHotelById(Long id, HotelDto hotelDto) {
        log.info("updating hotel info with id: {}", id);
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel with matching Id does not exists!"));

        modelMapper.map(hotelDto, hotel);
        hotel.setId(id);
        hotel = hotelRepository.save(hotel);
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    @Transactional
    public void DeleteHotelById(Long id) {
        log.info("deleting hotel info with id: {}", id);
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel with matching Id does not exists!"));

        hotelRepository.deleteById(id);
        //TODO: delete the future inventory of the hotel
        for(Room room: hotel.getRooms()){
            inventoryService.deleteFutureInventories(room);
        }
    }

    @Override
    @Transactional
    public void activateHotel(Long Id) {
        log.info("Activating hotel info with id: {}", Id);
        Hotel hotel = hotelRepository
                .findById(Id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel with matching Id does not exists!"));

        hotel.setActive(true);
        //TODO: create the inventory for all rooms of this hotel
        for(Room room: hotel.getRooms()){
            inventoryService.initializeInventoryWithRoom(room);
        }
    }


}
