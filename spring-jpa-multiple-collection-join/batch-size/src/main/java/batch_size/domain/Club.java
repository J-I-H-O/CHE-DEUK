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

@Entity
@NoArgsConstructor
@Getter
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "club", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Member> members = new ArrayList<>();

    public Club(List<Member> members) {
        this.members = members;
    }

    public void addMember(Member member) {
        member.setClub(this);
        members.add(member);
    }
}
