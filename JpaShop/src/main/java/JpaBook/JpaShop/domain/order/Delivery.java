package JpaBook.JpaShop.domain.order;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery")
    private Order order;

    @Embedded
    private Address address;


    @Enumerated(EnumType.STRING) //항상 String 타입으로 설정
    private DeliveryStatus status; //READY, COMP




}
