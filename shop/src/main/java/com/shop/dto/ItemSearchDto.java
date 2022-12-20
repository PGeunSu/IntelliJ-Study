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
    private String searchBy; //상품명으로 조죄할 때 어떤 유형으로 조회할 지
    private String searchQuery = ""; //조회할 검색어를 저장할 변수
    //searchBy 가 itemNm 상품명을 기준으로 검색, createBy 상품등록자 아이디 기준검색
}
