package com.nijunyang.algorithm.tree;

import java.util.*;

/**
 * Description: 哈夫曼树
 * Created by nijunyang on 2020/4/28 21:43
 */
public class HuffmanTree {

    private static final byte ZERO = 0;
    private static final byte ONE = 1;
    HuffmanNode root;
    Map<Character, Integer> weightMap; //字符对应的权重
    List<HuffmanNode> leavesList; // 叶子
    Map<Character, String> leavesCodeMap; // 叶子结点的编码

    public HuffmanTree(Map<Character, Integer> weightMap) {
        this.weightMap = weightMap;
        this.leavesList = new ArrayList<>(weightMap.size());
        this.leavesCodeMap = new HashMap<>(weightMap.size());
        creatTree();
    }

    public static void main(String[] args) {
        Map<Character, Integer> weightMap = new HashMap<>();
        //a:3  f:4  c:6  g:12  d:20  b:24  e:34
        weightMap.put('a', 3);
        weightMap.put('b', 24);
        weightMap.put('c', 6);
        weightMap.put('d', 20);
        weightMap.put('e', 34);
        weightMap.put('f', 4);
        weightMap.put('g', 12);
        HuffmanTree huffmanTree = new HuffmanTree(weightMap);
        //abcd: 1011001101000
        String code = huffmanTree.encode("abcd");
        System.out.println(code);
        System.out.println("1011001101000".equals(code));
        String msg = huffmanTree.decode(code);
        System.out.println(msg);

    }

    /**
     * 构造树结构
     */
    private void creatTree() {
        PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<>();
        weightMap.forEach((k,v) -> {
            HuffmanNode huffmanNode = new HuffmanNode(k, v);
            priorityQueue.add(huffmanNode);
            leavesList.add(huffmanNode);
        });
        int len = priorityQueue.size();//先把长度取出来，因为等下取数据队列长度会变化

        //HuffmanNode实现了Comparable接口，优先队列会帮我们排序，我们只需要每次弹出两个元素就可以了
        for (int i = 0; i < len - 1; i++) {
            HuffmanNode huffmanNode1 = priorityQueue.poll();
            HuffmanNode huffmanNode2 = priorityQueue.poll();
            int weight12 = huffmanNode1.weight + huffmanNode2.weight;

            HuffmanNode parent12 = new HuffmanNode(null, weight12); //父结点不需要数据直接传个null
            parent12.left = huffmanNode1;  //建立父子关系，因为排好序的，所以1肯定是在左边，2肯定是右边
            parent12.right = huffmanNode2;
            huffmanNode1.parent = parent12;
            huffmanNode2.parent = parent12;
            priorityQueue.add(parent12);  //父结点入队
        }
        root = priorityQueue.poll(); //队列里面的最后一个即是我们的根结点


        /**
         * 遍历叶子结点获取叶子结点数据对应编码存放起来，编码时候直接拿出来用
         */
        leavesList.forEach(e -> {
            HuffmanNode current = e;
            StringBuilder code = new StringBuilder();
            do {
                if (current.parent != null && current == current.parent.left) { // 说明当前点是左边
                    code.append(ZERO); //左边0
                } else {
                    code.append(ONE);//左边1
                }
                current = current.parent;
            }while (current.parent != null); //父结点null是根结点
            code.reverse(); //因为我们是从叶子找回去的 ，所以最后需要将编码反转下
            leavesCodeMap.put(e.data, code.toString());
        });
    }

    /**
     * 编码
     */
    public String encode(String msg) {
        char[] chars = msg.toCharArray();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            code.append(leavesCodeMap.get(chars[i]));
        }
        return code.toString();
    }

    /**
     * 解码
     */
    public String decode(String code) {
        char[] chars = code.toCharArray();
        Queue<Byte> queue = new ArrayDeque();
        for (int i = 0; i < chars.length; i++) {
            queue.add(Byte.parseByte(String.valueOf(chars[i])));
        }
        HuffmanNode current = root;
        StringBuilder sb = new StringBuilder();
        while (!queue.isEmpty() ){
            Byte aByte = queue.poll();
            if (aByte == ZERO) {
                current = current.left;
            }
            if (aByte == ONE) {
                current = current.right;
            }
            if (current.right == null && current.left == null) {
                sb.append(current.data);
                current = root;
            }
        }
        return sb.toString();
    }

    /**
     * 结点 实现Comparable接口 方便使用优先队列（PriorityQueue）排序
     */
    private class HuffmanNode implements Comparable<HuffmanNode>{

        Character data;		//字符
        int weight;		//权重
        HuffmanNode left;
        HuffmanNode right;
        HuffmanNode parent;

        @Override
        public int compareTo(HuffmanNode o) {
            return this.weight - o.weight;
        }
        public HuffmanNode(Character data, int weight) {
            this.data = data;
            this.weight = weight;
        }
    }
}
