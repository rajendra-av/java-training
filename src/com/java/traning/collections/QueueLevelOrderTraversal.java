package com.java.traning.collections;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class QueueLevelOrderTraversal {

    // A node in a binary tree
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    // Returns the node values grouped by level, from top to bottom, left to right.
    static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;                       // an empty tree has no levels
        }

        // Queue of nodes waiting to be visited, in left-to-right order
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            // The number of nodes in the queue is exactly the size of the current level.
            // Capturing it now keeps this level separate from the children we add below.
            int levelSize = queue.size();
            List<Integer> level = new ArrayList<>();

            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();    // take the next node from the front
                level.add(node.val);

                // Queue the children so they form the next level, in left-to-right order
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }

            result.add(level);                   // finished one level
        }

        return result;
    }

    public static void main(String[] args) {
        //       3
        //      / \
        //     9  20
        //        / \
        //       15  7
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);

        System.out.println(levelOrder(root));   // [[3], [9, 20], [15, 7]]
        System.out.println(levelOrder(null));   // []
    }
}