package JpaBook.JpaShop.domain.item;

import JpaBook.JpaShop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//구현체를 가지고 할 것이므로 추상클래스로 제작
@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Item {

    //상속관계 전약을 지정 - 싱글테이블


    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //비즈니스 로직 addStock(), removeStock()
    //상품 서비스에서 상품 엔티티의 stock 을 컨트롤 하기보다는, 상품 엔티티에 비즈니스 로직을 만드는 것이 엔티티의 독립성을 지켜주기 좋다.

    //재고 증가
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }
    //재고 감소
    public void removeStock(int quantity){
        int restStock = this.stockQuantity - quantity;
        if(restStock < 0 ){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }

    //addStock() : 파라미터로 넘어온 수 만큼 재고를 늘린다. 이 메서드는 재고를 증가하거나 상품을 취소해서 재고를 다시 늘려야 할 때 사용한다.
    //removeStock() : 파라미터로 넘어온 수 만큼 재고를 줄인다. 만약 재고가 부족하면 예외가 발생한다.



}
