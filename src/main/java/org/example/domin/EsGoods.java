package org.example.domin;

import lombok.Data;

@Data
public class EsGoods {

    private String userId;

    private String goodsId;

    public EsGoods(String userId, String goodsId) {
        this.userId = userId;
        this.goodsId = goodsId;
    }

    public EsGoods() {
    }
}
