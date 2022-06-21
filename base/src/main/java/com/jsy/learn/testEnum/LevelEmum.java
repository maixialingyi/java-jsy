package com.jsy.learn.testEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LevelEmum {

    AMOUNT(0, "订单金额"),
    COUNT(1,"订单数量"),
    UNIT_PRICE(2,"订单单价"),
    SHOP_COUNT(3,"下单店铺数"),
    THIRD_CATEGORY_COUNT(4,"下单三级类目数");

    private Integer value;
    private String desc;

    public static void switchEmun(LevelEmum levelEmum){
        switch (levelEmum){
            case COUNT:
                System.out.println("count");
                break;
            case AMOUNT:
                System.out.println(AMOUNT);
                break;
            default:
                System.out.println("null");
        }
    }

    public static void main(String[] args) {
        LevelEmum.switchEmun(LevelEmum.SHOP_COUNT);
    }
}
