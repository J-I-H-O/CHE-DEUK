package batch_size.repsoitory;

import batch_size.domain.BatchSizeClub;
import batch_size.domain.BatchSizeMember;
import batch_size.domain.Club;
import batch_size.domain.Member;
import batch_size.repository.BatchSizeClubRepository;
import batch_size.repository.ClubRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ClubRepositoryTest {

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private BatchSizeClubRepository batchSizeClubRepository;

    @Autowired
    private EntityManager em;

    @DisplayName("자식 엔티티 조회 시 처음 조회된 부모 엔티티의 개수만큼 추가적인 쿼리가 발생한다.")
    @Test
    void nPlusOne() {
        Club club1 = new Club();
        Club club2 = new Club();

        Member member1 = new Member();
        Member member2 = new Member();
        club1.addMember(member1);
        club2.addMember(member2);

        em.persist(club1);
        em.persist(club2);
        em.flush();
        em.clear();

        clubRepository.findAll();

        /**
         * 2개의 club 조회
         * Hibernate:
         *     select
         *         c1_0.id
         *     from
         *         club c1_0
         */

        /**
         * 첫 번째 club에 대한 member 조회
         * Hibernate:
         *     select
         *         m1_0.club_id,
         *         m1_0.id,
         *         m1_0.name
         *     from
         *         member m1_0
         *     where
         *         m1_0.club_id=?
         */

        /**
         * 두 번째 club에 대한 member 조회
         * Hibernate:
         *     select
         *         m1_0.club_id,
         *         m1_0.id,
         *         m1_0.name
         *     from
         *         member m1_0
         *     where
         *         m1_0.club_id=?
         */
    }

    @DisplayName("batch size 옵션을 설정하면 IN 쿼리로 연관된 엔티티를 한 번에 조회한다.")
    @Test
    void batchSize() {
        BatchSizeClub club1 = new BatchSizeClub();
        BatchSizeClub club2 = new BatchSizeClub();

        BatchSizeMember member1 = new BatchSizeMember();
        BatchSizeMember member2 = new BatchSizeMember();
        club1.addMember(member1);
        club2.addMember(member2);

        em.persist(club1);
        em.persist(club2);
        em.flush();
        em.clear();

        batchSizeClubRepository.findAll();
        /**
         * Hibernate:
         *     select
         *         bsc1_0.id
         *     from
         *         batch_size_club bsc1_0
         */

        /**
         * Hibernate:
         *     select
         *         m1_0.club_id,
         *         m1_0.id,
         *         m1_0.name
         *     from
         *         batch_size_member m1_0
         *     where
         *         m1_0.club_id in (?, ?, ?, ?...) // 100개
         */
    }
}
