package org.example.leetcode;

/**
 * 19. 删除链表的倒数第 N 个结点
 * 给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点。
 *
 *  输入：head = [1,2,3,4,5], n = 2
 * 输出：[1,2,3,5]
 * 示例 2：
 *
 * 输入：head = [1], n = 1
 * 输出：[]
 *
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/remove-nth-node-from-end-of-list
 * 作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class RemoveNthFromEnd {

    public static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode listNode = new ListNode();
        listNode.next = head.next;
        int length = 0;
        while (listNode != null) {
            length++;
            listNode = listNode.next;
        }
        if (length == 1 && n == 1) {
            return new ListNode();
        }
        if (length == n) {
            return head.next;
        }
        listNode = head;
        for (int i = 0; i < length - n - 1; i++) {
            listNode = listNode.next;
        }
        listNode.next = listNode.next.next;
        return head;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1, new ListNode(2));
        ListNode listNode = removeNthFromEnd(head, 1);
        while (listNode != null) {
            System.out.println(listNode.val);
            listNode = listNode.next;
        }
    }

}