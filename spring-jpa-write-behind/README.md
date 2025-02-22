# JPA 기본 키 할당 전략

## 직접 할당 방식

- `@Id` 어노테이션만을 적용한 상태로, 코드 상에서 직접 기본 키를 할당해주는 방식

    ```java
    member.setId(1L);
    EntityManager.persist(member);
    ```


## 자동 할당 방식

### IDENTITY

- 기본 키 생성을 DB에 위임
- `em.persist()` 호출 시점에 1차 캐시에 엔티티를 등록하기 위해서는 키로 사용할 PK가 필요함. IDENTITY 전략은 DB를 통해 PK를 얻어와야 하므로, `em.persist()` 호출 시점에 즉시 INSERT 쿼리를 실행하고 반환받은 식별자 값을 사용함
    - 즉, I**DENTITY 전략을 사용하면 영속성 컨텍스트의 쓰기지연(Transaction Write-Behind) 기능을 사용할 수 없음**
    - 기본적으로 **Batch Insert 불가능**
- 추가로 궁금한 것 : MySQL의 auto_increment 작동 원리

### SEQUENCE

- DB 시퀀스 객체를 사용해 기본 키 생성

  > **시퀀스(Sequence):** 자동으로 순차적으로 증가하는 순번을 반환하는 데이터베이스 객체. Oracle, PostgreSQL, DB2, H2 등에서 사용 가능 (기본적으로 MySQL은 지원 x)
  `nextval` ****을 통해 다음에 생성할 식별자를 얻을 수 있음

- **엔티티 저장 전에 미리 PK 값을 가져오는 방식**으로 동작. `em.persist()` 를 호출할 때 DB 시퀀스 객체를 사용해 식별자를 가져오고, 조회한 식별자를 엔티티에 할당한 후 엔티티를 영속성 컨텍스트에 저장함
- 이후 트랜잭션이 커밋되는 시점에 플러시가 일어나며 실제 INSERT 쿼리가 실행됨
- 즉, **영속성 컨텍스트에 엔티티를 등록하는 시점에 DB에 INSERT 쿼리를 실행하지 않아도 되므로 Batch Insert가 가능**함
- **DB의 시퀀스 객체에 접근해 ID를 조회하는 추가적인 동작(`nextval`)이 필요하므로, DB와 통신 과정이 추가**된다는 단점 존재. **이때 allocationSize 값을 조절해 DB 접근을 최소화**할 수 있음
    - allocationSize 값이 50이라면, 시퀀스를 한 번에 50 증가시킨뒤 1~50까지의 값을 메모리에 올려두었다가 식별자로 할당
    - 51번째가 되었을 때, 다시 한 번 시퀀스를 한 번에 50 증가시킨 뒤 51~100까지의 값을 메모리에 올려두었다가 식별자로 할당
- 예제

    ```java
    @Entity
    @SequenceGenerator(
        name = "member_seq_generator", // 시퀀스 생성기 이름
        sequenceName = "member_seq", // 실제 DB 시퀀스 이름
        initialValue = 1, // 시퀀스의 시작 값
        allocationSize = 50 // 미리 가져올 nextval()의 개수
    )
    public class Member {
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq_generator")
        private Long id;
    }
    ```

    - `@SequenceGenerator` : 시퀀스 생성기를 등록할 때 사용
    - `name` : 시퀀스 객체와 매핑될 시퀀스 생성기의 이름
    - `sequenceName` : 실제 DB의 시퀀스의 이름. JPA는 해당 이름을 사용해 시퀀스 생성기와 실제 DB의 시퀀스 객체를 매핑함
    - `allocationSize` : 한 번에 가져올 `nextval` 의 개수

**[IDENTITY vs SEQUENCE 전략의 write-behind 동작 비교]**

- **IDENTITY**

    ```java
    @Entity
    public class IdentityPerson {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
    }
    
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
    ```

    ```java
    persist() 호출
    insert into "jpa_test$identity_person" (id) values (default)
    
    persist() 호출
    insert into "jpa_test$identity_person" (id) values (default)
    
    persist() 호출
    insert into "jpa_test$identity_person" (id) values (default)
    
    flush() 호출 직전
    ```

    - persist()가 호출될 때마다 즉시 INSERT 쿼리를 실행하는 것을 확인할 수 있음
- **SEQUENCE**

    ```java
    @Entity
    @SequenceGenerator(
        name = "person_sequence_generator",
        sequenceName = "person_sequence",
        initialValue = 1,
        allocationSize = 50
    )
    public class SequencePerson {
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_sequence_generator")
        private Long id;
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
    ```

    ```java
    create sequence person_sequence start with 1 increment by 50
    
    persist() 호출
    select next value for person_sequence
    persist() 호출
    select next value for person_sequence // TODO: 이거 왜 두 번 호출되는지 찾아보기
    persist() 호출
    
    flush() 호출 직전
    insert into "jpa_test$identity_person" (id) values (default)
    insert into "jpa_test$identity_person" (id) values (default)
    insert into "jpa_test$identity_person" (id) values (default)
    ```

    - 엔티티를 저장하기 전에 미리 시퀀스 객체를 사용해 식별자를 얻어오는 것을 확인할 수 있음
    - 이때 엔티티를 영속화 시킬 때마다 매번 nextval을 호출하는 경우, DB 부하가 걸려 성능이 저하될 수 있음.
      최적화를 위해 allocationSize를 설정해주면 한 번에 allocationSize 만큼의 nextval을 얻어올 수 있음. 이렇게 얻어온 식별자는 하이버네이트가 메모리에 유지해두었다가 두고두고 사용
    - 더 찾아볼 것: 만약 트랜잭션이 롤백되거나, 애플리케이션이 비정상적으로 종료되어 할당받은 식별자들을 실제로 엔티티의 ID로 사용하지는 못했다면 어떻게 되는지?
        - GPT 피셜: 하이버네이트가 미리 가져온 ID는 재사용되지 않음. 즉 미리 할당해두었으나 사용되지 못한 ID는 손실됨. 즉, 한 트랜잭션에서 일부 엔티티가 저장되지 않고 롤백되면 **그 ID들은 영원히 건너뛰게 됨**.
        - 오류로 엔티티들이 저장되지 않았더라도 이미 DB의 시퀀스 객체의 nextval은 증가한 상태이기 때문이라고 이해함

### TABLE

- 키 생성 전용 테이블을 두어 기본 키를 생성하는 방식. DB 시퀀스 객체 대신 테이블을 사용
- 식별자 할당 시 키 생성 전용 테이블을 조회하고, 그 값을 증가시키는 동작을 수행해야 함
- 즉 새로운 엔티티가 `persist()` 될 때마다
    1. 키 생성 전용 테이블에서 현재 ID 값을 읽어오고(SELECT),
    2. ID를 증가시켜 다시 저장하는(UPDATE) 두 개의 쿼리를 수행해야 함
- **매번 SELECT, UPDATE 두 번의 쿼리가 추가로 실행**되므로 IDENTITY나 SEQUENCE 방식에 비해 비효율적
- **UPDATE로 인해 락이 발생**하므로, 트랜잭션이 많아직수록 동시성 이슈가 발생할 수 있음
- 위와 같은 특징으로 인해 잘 사용되지 않음

### AUTO

- DB 방언의 종류에 따라 하이버네이트가 자동으로 전략을 선택하도록 위임
- DB 버전에 따라 기본으로 사용되는 전략이 달라질 수 있으므로 되도록 AUTO 대신 직접 지정해주는 것을 권장
