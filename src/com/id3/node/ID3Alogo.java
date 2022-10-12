package com.id3.node;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * ID3算法
 * @author JoMint
 *
 */
public class ID3Alogo {

    //存每个节点及其属性等相关变量
    private List<TreeNode> treeList;
    //存数据集
    private List<Map<String, String>> dataList;
    //遍历决策树时的开始节点
    private Attributes startNode;
    //决策结果变量的值
    private List<String> resultList;
    //结果属性节点
    private TreeNode resultNode;
    //决策树
    private String str;

    //构建决策树的开始调用方法
    public void ID3(String id3Name,String readPath,String printPath){

        //初始化成员变量
        initElement(id3Name);
        //读数据
        readData(readPath);
        //构建决策树
        cusTree(dataList, treeList, startNode);
        //System.out.println(startNode.getNextNode().getAttributes().get("Overcast").getLeafName());
        //遍历决策树，并把结果存入str中

        printTree(startNode,"");
        //打印决策树
        System.out.println(str);
        //输出决策树到文件
        printTreetoTxt(printPath);

    }

    /**
     * 初始化成员变量
     */
    private void initElement(String id3Name) {

        //存每个节点及其属性等相关变量
        treeList = new ArrayList<TreeNode>();
        //存数据集
        dataList = new ArrayList<Map<String,String>>();
        //遍历决策树时的开始节点
        startNode = new Attributes();
        //决策结果变量的值
        resultList = new ArrayList<String>();
        //结果属性节点
        TreeNode resultNode = null;
        //决策树
        str = id3Name+"决策树:\r\n";

    }


