package org.example.leetcode;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。 有效字符串需满足：
 *
 * 左括号必须用相同类型的右括号闭合。
 * 左括号必须以正确的顺序闭合。
 */
public class Bracket {

    public static Boolean matching(String str) {
        Map<String, String> map = new HashMap<>();
        map.put("}", "{");
        map.put(")", "(");
        map.put("]", "[");
        char[] chars = str.toCharArray();
        char[] cs = new char[chars.length];
        int i = 0;
        for (char c:chars) {
            if (map.containsValue(String.valueOf(c))) {
                cs[i++] = c;
            }else {
                if (i == 0 || !map.get(String.valueOf(c)).equals(String.valueOf(cs[i - 1]))) {
                    return false;
                }else {
                    i--;
                }
            }
        }
        if (i == 0) {
            return true;
        }else {
            return false;
        }
    }

    public static void main(String[] args) {
        System.out.println(matching("()"));
        System.out.println(matching("()[]{}"));
        System.out.println(matching("(]"));
        System.out.println(matching("([)]"));
        System.out.println(matching("{[]}"));
        System.out.println(matching("[[]({}([]())[][{()}])]"));
    }

}
