package JpaBook.JpaShop.domain;

import javax.persistence.*;

import JpaBook.JpaShop.domain.order.Address;
import JpaBook.JpaShop.domain.order.Order;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded //내장 타입을 포함 했다는 어노테이션
    private Address address;

    @OneToMany(mappedBy = "member") //일 대 다 관계 //order 테이블에 member 필드에 맵핑됐다.
    //읽기 전용
    private List<Order> orders = new ArrayList<>();

}
