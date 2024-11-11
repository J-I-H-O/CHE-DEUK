package batch_size.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

@Entity
@NoArgsConstructor
@Getter
public class BatchSizeClub {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // application.yml 설정으로 부여할 수도 있지만, 아래와 같이 어노테이션을 통해 클래스나 필드 단위로도 설정할 수도 있음
    @BatchSize(size = 10)
    @OneToMany(mappedBy = "club", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<BatchSizeMember> members = new ArrayList<>();

    public void addMember(BatchSizeMember member) {
        member.setClub(this);
        members.add(member);
    }
}
