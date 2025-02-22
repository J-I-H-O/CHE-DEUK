package spring_jpa.write_behind.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(
        name = "person_sequence_generator",
        sequenceName = "person_sequence",
        initialValue = 1,
        allocationSize = 50
)
public class SequencePerson {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_sequence_generator")
    private Long id;
}
