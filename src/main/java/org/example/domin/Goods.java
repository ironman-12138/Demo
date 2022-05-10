package org.example.domin;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class Goods implements Serializable {

    static final long serialVersionUID = 21L;

    @ApiModelProperty(value = "id主键")
    private Long id;

    @ApiModelProperty(value = "商品名称")
    @Excel(name = "商品名称", width = 20)
    private String name;

    @ApiModelProperty(value = "购物车中商品数量")
    @Excel(name = "商品数量", width = 20)
    private Integer num;

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", num=" + num +
                '}';
    }
}
