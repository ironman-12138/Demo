package org.example.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.example.common.exception.BusinessException;

@Slf4j
public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String getJson(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (Exception e) {
            throw new BusinessException("JSON转换字符串异常");
        }
    }

    public static T getJsonStrToClass(String s, Class<T> tClass) {
        try {
            return objectMapper.readValue(s, tClass);
        } catch (Exception e) {
            throw new BusinessException("JSON转换字符串异常");
        }
    }

}
