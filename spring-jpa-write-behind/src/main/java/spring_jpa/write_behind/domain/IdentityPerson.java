package spring_jpa.write_behind.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class IdentityPerson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
