package Zip_File;

import java.util.Comparator;

public class ByteNode implements Comparable<ByteNode> {
    Byte data;
    int frequency;
    ByteNode left;
    ByteNode right;

    public ByteNode(Byte data,int frequency){
        this.data = data;
        this.frequency = frequency;
        this.left = null;
        this.right = null;
    }

    @Override
    public int compareTo(ByteNode o) {
        return this.frequency - o.frequency;
    }
}
