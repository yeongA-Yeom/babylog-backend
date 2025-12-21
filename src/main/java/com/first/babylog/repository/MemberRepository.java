package com.first.babylog.repository;

import com.first.babylog.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// JpaRepository<대상엔티티, PK타입>을 상속받으면 기본적인 CRUD 메서드가 자동 생성됨
public interface MemberRepository extends JpaRepository<Member, Long> {

    // [JPA 메서드 명명 규칙 치트시트]
    // 공식: "동사 + By + 필드명" (CamelCase 준수)

    // 1. 조회 (Select) - SELECT * FROM ...
    // findBy..., getBy..., readBy..., queryBy... 모두 똑같이 조회 기능을 합니다.
    Optional<Member> findByEmail(String email);
    // 예: getByName(String name), readByPhone(String phone)

    // 2. 존재 여부 (Exists) - SELECT count(*) > 0 ...
    boolean existsByEmail(String email); // 이메일 중복 검사용

    // 3. 삭제 (Delete) - DELETE FROM ...
    // deleteBy..., removeBy...
    // 예: void deleteByEmail(String email); // 이메일로 회원 삭제

    // 4. 개수 (Count) - SELECT count(*) FROM ...
    // 예: long countByRole(String role); // 특정 권한을 가진 회원 수

    // 5. 저장/수정 (Insert/Update)
    // *주의* 메서드 이름 규칙으로 insertBy... 같은 건 만들지 않습니다!
    // JpaRepository가 기본 제공하는 save(entity) 메서드를 호출하면 알아서 INSERT(없으면)나 UPDATE(있으면)를 합니다.
    // [구분 기준]
    // - Entity의 @Id(PK) 값이 null이면 -> INSERT (새 데이터)
    // - Entity의 @Id(PK) 값이 있으면 -> UPDATE (기존 데이터 수정)

    // 6. 고급 규칙
    // - AND/OR 조건: findByNameAndEmail(String name, String email)
    // - 포함 검색(Like): findByNameContaining(String keyword) -> ... LIKE %keyword%
    // - 정렬(OrderBy): findByAgeOrderByCreatedAtDesc(int age)
    // - 상위 N개(Top/First): findTop3ByAge(int age)
}
