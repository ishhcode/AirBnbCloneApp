package com.learning.AirBnb.Project.entities;

import com.learning.AirBnb.Project.entities.Enum.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "app_user_id", nullable = false)
    private User user;

    private String name;

    @Enumerated(value= EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    private Integer age;

    @CreationTimestamp
    private LocalDateTime createdAt;

//    @ManyToMany(mappedBy = "guests")
//    private Set<Booking> bookings;

}
