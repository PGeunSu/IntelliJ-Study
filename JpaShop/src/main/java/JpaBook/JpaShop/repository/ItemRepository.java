package JpaBook.JpaShop.repository;

import JpaBook.JpaShop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    //상품 저장
    public void save(Item item){
        if(item.getId() == null){ //item 은 처음에 처음에 저장할 때 아이디가 없음 -> jpa 가 제공하는 persist 를 사용
            em.persist(item); //새로 생성하는 상품 - 신규등록
        }else{
            em.merge(item); //update 와 유사(db에 등록된 것을 가져와서 작업)
        }

        //save() id가 없으면 신규로 보고 persist() 실행
        //id가 있으면 이미 데이터베이스에 저장된 엔티티를 수정한다고 보고, merge()를 실행, 자세한 내용은 뒤에 웹에서 설명
    }


    public Item findOne(Long id){
        return em.find(Item.class, id);
    }
    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }


}
