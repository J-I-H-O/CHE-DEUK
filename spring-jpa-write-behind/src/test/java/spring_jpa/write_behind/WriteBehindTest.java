package spring_jpa.write_behind;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import spring_jpa.write_behind.domain.IdentityPerson;
import spring_jpa.write_behind.domain.SequencePerson;

@DataJpaTest
public class WriteBehindTest {

    @Autowired
    private EntityManager em;

    @Test
    void IDENTITY_전략은_write_behind가_동작하지_않는다() {
        for (int i = 1; i <= 3; i++) {
            IdentityPerson person = new IdentityPerson();
            System.out.println("persist() 호출");
            em.persist(person); // 즉시 INSERT 실행됨
        }
        System.out.println("flush() 호출 직전");
        em.flush(); // INSERT 쿼리 실행
        em.clear();
    }

    @Test
    void SEQUENCE_전략은_write_behind가_동작한다() {
        for (int i = 1; i <= 3; i++) {
            SequencePerson person = new SequencePerson();
            System.out.println("persist() 호출");
            em.persist(person); // ID 미리 할당됨, INSERT 쿼리는 아직 실행 X
        }
        System.out.println("flush() 호출 직전");
        em.flush(); // INSERT 쿼리 실행
        em.clear();
    }
}
