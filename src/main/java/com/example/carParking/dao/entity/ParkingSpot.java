package com.example.carParking.dao.entity;

import com.example.carParking.dao.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Objects;

@Entity
@Getter
@Setter
@Accessors(chain = true)
public class ParkingSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NonNull
    private String spotNumber;

    @Column(length = 32, columnDefinition = "varchar(32) default 'FREE'", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status = Status.FREE;

    @ManyToOne
    private Resident resident;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingSpot that = (ParkingSpot) o;
        return Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}

