package org.example.domin.myenum;

import cn.hutool.core.bean.BeanUtil;
import lombok.AllArgsConstructor;
import lombok.var;

import java.util.List;
import java.util.Map;

/**
 * 设备类型
 */
@AllArgsConstructor
public enum IntegerEnum {

    TRUE(0, "启用或未删除"),
    FALSE(1, "关闭或删除");

    private Integer type;
    private String name;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static IntegerEnum valueOfByType(Integer type) {
        if (type != null) {
            for (IntegerEnum e : IntegerEnum.values()) {
                if (type.equals(e.getType())) {
                    return e;
                }
            }
        }
        return null;
    }

}
