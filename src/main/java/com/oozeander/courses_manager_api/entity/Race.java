package com.oozeander.courses_manager_api.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "race")
@NoArgsConstructor
@AllArgsConstructor
public class Race {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    LocalDate date;

    int number;

    String name;

    @OneToMany(mappedBy = "race", cascade = CascadeType.PERSIST)
    @Builder.Default
    @ToString.Exclude
    Set<Participant> participants = new HashSet<>();
}
