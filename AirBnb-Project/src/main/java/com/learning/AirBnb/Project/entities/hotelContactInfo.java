package com.learning.AirBnb.Project.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class hotelContactInfo {

    private String completeAddress;

    private String location;

    private String phoneNumber;
    @Email
    private String email;
}
//reusable can be used in some other entity also