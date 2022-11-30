package JpaBook.JpaShop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본키 생성을 데이터 베이스 위임 (AUTO INCREMENT)
    private Long id;

    @Column
    private String username;
}
