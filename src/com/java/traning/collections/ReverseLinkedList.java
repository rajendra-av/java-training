package com.java.traning.collections;

public class ReverseLinkedList {

    // A node in a singly linked list
    static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }

    // Reverses the list in place and returns the new head.
    static ListNode reverseList(ListNode head) {
        ListNode prev = null;      // the reversed part of the list, initially empty
        ListNode current = head;   // the node we are currently processing

        while (current != null) {
            // Save the rest of the list before we overwrite current.next
            ListNode next = current.next;

            // Point the current node backward to the previous node
            current.next = prev;

            // Move both pointers one step forward
            prev = current;
            current = next;
        }

        // When the loop ends, current is null and prev is the old tail,
        // which is now the new head
        return prev;
    }

    // Helper: builds a list from an array of values
    static ListNode build(int... values) {
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        for (int v : values) {
            tail.next = new ListNode(v);
            tail = tail.next;
        }
        return dummy.next;
    }

    // Helper: converts a list to a string for printing
    static String toText(ListNode head) {
        StringBuilder sb = new StringBuilder("[");
        for (ListNode node = head; node != null; node = node.next) {
            sb.append(node.val);
            if (node.next != null) {
                sb.append(" -> ");
            }
        }
        return sb.append("]").toString();
    }

    public static void main(String[] args) {
        ListNode head = build(1, 2, 3, 4, 5);
        System.out.println("Original : " + toText(head));

        ListNode reversed = reverseList(head);
        System.out.println("Reversed : " + toText(reversed));

        System.out.println("Empty    : " + toText(reverseList(null)));
    }
}