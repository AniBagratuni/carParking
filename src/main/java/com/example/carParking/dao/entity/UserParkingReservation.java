package com.example.carParking.dao.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
public class UserParkingReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @NonNull
    private Long userId;

    @OneToOne
    @NonNull
    private ParkingSpot parkingSpot;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime bookStartTime;

   //user can mention end time
    private LocalDateTime endTime;

    public UserParkingReservation () {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserParkingReservation that = (UserParkingReservation) o;
        return Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
