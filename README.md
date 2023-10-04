# JPA 사용법

## 엔티티

엔티티를 명시하는 어노테이션과 해당 엔티티가 사용하는 테이블을 명시하는 어노테이션을 작성한다.

- ### @Entity(name="EntityName")
- ### @Table(name="TABLE_NAME")

만약 시퀸스를 사용한다면 시퀸스 어노테이션을 작성한다.

시퀸스는 클레스에 작성하는 @SequenceGenerator와 시퀸스를 사용할 필드에 작성하는 @GeneratedValue가 쌍으로 작성되어야 한다.

- ### @SequenceGenerator : 시퀸스를 생성한다.
    - name : SequenceGenerator의 이름
    - sequenceName : 실제 생성 될 시퀸스 이름
    - initialValue : 시작 값
    - allocationSize : 기본값은 50
        - 시퀸스가 증가할 때마다 시퀸스를 호출하면 메모리에 부하가 걸리는지
        - JPA에서 현재 값과 다음 값을 기억하고 그 사이의 값을 시퀸스의 호출없이 자동으로 부여한다.

- ### @GeneratedValue : 생성된 값을 사용한다.
    - strategy : 사용할 값의 타입
        - IDENTITY :시본키 생성을 데이터베이스에 위임한다.
        - SEQUENCE :데이터베이스 시퀸스를 사용해서 기본 키를 할당한다.
        - TABLE : 키 생성 테이블을 사용한다.
    - generator : 사용할 값의 이름

### 엔티티 작성시 주의사항

1. 기본생성자는 필수
2. final 클래스,enum, interface, inner class는 엔티티로 사용하지 못한다.
3. 매핑할 필드에 final을 사용하지 못한다.

### 필드와 컬럼을 매핑하기위한 어노테이션

- ### @Id : 기본키 지정
- ### @Column : 컬럼을 매핑한다.
    - name : 컬럼명
        - ex) name = "MEMBER_ID"
    - length : 문자열 길이 (default는 255)
        - ex) length = 500 -> varchar2(500)
    - nullable = false : NOT NULL
    - unique = true : UNIQUE
    - columnDefinition : 제약조건 커스텀
        - ex) columnDefinition = "varchar2(200) default '010-0000-0000'"

- ### @Temporal : 날짜 타입을 매핑한다.
    - 반드시 java.util.Date 형식에서 사용할 것
    - DATE : 날짜만 반환
        - ex) @Temporal(TemploralType.DATE)
        - ->  2023-06-06
    - TIME : 시간만 반환
        - ex) @Temporal(TemploralType.TIME)
        - ->  1970-01-01 10:25:26
    - TIMESTAMP : 날짜와 시간 모두 반환
        - ex) @Temporal(TemploralType.TIMESTAMP)
        - ->  2023-06-06 10:25:26

- ### @Lob : BLOB, CLOB 타입을 패핑한다.
- ### @Transient : 매핑하지 않을 특정 필드에 작성한다.
- ### @Enumerated : Enum 타입을 매핑한다.
- ### @Access : JPA가 엔티티에 접근하는 방식을지정한다.

### 쿼리메소드 유형

    And                  ex) findByMenuCodeAndMenuName                   -> where x.menuCode =?1 and x.menuName =?2
    Or                   ex) findByMenuCodeOrMenuName                    -> where x.menuCode =?1 or x.menuName =?2
    Between              ex) findByMenuPriceBetween                      -> where x.menuPrice between ?1 and ?2
    LessThan             ex) findByMenuPriceLessThan                     -> where x.menuPrice < ?1
    LessThanEqual        ex) findByMenuPriceLessThanEqual                -> where x.menuPrice <= ?1
    GraterThan           ex) findByMenuPriceGraterThan                   -> where x.menuPrice > ?1
    GraterThanEqual      ex) findByMenuPriceGraterThanEqual              -> where x.menuPrice >= ?1
    After                ex) findByDateAfter                             -> where x.date > ?1
    Before               ex) findByDateBefore                            -> where x.date < ?1
    IsNUll               ex) findByMenuNameIsNull                        ->  where x.menuName is null
    IsNotNull, NotNull   ex) findByMenuName(Is)NotNull                   ->  where x.menuName is not null
    Like                 ex) findByMenuNameLike                          ->  where x.menuName like ?1
    NotLike              ex) findByMenuNameNotLike                       ->  where x.menuName not like ?1
    StaringWith          ex) findByMenuNameStaringWith                   ->  where x.menuName like ?1 || '%'
    EndingWith           ex) findByMenuNameEndingWith                    ->  where x.menuName like '%' || ?1
    Containing           ex) findByMenuNameContaining                    ->  where x.menuName like '%' || ?1 || '%'
    OrderBy              ex) findByPriceOrderByMenuCodeDesc              -> where x.menuPrice = ?1 order by x.menuCode desc
    Not                  ex) findByMenuNameNot                           -> where x.menuName <> ?1
    In                   ex) findByMenuNameIn(Collection<Name> names)    -> where x.menuName in (?1)

## 페이지 정보

    System.out.println("조회한 내용 목록 : " + menuList.getContent());
    System.out.println("총 페이지 수 : " + menuList.getTotalPages());
    System.out.println("총 메뉴 수 : " + menuList.getTotalElements());
    System.out.println("해댕 페이지에 조회될 요소 수 : " + menuList.getSize());
    System.out.println("해당 페이지의 실제 요소 수 : " + menuList.getNumberOfElements());
    System.out.println("현재 페이지 : " + menuList.getNumber() + 1);
    System.out.println("getPageable : " + menuList.getPageable());
    System.out.println("첫 페이지 여부 : " + menuList.isFirst());
    System.out.println("마지막 페이지 여부 : " + menuList.isLast());
    System.out.println("정렬 방식 : " + menuList.getSort());