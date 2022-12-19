package com.shop.dto;

import com.shop.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemSearchDto {

    //all : 상품등록 전체
    //1d 하루 1w 일주일 1m 한달 6m 6개월
    private String searchDateType; //상품 등록일
    private ItemSellStatus searchSellStatus; //상품 판매 상태
    private String searchBy;
    private String searchQuery = "";
}