    /**
     * 读数据
     */
    private void readData(String path) {

        Map<String, String> dataMap;
        Map<String,Attributes> attrMap;
        TreeNode treeNode;
        int num;

        //创建读取properties文件的对象
        Properties pro = new Properties();


        try {
            //为了读取中文字符，将读取文件的类型改为字符流读取
            InputStream inputStream = new FileInputStream(path);
            BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
            //加载数据文件
            pro.load(bf);
            //读取数据总个数
            num = Integer.parseInt(pro.getProperty("datanum"));
            //读取属性及属性值
            String attribute = pro.getProperty("nodeAndAttribute");
            //将每个属性分开，用数组存，遍历每个属性，再把每个属性的属性值分开，存到treeList中
            String[] attArray = attribute.split(",");
            for (int i = 0; i < attArray.length; i++) {

                treeNode = new TreeNode();
                String[] temp = attArray[i].split(":");
                String nodeName = temp[0];
                String[] attr = temp[1].split("/");
                treeNode.setNodeName(nodeName);
                attrMap = new HashMap<String, Attributes>();
                Attributes attributes;
                for (int j = 0; j < attr.length; j++) {
                    //Map<String, Integer> map = new HashMap<String, Integer>();
                    attributes = new Attributes();
                    //map.put(attr[j], 0);
                    attributes.setAttrName(attr[j]);
                    attrMap.put(attr[j], attributes);

                    //存入结果变量的值，为最后的判断做铺垫
                    if(i == attArray.length-1){

                        resultList.add(attr[j]);

                    }

                }
                treeNode.setAttributes(attrMap);
                treeList.add(treeNode);
            }

            //遍历数据集，将数据按行存入dataList中
            for (int i = 1; i <= num; i++) {

                dataMap = new HashMap<String, String>();
                String key = "D"+i;
                String[] colline = pro.getProperty(key).split(",");
                //System.out.println(key+"=="+colline.length);
                for (int j = 0; j < treeList.size(); j++) {
                    //System.out.println(treeList.size());
                    dataMap.put(treeList.get(j).getNodeName(), colline[j]);
                }
                dataList.add(dataMap);
            }

            //得到结果属性的名字
            resultNode = treeList.get(treeList.size()-1);


//            System.out.println("************************resultNode==" + resultNode + "***********************");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    /**
     * 数据处理
     * @param dataList
     * @param treeList
     */
    private List<List> dealData(List<Map<String, String>> dataList, List<TreeNode> treeList){


        List<List> returnList= new ArrayList<List>();
        int num = dataList.size();

        /*
         * 统计数据集中每个属性的属性值个数
         */
        Map<String, Attributes> attrMap = new HashMap<String, Attributes>();
        Map<String, Integer> resultMap;
        for (int i = 0; i < treeList.size(); i++) {
            for (int j = 0; j < dataList.size(); j++) {
                //获得当前数据集中当前列当前行的属性值
                String key = dataList.get(j).get(treeList.get(i).getNodeName());
                attrMap = treeList.get(i).getAttributes();
                //System.out.println(attrMap.get(key)+"=="+key);
                //计算样本中对应的属性变量的个数
                attrMap.get(key).setAttrNum(attrMap.get(key).getAttrNum()+1);

                //System.out.println("->"+attrMap.get(key));

                //获得结果变量值
                String result = dataList.get(j).get(treeList.get(treeList.size()-1).getNodeName());
                resultMap = attrMap.get(key).getResultNum();
                //如果包含这个结果变量，则数量上加1; 如果不包含，赋初值为1
                if (resultMap.containsKey(result)) {
                    resultMap.put(result, resultMap.get(result)+1);
                }else{
                    resultMap.put(result, 1);
                }
            }
        }
        /*
         * 计算熵
         */
        DecimalFormat df = new DecimalFormat("#.###");
        for (int i = 0; i < treeList.size(); i++) {
            //遍历 Attributes
            //计算属性熵： gain
            double gain = 0.0;
            for (Map.Entry<String, Attributes> element : treeList.get(i).getAttributes().entrySet()) {
                Attributes attr = treeList.get(i).getAttributes().get(element.getKey());
                Map<String, Integer> result = attr.getResultNum();
                //遍历每个 Attributes 的 resultNum
                //计算属性值的熵 ：h
                double h = 0.0;
                for (Map.Entry<String, Integer> element2 : result.entrySet()) {
                    double resultNum = (double)result.get(element2.getKey());
                    double attrNum = (double)attr.getAttrNum();
                    resultNum = resultNum/attrNum;
                    h -= (resultNum*(Math.log(resultNum)/Math.log((double)2)));
                    h = Double.parseDouble(df.format(h));
                    attr.setH(h);
                }
                gain += ((double)attr.getAttrNum()/num)*attr.getH();
                gain = Double.parseDouble(df.format(gain));

            }

            treeList.get(i).setGain(gain);
        }

        //将处理好的dataList和treeList放在returnList中返回
        returnList.add(dataList);
        returnList.add(treeList);
        return returnList;


    }

    /**
     * 构建决策树
     * @param dataList
     * @param treeList
     */
    @SuppressWarnings("unchecked")
    private void cusTree(List<Map<String, String>> dataList, List<TreeNode> treeList, Attributes cAttr){
        List<List> curryList= new ArrayList<List>();
        //处理数据
        curryList = dealData(dataList, treeList);
        //从 curryList 中得到 dataList 和 treeList
        dataList = (List<Map<String, String>>)curryList.get(0);
        treeList = (List<TreeNode>)curryList.get(1);


        //判断当前处理的数据集中的决策结果，若决策结果相同的个数等于总的当前处理的数据集的条数，则遍历结束
        //将当前的决策结果放入当前判断的属性值的后边
        //返回到调用这个函数的父函数
        for (TreeNode treeNode : treeList) {
            if (treeNode.getNodeName().equals(resultNode.getNodeName())) {
                for (String attr : resultList) {
                    if (treeNode.getAttributes().get(attr).getAttrNum() == dataList.size()) {
                        cAttr.setLeafName(attr);
                        return;
                    }
                }
            }
        }
        //寻找最优解
        //得到根节点
        TreeNode rootNode = treeList.get(0);
        for (TreeNode treeNode : treeList) {

            if(!treeNode.getNodeName().equals(treeList.get(treeList.size()-1).getNodeName())){
                if(treeNode.getGain() < rootNode.getGain()){
                    rootNode = treeNode;
                }
            }

        }
        cAttr.setNextNode(rootNode);
        //对当前根节点的属性进行遍历，寻找下一个节点
        //节点名
        String nodeName = rootNode.getNodeName();
        //属性名
        String attrName = "";
        //属性节点
        Attributes attr = new Attributes();
        //当前节点的属性值集合
        Map<String, Attributes> attrMap = rootNode.getAttributes();
        //遍历节点的每个属性值
        for (Map.Entry<String, Attributes> entry : attrMap.entrySet()) {
            attr = attrMap.get(entry.getKey());
            attrName = attr.getAttrName();
            //得到新的data集合对象
            List<Map<String, String>> newDataList = new ArrayList<Map<String,String>>();
            Map<String, String> newMap = new HashMap<String, String>();
            //String attrName = rootNode.getAttributes().get("Sunny").getAttrName();
            newMap.clear();
            //删除dataList中已处理过的节点数据
            //遍历dataList
            for (Map<String, String> map : dataList) {

                if(map.containsKey(nodeName)){

                    if(map.get(nodeName).equals(attrName)){
                        newMap = new HashMap<String, String>();
                        for (Map.Entry<String, String> m : map.entrySet()) {

                            //如果该节点不是已处理过的节点
                            if(!m.getKey().equals(nodeName)){
                                //得到新的节点
                                newMap.put(m.getKey(), map.get(m.getKey()));
                            }

                        }

                        //将新的节点存入newDataList中
                        newDataList.add(newMap);
                    }

                }

            }

            //获得新的tree集合对象，而且值为初值
            List<TreeNode> newTreeList = new ArrayList<TreeNode>();
            //将treeList中的数据清空
            clearTree(treeList);

            //删除treeList中已处理过的节点
            for (TreeNode treeNode : treeList) {
                if(!treeNode.getNodeName().equals(nodeName)){
                    newTreeList.add(treeNode);
                }
            }

            //递归调用当前函数，继续找节点
            cusTree(newDataList, newTreeList,attr);
        }
    }

    /**
     * 输出决策树
     * @param attr
     */
    private void printTree(Attributes attr, String ceil) {

        String nodeName = attr.getNextNode().getNodeName();
        Map<String, Attributes> attrMap = attr.getNextNode().getAttributes();

        str += ceil+"----"+nodeName+"\r\n";
        for (Map.Entry<String, Attributes> nextAttr:attrMap.entrySet()) {

            //如果当前属性值没有下一个节点，则将当前属性值的名称及决策结果输出
            if(attrMap.get(nextAttr.getKey()).getNextNode() == null){

                str += ceil+"-------"+attrMap.get(nextAttr.getKey()).getAttrName()+"\r\n";
                str += ceil+"----------"+attrMap.get(nextAttr.getKey()).getLeafName()+"\r\n";

            }else{

                str += ceil+"-------"+attrMap.get(nextAttr.getKey()).getAttrName()+"\r\n";
                printTree(attrMap.get(nextAttr.getKey()),"------");
            }
        }

    }

    /**
     * 打印决策树到txt文本
     * @param path
     */
    private void printTreetoTxt(String path){

        if(path == null || path.equals("")) return;
        File file = new File(path);
        File folder = file.getParentFile();
        FileWriter fw;
        try {
            if(!folder.exists()){
                folder.mkdirs();
                file.createNewFile();
            }

            fw = new FileWriter(file);
            fw.write(str);

            fw.flush();
            fw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /**
     * 还原初始数据
     * @param treeList
     */
    private void clearTree(List<TreeNode> treeList){

        for (TreeNode treeNode : treeList) {
            Map<String, Attributes> map = treeNode.getAttributes();

            for (Map.Entry<String, Attributes> entry : map.entrySet()) {
                Attributes attr = map.get(entry.getKey());
                attr.setAttrNum(0);
                attr.setH(0);
                Map<String, Integer> map2 = attr.getResultNum();
                map2.clear();
            }
            treeNode.setGain(0);
        }
    }}

