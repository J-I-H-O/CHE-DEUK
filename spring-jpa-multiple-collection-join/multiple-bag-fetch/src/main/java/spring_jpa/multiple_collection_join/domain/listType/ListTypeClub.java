package spring_jpa.multiple_collection_join.domain.listType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderColumn;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

// https://www.inflearn.com/community/questions/1073126/jpql-%EC%97%94%ED%8B%B0%ED%8B%B0-%EC%9D%B8%EC%A7%80-%EC%98%A4%EB%A5%98?srsltid=AfmBOoqYNJa1EGL0yrW-qDnTmCyuDSV3Xt_GNGzsAZfy_2OnP-F0kqvT
@Entity
@NoArgsConstructor
@Getter
public class ListTypeClub {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "club")
    @OrderColumn(name = "order_column")
    private List<ListTypeMember> members = new ArrayList<>();

    @OneToMany(mappedBy = "club")
    @OrderColumn(name = "order_column")
    private List<ListTypePet> pets = new ArrayList<>();

    public ListTypeClub(List<ListTypeMember> members, List<ListTypePet> pets) {
        this.members = members;
        this.pets = pets;
    }

    public void addMember(ListTypeMember member) {
        member.setClub(this);
        members.add(member);
    }
}
