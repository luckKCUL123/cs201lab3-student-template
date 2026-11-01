import java.util.ArrayList;
import java.util.List;

public class Q1Test {
     public static void main(String[] args) {

          String result, traversal1, traversal2, traversal3;

          traversal1 = "1-2";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "3-2-1";
          traversal3 = "2-3-1";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "3-1-2-5-4";
          traversal2 = "2-1-4-5-3";
          traversal3 = "1-2-3-4-5";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Preorder, Traversal 2 - Postorder, Traversal 3 - Inorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "10-20-30-40-50";
          traversal2 = "20-10-30-40-50";
          traversal3 = "10-50-40-30-20";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();
     } 

      // write your codes here

     private static final String INVALID = "Invalid traversals";
     private static final String[] NAMES = { "Inorder", "Preorder", "Postorder" };
 
     private static final int[][] PERMS = {
               { 0, 1, 2 }, { 0, 2, 1 }, { 1, 0, 2 },
               { 1, 2, 0 }, { 2, 0, 1 }, { 2, 1, 0 }
     };

     public static String verify(String traversal1, String traversal2, String traversal3){

          int[][] t = new int[3][];
          try {
               t[0] = parse(traversal1);
               t[1] = parse(traversal2);
               t[2] = parse(traversal3);
          } catch (NumberFormatException e) {
               return INVALID;
          }
 
          // All three traversals of the same tree must have the same number of nodes
          if (t[0].length != t[1].length || t[1].length != t[2].length || t[0].length == 0)
               return INVALID;
 
          for (int[] p : PERMS) {
               int[] in = t[p[0]], pre = t[p[1]], post = t[p[2]];
               if (isValidCombination(in, pre, post)) {
                    // role[i] = role played by traversal i
                    String[] role = new String[3];
                    for (int k = 0; k < 3; k++)
                         role[p[k]] = NAMES[k];
                    return "Traversal 1 - " + role[0]
                              + ", Traversal 2 - " + role[1]
                              + ", Traversal 3 - " + role[2];
               }
          }
          return INVALID;
     }

     private static boolean isValidCombination(int[] in, int[] pre, int[] post) {
          Node root = null;
          for (int v : pre) {
               if (contains(root, v))
                    return false; // duplicate node
               root = insert(root, v);
          }
 
          List<Integer> list = new ArrayList<>();
          preorder(root, list);
          if (!matches(list, pre))
               return false;
 
          list.clear();
          inorder(root, list);
          if (!matches(list, in))
               return false;
 
          list.clear();
          postorder(root, list);
          return matches(list, post);
     }
 
     private static int[] parse(String s) {
          String[] parts = s.trim().split("-");
          int[] a = new int[parts.length];
          for (int i = 0; i < parts.length; i++)
               a[i] = Integer.parseInt(parts[i].trim());
          return a;
     }
 
     private static boolean matches(List<Integer> list, int[] arr) {
          if (list.size() != arr.length)
               return false;
          for (int i = 0; i < arr.length; i++)
               if (list.get(i) != arr[i])
                    return false;
          return true;
     }
 
     private static class Node {
          int value;
          Node left, right;
 
          Node(int value) {
               this.value = value;
          }
     }
 
     private static Node insert(Node node, int v) {
          if (node == null)
               return new Node(v);
          if (v < node.value)
               node.left = insert(node.left, v);
          else
               node.right = insert(node.right, v);
          return node;
     }
 
     private static boolean contains(Node node, int v) {
          while (node != null) {
               if (v == node.value)
                    return true;
               node = v < node.value ? node.left : node.right;
          }
          return false;
     }
 
     private static void preorder(Node n, List<Integer> out) {
          if (n == null)
               return;
          out.add(n.value);
          preorder(n.left, out);
          preorder(n.right, out);
     }
 
     private static void inorder(Node n, List<Integer> out) {
          if (n == null)
               return;
          inorder(n.left, out);
          out.add(n.value);
          inorder(n.right, out);
     }
 
     private static void postorder(Node n, List<Integer> out) {
          if (n == null)
               return;
          postorder(n.left, out);
          postorder(n.right, out);
          out.add(n.value);
     }
}