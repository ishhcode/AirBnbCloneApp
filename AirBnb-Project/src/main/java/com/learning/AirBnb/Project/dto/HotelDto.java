package com.learning.AirBnb.Project.dto;

import com.learning.AirBnb.Project.entities.hotelContactInfo;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
public class HotelDto {
    private Long id;

    private String name;

    private hotelContactInfo contactInfo;

    private String city;

    private String[] photos;

    private String[] amenities;

    private Boolean active;
}
