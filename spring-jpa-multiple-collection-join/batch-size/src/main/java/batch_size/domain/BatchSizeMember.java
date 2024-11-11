package batch_size.domain;

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
public class BatchSizeMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private BatchSizeClub club;

    public BatchSizeMember(BatchSizeClub club) {
        this.club = club;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setClub(BatchSizeClub club) {
        this.club = club;
    }
}
