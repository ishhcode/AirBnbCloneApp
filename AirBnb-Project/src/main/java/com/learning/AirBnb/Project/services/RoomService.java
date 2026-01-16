package com.learning.AirBnb.Project.services;

import com.learning.AirBnb.Project.dto.RoomDto;
import java.util.List;


public interface RoomService {

    public RoomDto createNewRoom(Long hotelId, RoomDto roomDto);

    public List<RoomDto> getAllRoomsByHotelId(Long hotelId);

    public RoomDto getRoomById(Long roomId);

    public void DeleteRoomById(Long roomId);

}
