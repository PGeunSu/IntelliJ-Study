package com.shop.repository;

import com.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ItemRepository extends JpaRepository<Item, Long>,
        QuerydslPredicateExecutor<Item>, ItemRepositoryCustom {
    //쿼리 메서드
    //스프링 데이터 jpa 에서 제공
    //Repository interface 에서 간단한 네이밍 룰을 이용하여 메소드를 작성하면 원하는 쿼리 실행 가능

    //Entity 이름은 생략가능
    //find + (Entity 이름) + By + 변수이름

    //QuerydslPredicateExecutor - Repository 인터페이스 지원
    //하나의 Entity 반환, 모든 Entity 반환, 매칭되는 Entity 결과, 수를 반환



    List<Item> findByItemNm(String itemNm);

    List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);

    List<Item> findByPriceLessThan(int price);

    List<Item> findByPriceLessThanOrderByPriceDesc(int price);



    @Query("select i from Item i where i.itemDetail like " + "%:itemDetail% order by i.price desc")
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);



    @Query(value="select * from item i where i.item_detail like " + "%:itemDetail% order by i.price desc", nativeQuery = true)
    List<Item> findByItemDetailByNative(@Param("itemDetail") String itemDetail);

}
