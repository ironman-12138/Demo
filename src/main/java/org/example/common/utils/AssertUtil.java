package org.example.common.utils;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import org.example.common.exception.BusinessException;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * 断言工具类
 * @author xCoder
 */
public final class AssertUtil {

    private AssertUtil() {
        throw new RuntimeException("不能实例化的工具类");
    }

    public static void isTrue(boolean flag, String errorMsg) {
        isTrue(flag, "500", errorMsg);
    }

    public static void isTrue(boolean flag, String code, String errorMsg) {
        if (flag) {
            throw new BusinessException(code, errorMsg);
        }
    }

    public static void isFalse(boolean flag, String errorMsg) {
        isFalse(flag, "500", errorMsg);
    }

    public static void isFalse(boolean flag, String code, String errorMsg) {
        if (!flag) {
            throw new BusinessException(code, errorMsg);
        }
    }

    public static void isNull(Object obj, String errorMsg) {
        isNull(obj, "500", errorMsg);
    }

    public static void isNull(Object obj, String code, String errorMsg) {
        if (obj == null) {
            throw new BusinessException(code, errorMsg);
        }
    }

    public static void isNotNull(Object obj, String errorMsg) {
        isNotNull(obj, "500", errorMsg);
    }

    public static void isNotNull(Object obj, String code, String errorMsg) {
        if (obj != null) {
            throw new BusinessException(code, errorMsg);
        }
    }

    public static void isBlank(String str, String errorMsg) {
        isBlank(str, "500", errorMsg);
    }

    public static void isBlank(String str, String code, String errorMsg) {
        if (StrUtil.isBlank(str)) {
            throw new BusinessException(code, errorMsg);
        }
    }

    public static void isListEmpty(List<?> list, String errorMsg) {
        isListEmpty(list, "500", errorMsg);
    }

    public static void isListEmpty(List<?> list, String code, String errorMsg) {
        if (CollUtil.isEmpty(list)) {
            throw new BusinessException(code, errorMsg);
        }
    }

    public static void isMapEmpty(Map<?, ?> map, String errorMsg) {
        isMapEmpty(map, "500", errorMsg);
    }

    public static void isMapEmpty(Map<?, ?> map, String code, String errorMsg) {
        if (CollUtil.isEmpty(map)) {
            throw new BusinessException(code, errorMsg);
        }
    }

    public static <T> void assertionPolicy(T test, Predicate<T> predicate, String errorMsg) {
        assertionPolicy(test, predicate, "500", errorMsg);
    }

    public static <T> void assertionPolicy(T t, Predicate<T> predicate, String code, String errorMsg) {
        if (predicate.test(t)) {
            throw new BusinessException(code, errorMsg);
        }
    }
}

