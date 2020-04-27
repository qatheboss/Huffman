package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Huffman {


    private class HuffNode implements Comparable{
        public Character ch;
        public int count;
        public HuffNode left;
        public HuffNode right;

        public HuffNode(Character ch, int count, HuffNode left, HuffNode right) {
            this.ch = ch;
            this.count = count;
            this.left = left;
            this.right = right;

        }

        @Override
        public int compareTo(Object o) {
            HuffNode other =(HuffNode) o;
            return count - other.count;
        }
    }

    HashMap<Character, String> encodes;
    HashMap<String, Character> decodes;
    HuffNode root;
    public void buildTree(String fileName) {
        HashMap<Character, HuffNode> freq = new HashMap<Character, HuffNode>();
        try {
            Scanner fin = new Scanner(new File(fileName));
            while (fin.hasNextLine()) {
                String line = fin.nextLine() + "\n";
                for (int i=0; i < line.length(); i++) {
                    if (freq.containsKey(line.charAt(i))) {
                        freq.get(line.charAt(i)).count += 1;
                    }
                    else {
                        freq.put(line.charAt(i), new HuffNode(line.charAt(i), 1, null, null));

                    }
                }

            }
            fin.close();
        }
        catch (FileNotFoundException fnf) {
            System.out.println("No Files Found!!!!!!");
        }
        PriorityQueue<HuffNode> pq = new PriorityQueue<HuffNode>();
        Iterator it = freq.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry me = (Map.Entry) it.next();
            HuffNode hn = (HuffNode) me.getValue();
            pq.add(hn);
        }
        while (pq.size() > 1) {
            HuffNode left = pq.remove();
            HuffNode right = pq.remove();
            HuffNode parent = new HuffNode('\0', left.count + right.count, left, right);
            pq.add(parent);
        }
        root = pq.remove();
        encodes = new HashMap<Character, String>();
        decodes = new HashMap<String, Character>();

        buildHuffmanTree(root, "");
    }

    private  void buildHuffmanTree(HuffNode root, String s) {
        if (root.left == null && root.right == null) {
            encodes.put(root.ch, s);
            decodes.put(s, root.ch);
        }
        else {
            buildHuffmanTree(root.left, s + "0");
            buildHuffmanTree(root.right, s + "1");

        }
    }
    public String encode(String fileName) {
        try {
            Scanner fin = new Scanner(new File(fileName));
            String encoded = "";
            while (fin.hasNextLine()) {
                String line = fin.nextLine();
                for (int i=0; i < line.length(); i++) {
                    if (encodes.containsKey(line.charAt(i))) {
                        encoded += encodes.get(line.charAt(i));
                    }

                }

            }
            fin.close();
            return encoded;
        }
        catch (FileNotFoundException fnf) {
            System.out.println("No Files Found!!!!!!");
        }
        return "";
    }

    public String decode(String code) {
        String text = "";
        for (int i = 0; i < code.length(); i++) {
            for (int j = i + 1; j <= code.length(); j++) {
                String sub = code.substring(i,j);
                if(decodes.containsKey(sub)) {
                    text += decodes.get(sub);
                    i = j-1;
                    break;
                }
            }
        }
        return text;
    }
    public void breadthFirst() {
        Queue<HuffNode> q = new LinkedList<HuffNode>();
        q.add(root);
        while (q.isEmpty() == false) {
            HuffNode hn = q.remove();
            if (hn.left == null && hn.right == null) {
                System.out.println(hn.ch + " " + hn.count);
            }
            else {
                q.add(hn.left);
                q.add(hn.right);
            }
        }
    }
}
