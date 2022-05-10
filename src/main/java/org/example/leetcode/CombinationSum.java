package org.example.leetcode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 给你一个 无重复元素 的整数数组 candidates 和一个目标整数 target ，找出 candidates 中可以使数字和为目标数 target 的 所有不同组合 ，并以列表形式返回。你可以按 任意顺序 返回这些组合。
 *
 * candidates 中的 同一个 数字可以 无限制重复被选取 。如果至少一个数字的被选数量不同，则两种组合是不同的。 
 *
 * 对于给定的输入，保证和为 target 的不同组合数少于 150 个。
 *
 *  示例 1：
 *
 * 输入：candidates = [2,3,6,7], target = 7
 * 输出：[[2,2,3],[7]]
 * 解释：
 * 2 和 3 可以形成一组候选，2 + 2 + 3 = 7 。注意 2 可以使用多次。
 * 7 也是一个候选， 7 = 7 。
 * 仅有这两种组合。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/combination-sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class CombinationSum {

    /**
     * 采用搜索回溯的方法解决
     * @param candidates
     * @param target
     * @return
     */
    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        Set<List<Integer>> res = new HashSet<>();
        List<Integer> item = new ArrayList<>();
        search(candidates, target, res, item, 0);
        List<List<Integer>> result = new ArrayList<>(res);
        return result;
    }

    private static void search(int[] candidates, int target, Set<List<Integer>> res, List<Integer> item, int index) {
        if (index == candidates.length) {
            return;
        }

        if (target == 0) {
            // 这里之前的写法是 lists.add(item);
            // 这样是不对的，因为lists里面添加的是之前的那个list，指向的是之前的那个地址，而之前的那个list一直在变，变到最后就全是空的。
            // 所以最后就相当于在lists里面添加了一堆空list
            res.add(new ArrayList<>(item));
        }

        //跳过当前值回溯
        search(candidates, target, res, item, index + 1);

        //加入当前值回溯
        if (target - candidates[index] >= 0) {
            item.add(candidates[index]);
            search(candidates, target - candidates[index], res, item, index);
            item.remove(item.size() - 1);
        }
    }

    public static void main(String[] args) {
        int[] ints = new int[]{2,3,6,7};
        int target = 7;
        System.out.println(combinationSum(ints, target).toString());
    }

}
