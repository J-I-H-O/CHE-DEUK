package spring_jpa.multiple_collection_join.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import spring_jpa.multiple_collection_join.domain.bagType.BagTypeClub;

public interface BagTypeClubRepository extends JpaRepository<BagTypeClub, Long> {

    @Query("""
                SELECT C
                FROM BagTypeClub C
                JOIN FETCH C.members
                JOIN FETCH C.pets
            """)
    List<BagTypeClub> findAllJoiningMultipleCollectionFields();

    @Query("""
                SELECT C
                FROM BagTypeClub C
                JOIN FETCH C.members
            """)
    List<BagTypeClub> findAllJoiningMember();
}
