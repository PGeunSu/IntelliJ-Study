package JpaBook.JpaShop.domain.item;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;
    private String name;

    @ManyToMany
    //중간 테이블 맵핑 // 1 대 다  다 대 1을 연결하는 중간 테이블이 필요함
    @JoinTable(name = "category_item",joinColumns = @JoinColumn(name = "category_id"),
                                    inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY) //카테고리는 자체적으로 양방향 관계를 맺음.
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    // 연관관계 메서드
    public void addChildCategory(Category child){
        this.child.add(child);
        child.setParent(this);
    }


}
