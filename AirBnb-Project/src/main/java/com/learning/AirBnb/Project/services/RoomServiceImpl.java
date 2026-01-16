package com.learning.AirBnb.Project.services;

import com.learning.AirBnb.Project.dto.RoomDto;
import com.learning.AirBnb.Project.entities.Hotel;
import com.learning.AirBnb.Project.entities.Room;
import com.learning.AirBnb.Project.exceptions.ResourceNotFoundException;
import com.learning.AirBnb.Project.repositories.HotelRepository;
import com.learning.AirBnb.Project.repositories.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomServiceImpl implements RoomService{
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;
    private final InventoryService inventoryService;

    @Override
    public RoomDto createNewRoom(Long hotelId, RoomDto roomDto) {
        log.info("creating a new room in the hotel with id:{0}", hotelId);
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel with matching Id does not exists!"));
        Room room = modelMapper.map(roomDto, Room.class);
        room.setHotel(hotel);
        room = roomRepository.save(room);
        if(hotel.getActive()){
            inventoryService.initializeInventoryWithRoom(room);
        }
        return modelMapper.map(room, RoomDto.class);
    }

    @Override
    public List<RoomDto> getAllRoomsByHotelId(Long hotelId) {
        log.info("geeting list of all rooms in the hotel with id:{0}", hotelId);
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel with matching Id does not exists!"));
        return hotel.getRooms()
                .stream()
                .map((e) ->modelMapper.map(e, RoomDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public RoomDto getRoomById(Long roomId) {
        log.info("geeting a room with id:{0}", roomId);
        Room room = roomRepository
                .findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("room with matching Id does not exists!"));
        return modelMapper.map(room, RoomDto.class);
    }

    @Override
    public void  DeleteRoomById(Long roomId) {
        log.info("deleting a room with id:{0}", roomId);
        Room room = roomRepository
                .findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("room with matching Id does not exists!"));
        roomRepository.deleteById(roomId);
        //TODO: delete all future inventories for this room
        inventoryService.deleteFutureInventories(room);
    }
}
