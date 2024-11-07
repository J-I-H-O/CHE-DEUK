### 하이버네이트 Wrappers

- 하이버네이트는 지연 로딩과 같은 추가 작업을 위해 자바 컬렉션 프레임워크를 래핑한 PersistentCollection을 제공한다.
    - https://docs.jboss.org/hibernate/orm/6.5/userguide/html_single/Hibernate_User_Guide.html#collection-wrapper
- **PersistentBag**
    - 순서를 보장하지 않고, 중복을 허용한다.
    - 자바의 List를 사용하면 하이버네이트는 PersistentBag을 사용한다.
    - 여러 Bag을 한 번에 fetch join 하는 경우, MultipleBagException이 발생한다.
- **PersistentList**
    - 순서를 보장하고, 중복을 허용한다.
    - 자바의 List와 함께 `@OrderColumn` 을 사용하면 하이버네이트는 PersistentList를 사용한다.
    - 여러 PersistentList를 한 번에 fetch join 하는 경우에는 MultipleBagException이 발생하지 않는다.

      > `@OrderColumn` : 테이블에 순서를 나타내는 컬럼을 추가한다.

- **PersistentSet**
    - 순서를 보장하지 않고, 중복을 허용하지 않는다.
    - 자바의 Set을 사용하면 하이버네이트는 PersistnetSet을 사용한다.
    - 여러 PersistentSet을 한 번에 fetch join 하는 경우에는 MultipleBagException이 발생하지 않는다.
    - Set을 사용할 때에는 equals(), hashCode()를 적절히 오버라이딩 해야 한다. 만약 개발자가 오버라이딩 하지 않는 경우, 하이버네이트는 자바의 기본 동등성 비교를 사용한다.

      > When using Sets, it’s very important to supply proper equals/hashCode implementations for child entities.
      >
      >
      > In the absence of a custom equals/hashCode implementation logic, Hibernate will use the default Java
      reference-based object equality which might render unexpected results when mixing detached and managed object
      instances.

### MultipleBagException

- https://docs.jboss.org/hibernate/orm/6.5/javadocs/org/hibernate/loader/MultipleBagFetchException.html
- 하나의 엔티티가 Bag으로 래핑되는 여러 개의 컬렉션 필드를 가지고 있을 때, 이들을 한 번에 fetch join 하면 해당 예외가 발생한다.
    - Bag은 순서를 보장하지 않고 중복을 허용하기 때문에, 어떤 컬럼을 엔티티로 매핑해야 할 지 알 수 없다.
    - 하이버네이트는 이처럼 데이터를 올바르게 매핑하지 못하는 이슈를 피하기 위해 위 예외를 발생시킨다.
    - 이때 fetch join 해야만 예외가 발생한다. inner join이나 outer join은 예외 발생하지 않는다.
- 해결 방법
    1. **OneToMany 컬렉션 필드의 자료형으로 Set 사용**
        1. Set은 중복을 허용하지 않기 때문에, Bag을 사용했을 때 발생하는 중복 데이터 조회 문제를 해결 가능하다.
        2. 이때 후술할 중복 row로 인한 성능 저하 문제가 있으므로, Set을 사용한다고 해서 모든 문제가 해결된 것은 아니다.

### 중복 row로 인한 성능 저하

- 컬렉션 join시 카테시안 곱으로 인한 중복 row가 발생한다.
- 이때 컬렉션 필드가 많아질 수록 자연스럽게 중복 row 수도 커지고, 수많은 row를 조회해야 하기 때문에 DB 성능 저하가 발생한다.
- 해결 방법
    1. **여러 번의 쿼리로 나누기**
        1. 각 컬렉션을 한 번에 fetch join으로 조회하지 말고, 여러 번에 걸쳐 나누어 실행함으로써 회피할 수 있다.
        2. 이때 default_batch_fetch_size 설정을 사용하면 IN 쿼리로 컬렉션 조회를 최적화 할 수 있다.
