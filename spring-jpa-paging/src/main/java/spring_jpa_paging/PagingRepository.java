package spring_jpa_paging;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagingRepository extends JpaRepository<Member, Long> {

    Page<Member> findPageBy(Pageable pageable);
    Slice<Member> findSliceBy(Pageable pageable);
}
