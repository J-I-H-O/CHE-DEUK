package spring_jpa.multiple_collection_join.domain.bagType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

// https://www.inflearn.com/community/questions/1073126/jpql-%EC%97%94%ED%8B%B0%ED%8B%B0-%EC%9D%B8%EC%A7%80-%EC%98%A4%EB%A5%98?srsltid=AfmBOoqYNJa1EGL0yrW-qDnTmCyuDSV3Xt_GNGzsAZfy_2OnP-F0kqvT
@Entity
@NoArgsConstructor
@Getter
public class BagTypeClub {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "club")
    private List<BagTypeMember> members = new ArrayList<>();

    @OneToMany(mappedBy = "club")
    private List<BagTypePet> pets = new ArrayList<>();

    public BagTypeClub(List<BagTypeMember> members, List<BagTypePet> pets) {
        this.members = members;
        this.pets = pets;
    }

    public void addMember(BagTypeMember member) {
        member.setClub(this);
        members.add(member);
    }
}
