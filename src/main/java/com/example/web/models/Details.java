package com.example.web.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Details {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String realtySupType, balcony, bathroom;

    private Integer rooms, floor;

    @Column( unique = true)
    private Float square;

    private Float livingSpace, kitchenSpace;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "immovables_id")
    private Immovables immovables;

    @Override
    public String toString() {
        return "Details{" +
                "id=" + id +
                ", realtySupType='" + realtySupType + '\'' +
                ", rooms=" + rooms +
                ", floor=" + floor +
                ", square=" + square +
                ", livingSpace=" + livingSpace +
                ", kitchenSpace=" + kitchenSpace +
                ", balcony=" + balcony +
                ", bathroom=" + bathroom +
                ", immovables=" + immovables.getId() +
                '}';
    }
}
