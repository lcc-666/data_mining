package KNN;
import java.util.ArrayList;
/*
 * 主函数 KNN
 */
public class TestJava {
  static ArrayList< PointBean> listA;
  static ArrayList< PointBean> listB;
  static ArrayList< PointBean> listC;
  static ArrayList< PointBean> listD;
  public static void main(String[] args) {
    //创建Arraylist
    listA=new ArrayList<PointBean>();
    listB=new ArrayList<PointBean>();
    listC=new ArrayList<PointBean>();
    listD=new ArrayList<PointBean>();
    //给Arraylist写入数据
    setDate();
    getTestResult();
  }
  /**
   * 得到结果
   */
  private static void getTestResult() {
    //创建对象
    KnnMain km=new KnnMain();
    for(int i=0;i<listD.size();i++){
      km.getContent(listA, listB, listC, listD.get(i));
    }
  }
  /**
   * 写入数据
   */
  private static void setDate() {
    //A的坐标点,即A类的点有（1，0），（1，1），（2，1），（2,0),(1,2)五个点
    int A_x[]={1,1,2,2,1};
    int A_y[]={0,1,1,0,2};
    //B的坐标点
    int B_x[]={2,3,3,3,4};
    int B_y[]={4,4,3,2,3};
    //C的坐标点
    int C_x[]={4,5,5,6,6};
    int C_y[]={1,2,0,2,1};
    // 测试数据，测试D中五个点分别属于哪一类
    //D的坐标点
    int D_x[]={3,3,3,0,5};
    int D_y[]={0,1,5,0,1};
    //把A类点放入listA
    PointBean bA;
    for(int i=0;i<5;i++){
      bA=new PointBean(A_x[i], A_y[i]);
      listA.add(bA);
    }
    //把B类点放入listB
    PointBean bB ;
    for(int i=0;i<5;i++){
      bB=new PointBean(B_x[i], B_y[i]);
      listB.add(bB);
    }
    //把C类点放入listC
    PointBean bC ;
    for(int i=0;i<5;i++){
      bC=new PointBean(C_x[i], C_y[i]);
      listC.add(bC);
    }
    //把D中点放入listD
    PointBean bD ;
    for(int i=0;i<5;i++){
      bD=new PointBean(D_x[i], D_y[i]);
      listD.add(bD);
    }
  }
}