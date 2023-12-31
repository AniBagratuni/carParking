package com.example.carParking.dao.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Objects;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
public class Resident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false,columnDefinition = "boolean default false")
    private Boolean isFree = false;

    private double pricePerHour;

    @Column(nullable = false, columnDefinition = "double(10,2) default 0.00")
    private double minPrice;

    public Resident () {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resident that = (Resident) o;
        return Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
