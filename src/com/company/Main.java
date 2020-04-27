package com.company;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {
	// write your code here
        String fileName = "fish.txt";
        Huffman h = new Huffman();
        h.buildTree(fileName);
        String code = h.encode(fileName);
        System.out.println(code);
        String text = h.decode(code);
        System.out.println(text);
        h.breadthFirst();

    }


}
