package org.example.leetcode;

public class AddTwoNumbers {

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode listNode = new ListNode();
        ListNode newNode = listNode;
        int x = 0;
        while (l1 != null || l2 != null || x != 0) {

            newNode.val = (l1 != null ? l1.val : 0) + (l2 != null ? l2.val : 0) + x;
            if (newNode.val >= 10) {
                x = newNode.val / 10;
                newNode.val = newNode.val % 10;
            }else {
                x = 0;
            }
            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }
            if (l1 != null || l2 != null || x != 0) {
                ListNode sumNode = new ListNode();
                newNode.next = sumNode;
                newNode = newNode.next;
            }
        }
        return listNode;
    }

    public static void main(String[] args) {
//        ListNode l1 = new ListNode(2, new ListNode(4, new ListNode(3)));
        ListNode l1 = new ListNode(9);
//        ListNode l2 = new ListNode(5, new ListNode(6, new ListNode(4)));
        ListNode l2 = new ListNode(1, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9)))))))));
        ListNode listNode = addTwoNumbers(l1, l2);
        while (listNode.next != null) {
            System.out.println(listNode.val);
            listNode = listNode.next;
        }
        System.out.println(listNode.val);
    }

}

class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}