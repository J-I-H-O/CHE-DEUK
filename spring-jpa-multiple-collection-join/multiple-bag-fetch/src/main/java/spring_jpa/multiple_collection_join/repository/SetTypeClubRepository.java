package spring_jpa.multiple_collection_join.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import spring_jpa.multiple_collection_join.domain.setType.SetTypeClub;

public interface SetTypeClubRepository extends JpaRepository<SetTypeClub, Long> {

    @Query("""
                SELECT C
                FROM SetTypeClub C
                JOIN FETCH C.members
                JOIN FETCH C.pets
            """)
    List<SetTypeClub> findAllJoiningMultipleCollectionFields();
}
