package com.oozeander.courses_manager_api.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "participant")
@NoArgsConstructor
@AllArgsConstructor
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    int number;

    String name;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_race", referencedColumnName = "id")
    @ToString.Exclude
    Race race;

    public void addRace(Race race) {
        this.setRace(race);
        race.getParticipants().add(this);
    }
}
