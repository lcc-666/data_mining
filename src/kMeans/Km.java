package kMeans;

import java.util.*;

public class Km {
    private final ArrayList<double[]> dataSet; // 数据集链表
    private ArrayList<double[]> center; // 中心点链表
    private ArrayList<ArrayList<double[]>> cluster; // 聚类链表
    private int k; // 类数
    private int m; // 迭代次数
    private int dataSetLength; // 数据集长度
    private final ArrayList<Double> wc; // 每次迭代的误差链表

    public Km(int k) { // 构造函数
        if (k < 1)
            k = 1;
        this.k = k;
        dataSet = new ArrayList<>();
        center = new ArrayList<>();
        cluster = new ArrayList<>();
        m = 0;
        dataSetLength = 0;
        wc = new ArrayList<>();
    }

    private ArrayList<ArrayList<double[]>> getCluster() { // 获取聚类链表
        return cluster;
    }

    private void init() { // 初始化，样本数据集
        if (dataSet.size() == 0) {
            double[][] dataSetArray = new double[][] { { 8, 2 }, { 3, 4 },
                    { 2, 5 }, { 4, 2 }, { 7, 3 }, { 6, 2 }, { 4, 7 }, { 6, 3 },
                    { 5, 3 }, { 6, 3 }, { 6, 9 }, { 1, 6 }, { 3, 9 }, { 4, 1 },
                    { 8, 6 } };
            dataSet.addAll(Arrays.asList(dataSetArray));
        }
        dataSetLength = dataSet.size();
        if (k > dataSetLength) {
            k = dataSetLength;
        }
        center = initCenter();//初始化中心点
        cluster = initCluster();//初始化空的聚类链表
    }

    private ArrayList<double[]> initCenter() {//初始化中心点，防止找到数据集中的同一个点，但是有一个问题是也不保证数据集中存在相同的点
        ArrayList<double[]> center = new ArrayList<>();
        int[] randoms = new int[k];
        boolean flag;
        Random random = new Random();
        int temp = random.nextInt(dataSetLength);
        randoms[0] = temp;
        for (int i = 1; i < k; i++) {
            flag = true;
            while (flag) {
                temp = random.nextInt(dataSetLength);
                int j = 0;
                while (j < i) {
                    if (temp == randoms[j]) {
                        break;
                    }
                    j++;
                }
                if (j == i) {
                    flag = false;
                }
            }
            randoms[i] = temp;
        }
        for (int i = 0; i < k; i++) {
            center.add(dataSet.get(randoms[i]));// 生成初始化中心链表
            // System.out.println(center.get(i)[0]+" "+center.get(i)[1]);
        }
        return center;
    }

    private ArrayList<ArrayList<double[]>> initCluster() {
        //初始化空的聚类链表
        for (int i = 0; i < k; i++) {
            ArrayList<double[]> clusters = new ArrayList<>();
            cluster.add(clusters);
        }
        return cluster;
    }

    private double distance(double[] point, double[] center) {
        double x = point[0] - center[0];
        double y = point[1] - center[1];
        return x * x + y * y;
    }
    //求出离样本点最近的中心点
    private int minDistance(double[] distance) {
        double minDistance = distance[0];
        int minLocation = 0;
        for (int i = 1; i < k; i++) {
            if (minDistance > distance[i]) {
                minDistance = distance[i];
                minLocation = i;
            } else if (distance[i] == minDistance) // 如果相等，随机返回一个位置
            {
                Random random = new Random();
                if (random.nextInt(10) < 5) {
                    minLocation = i;
                }
            }
        }
        return minLocation;
    }

    private void setCluster() { //重新设置
        double[] dist = new double[k];
        //通过两个for循环求dataset中点跟初始中心点的距离
        for (int i = 0; i < dataSetLength; i++) {
            for (int j = 0; j < k; j++) {
                dist[j] = distance(dataSet.get(i), center.get(j));
            }
            //第二个for循环，求出样本中某点和所有中心点的距离后，调用minDistance求出离该点最近的中心点，归为一簇
            cluster.get(minDistance(dist)).add(dataSet.get(i));
        }
    }

    private ArrayList<double[]> updateCenter() {//更新中心点


        for (int i = 0; i < k; i++) {
            double[] newCenter = new double[2];
            int n = cluster.get(i).size();
            if (n != 0) {
                for (int j = 0; j < n; j++) {
                    newCenter[0] += cluster.get(i).get(j)[0];
                    newCenter[1] += cluster.get(i).get(j)[1];
                }
                newCenter[0] = newCenter[0]/n;
                newCenter[1] = newCenter[1]/n;
                center.set(i, newCenter);
            }
        }
        return center;

    }

    private void errorSquare() {   //计算每次迭代后，所有点与其对应中心点的距离误差值
        double errorValue = 0;
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < cluster.get(i).size(); j++) {

                errorValue += distance(cluster.get(i).get(j), center.get(i));
            }
        }
        wc.add(errorValue);
    }

    private void kmeans() {
        init();//初始化样本数据集
        while (true) {
            // System.out.println(cluster.size());
            setCluster();
            errorSquare();
            if (m != 0) {
                if (wc.get(m) - wc.get(m - 1) == 0)
                    System.out.println(m);
                break;
            }
            center = updateCenter();
            m++;
            cluster.clear();
            cluster = initCluster();
        }
    }

    public void printDataArray(ArrayList<double[]> dataArray,
                               String dataArrayName) {
        for (int i = 0; i < dataArray.size(); i++) {
            System.out.println("print:" + dataArrayName + "[" + i + "]={"
                    + dataArray.get(i)[0] + "," + dataArray.get(i)[1] + "}");
        }
        System.out.println("===================================");
    }

    private void execute() {
        long startTime = System.currentTimeMillis();
        System.out.println("kmeans begins");
        kmeans();
        long endTime = System.currentTimeMillis();
        System.out.println("kmeans running time=" + (endTime - startTime)
                + "ms");
        System.out.println("kmeans ends");
        System.out.println();
    }

    public static void main(String[] args) {
        Km kl = new Km(3);//设置聚类的簇数
        kl.execute();
        // System.out.println(kl.center.get(9)[0]+" "+kl.center.get(9)[1]);
        ArrayList<ArrayList<double[]>> cluster = kl.getCluster();

        for (int i = 0; i < cluster.size(); i++) {
            kl.printDataArray(cluster.get(i), "cluster[" + i + "]");
        }
    }
}