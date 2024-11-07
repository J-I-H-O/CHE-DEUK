package spring_jpa.multiple_collection_join.domain.setType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class SetTypeClub {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "club")
    private Set<SetTypeMember> members = new HashSet<>();

    @OneToMany(mappedBy = "club")
    private Set<SetTypePet> pets = new HashSet<>();
}
