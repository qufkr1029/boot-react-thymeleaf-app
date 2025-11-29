package ai.junbeom.demo.account.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "accounts") // 테이블 이름은 일반적으로 복수형으로 지정
@Getter
@Setter // 필요에 따라 Setter 사용 여부 결정 (보통 도메인 객체는 Setter 최소화)
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA 사용 시 기본 생성자 필수
@AllArgsConstructor // 모든 필드를 포함하는 생성자
@Builder // 빌더 패턴 사용
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String role; // 예: "USER", "ADMIN" 등
}
