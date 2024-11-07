package spring_jpa.multiple_collection_join.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import jakarta.persistence.EntityManager;
import org.hibernate.collection.spi.PersistentBag;
import org.hibernate.collection.spi.PersistentList;
import org.hibernate.collection.spi.PersistentSet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import spring_jpa.multiple_collection_join.domain.bagType.BagTypeClub;
import spring_jpa.multiple_collection_join.domain.listType.ListTypeClub;
import spring_jpa.multiple_collection_join.domain.setType.SetTypeClub;

@DataJpaTest
public class ClubRepositoryTest {

    @Autowired
    private BagTypeClubRepository bagTypeClubRepository;

    @Autowired
    private SetTypeClubRepository setTypeClubRepository;

    @Autowired
    private ListTypeClubRepository listTypeClubRepository;

    @Autowired
    private EntityManager em;

    /**
     * 엔티티를 영속화할 때 컬렉션 필드를 하이버네이트가 제공하는 자료형으로 래핑함. 지연 로딩과 같은 추가 작업을 위함.
     */
    @DisplayName("하이버네이트는 자바의 List 자료형을 PersistentBag으로 래핑한다.")
    @Test
    void bagTypeProxy() {
        BagTypeClub bagTypeClub = new BagTypeClub();
        em.persist(bagTypeClub);

        assertThat(bagTypeClub.getMembers()).isExactlyInstanceOf(PersistentBag.class);
    }

    @DisplayName("자바의 List 자료형과 @OrderColumn을 함께 사용하면 PersistentList로 래핑한다.")
    @Test
    void listTypeProxy() {
        ListTypeClub listTypeClub = new ListTypeClub();
        em.persist(listTypeClub);

        assertThat(listTypeClub.getMembers()).isExactlyInstanceOf(PersistentList.class);
    }

    @DisplayName("하이버네이트는 자바의 Set 자료형을 PersistentSet으로 래핑한다.")
    @Test
    void setTypeProxy() {
        SetTypeClub setTypeClub = new SetTypeClub();
        em.persist(setTypeClub);

        assertThat(setTypeClub.getMembers()).isExactlyInstanceOf(PersistentSet.class);
    }

    /**
     * MultipleBagFetchException: Exception used to indicate that a query is attempting to simultaneously fetch multiple bags 동시에
     * 여러 개의 Bag을 동시에 fetch 하려고 시도한 경우 발생. (inner join이나 outer join 시에는 발생 X)
     * <p>
     * Bag: 하이버네이트가 제공하는 자료형. 자바 컬렉션 프레임워크의 List가 하이버네이트의 Bag에 대응됨. (중복 허용, 순서 X)
     */
    @DisplayName("여러 개의 PersistentBag fetch join시 MultipleBagFetchException이 발생한다.")
    @Test
    void multipleBagFetch() {
        assertThatThrownBy(() -> bagTypeClubRepository.findAllJoiningMultipleCollectionFields())
                .isExactlyInstanceOf(InvalidDataAccessApiUsageException.class)
                .hasMessageContaining("MultipleBagFetchException: cannot simultaneously fetch multiple bags");
    }

    @DisplayName("여러 개의 PersistentList fetch join시 MultipleBagFetchException이 발생하지 않는다.")
    @Test
    void multipleListJoin() {
        assertThatCode(() -> listTypeClubRepository.findAllJoiningMultipleCollectionFields())
                .doesNotThrowAnyException();
    }

    /**
     * PersistentSet 사용 시 MultipleBackFetchException이 발생하지 않는다.
     */
    @DisplayName("여러 개의 PersistentSet fetch join시 MultipleBagFetchException이 발생하지 않는다.")
    @Test
    void multipleSetFetchJoin() {
        assertThatCode(() -> setTypeClubRepository.findAllJoiningMultipleCollectionFields())
                .doesNotThrowAnyException();
    }
}
