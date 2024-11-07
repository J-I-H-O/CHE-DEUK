package spring_jpa.multiple_collection_join.domain.bagType;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class BagTypeMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private BagTypeClub club;

    public BagTypeMember(BagTypeClub club) {
        this.club = club;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setClub(BagTypeClub club) {
        this.club = club;
    }
}
