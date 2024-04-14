package Zip_File;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class Compressed {
    
    private static Map<Byte,String> huffman = new HashMap<Byte, String>();
    private static StringBuilder stringBuilder = new StringBuilder();

    public boolean CompressedData(String source,String destination){
        boolean isCom = false;
        try {
            FileInputStream fileInputStream = new FileInputStream(source);
            byte[] bytes = new byte[fileInputStream.available()];
            fileInputStream.read(bytes);
            byte[] huffmanCode = createZipData(bytes);
            OutputStream outputStream = new FileOutputStream(destination);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(huffmanCode);
            objectOutputStream.writeObject(huffman);
            fileInputStream.close();
            outputStream.close();
            objectOutputStream.close();
            System.out.println("Data is Compressed");
            isCom = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return isCom;
    }

    private byte[] createZipData(byte[] fileInputStream) {
        MinPriorityQueue<ByteNode> huffmanQue = getBytesCode(fileInputStream);
        ByteNode node = getHuffmanTree(huffmanQue);
        Map<Byte,String> huffmanCode = getHuffmanCode(node);
        byte[] zipCode = getZipCode(fileInputStream,huffmanCode);
        return zipCode;
    }

    private static byte[] getZipCode(byte[] fileInputStream, Map<Byte, String> huffmanCode) {
        StringBuilder str = new StringBuilder();
        for(byte b:fileInputStream){
            str.append(huffmanCode.get(b));
        }
        int len = (str.length()+7)/8;
        byte[] arr = new byte[len];
        int idx=0;

        for (int i=0;i<str.length();i+=8){
            String s;
            if(i+8>str.length()){
                s = str.substring(i);
            }else{
                s = str.substring(i,i+8);
            }
            arr[idx] = (byte) Integer.parseInt(s,2);
            idx++;
        }
        return arr;
    }

    // use PreOrder Traversal To get The Huffman code of all Bytes
    private static Map<Byte,String> getHuffmanCode(ByteNode node) {
        if(node==null) return null;
        getHuffmanCode(node.left,"0",stringBuilder);
        getHuffmanCode(node.right,"1",stringBuilder);
        return huffman;
    }

    private static void getHuffmanCode(ByteNode root,String code,StringBuilder stb){
        StringBuilder stb2 = new StringBuilder(stb);
        stb2.append(code);
        if(root!=null){
            if(root.data==null){
                getHuffmanCode(root.left,"0",stb2);
                getHuffmanCode(root.right,"1",stb2);
            }else{
                huffman.put(root.data,stb2.toString());
            }
        }
    }

    // get The Root Node of Queue..
    private static ByteNode getHuffmanTree(MinPriorityQueue<ByteNode> huffmanQue) {
        while (huffmanQue.len()>1){
            ByteNode left = huffmanQue.poll();
            ByteNode right = huffmanQue.poll();
            ByteNode parent = new ByteNode(null,left.frequency+right.frequency);
            parent.left = left;
            parent.right = right;
            huffmanQue.add(parent);
        }
        return huffmanQue.poll();
    }

    // Create a MiMinPriorityQueue basic of bytes and frequency
    private static MinPriorityQueue<ByteNode> getBytesCode(byte[] bytes) {
       MinPriorityQueue<ByteNode> huffmanQueue = new MinPriorityQueue<ByteNode>();
       Map<Byte,Integer> b = new HashMap<>();
       for(byte byt:bytes){
           Integer val = b.get(byt);
           if(val==null){
               b.put(byt,1);
           }else{
               b.put(byt,val+1);
           }
       }
       for(Map.Entry<Byte,Integer> i : b.entrySet()){
           huffmanQueue.add(new ByteNode(i.getKey(),i.getValue()));
       }
        System.out.println("MinPriority is Done..");
       return huffmanQueue;
    }

    public boolean deCompressed(String source,String destination){
        boolean decom = false;
        try {
            FileInputStream fileInputStream = new FileInputStream(source);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            byte[] huffmanByte = (byte[]) objectInputStream.readObject();
            Map<Byte,String> huffmanCode = (Map<Byte, String>) objectInputStream.readObject();

            byte[] bytes = decomposeData(huffmanByte,huffmanCode);
            OutputStream outputStream = new FileOutputStream(destination);
            outputStream.write(bytes);
            fileInputStream.close();
            outputStream.close();
            objectInputStream.close();
            System.out.println("Data is Dempressed..");
            decom = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return decom;
    }

    private byte[] decomposeData(byte[] huffmanByte, Map<Byte, String> huffmanCode) {
        StringBuilder str = new StringBuilder();
        for(int i=0;i<huffmanByte.length;i++){
            byte b = huffmanByte[i];
            boolean flag = (i==huffmanByte.length-1);
            str.append(convertData(!flag,b));
        }
        Map<String, Byte> map = new HashMap<>();
        for (Map.Entry<Byte, String> entry : huffmanCode.entrySet()) {
            map.put(entry.getValue(), entry.getKey());
        }
        ArrayList<Byte> arrayList = new ArrayList<>();
        for(int i=0;i<str.length();){
            int count = 1;
            boolean flag = true;
            Byte b = null;
            while (flag==true){
                String s = str.substring(i,i+count);
                b = map.get(s);
                if(b==null){
                    count++;
                }else {
                    flag = false;
                }
            }
            arrayList.add(b);
            i+=count;
        }
        byte[] ans = new byte[arrayList.size()];
        for (int i=0;i<ans.length;i++){
            ans[i] = arrayList.get(i);
        }
        return ans;
    }

    private String convertData(boolean b, byte b1) {
        int bt = b1;
        if(b) bt|=256;
        String str = Integer.toBinaryString(bt);
        if(b || bt<0){
            return str.substring(str.length() - 8);
        }
        return str;
    }

}
