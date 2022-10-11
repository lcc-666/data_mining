package KNN;
import java.util.ArrayList;
/*
 * ������ KNN
 */
public class TestJava {
  static ArrayList< PointBean> listA;
  static ArrayList< PointBean> listB;
  static ArrayList< PointBean> listC;
  static ArrayList< PointBean> listD;
  public static void main(String[] args) {
    //����Arraylist
    listA=new ArrayList<PointBean>();
    listB=new ArrayList<PointBean>();
    listC=new ArrayList<PointBean>();
    listD=new ArrayList<PointBean>();
    //��Arraylistд������
    setDate();
    getTestResult();
  }
  /**
   * �õ����
   */
  private static void getTestResult() {
    //��������
    KnnMain km=new KnnMain();
    for(int i=0;i<listD.size();i++){
      km.getContent(listA, listB, listC, listD.get(i));
    }
  }
  /**
   * д������
   */
  private static void setDate() {
    //A�������,��A��ĵ��У�1��0������1��1������2��1������2,0),(1,2)�����
    int A_x[]={1,1,2,2,1};
    int A_y[]={0,1,1,0,2};
    //B�������
    int B_x[]={2,3,3,3,4};
    int B_y[]={4,4,3,2,3};
    //C�������
    int C_x[]={4,5,5,6,6};
    int C_y[]={1,2,0,2,1};
    // �������ݣ�����D�������ֱ�������һ��
    //D�������
    int D_x[]={3,3,3,0,5};
    int D_y[]={0,1,5,0,1};
    //��A������listA
    PointBean bA;
    for(int i=0;i<5;i++){
      bA=new PointBean(A_x[i], A_y[i]);
      listA.add(bA);
    }
    //��B������listB
    PointBean bB ;
    for(int i=0;i<5;i++){
      bB=new PointBean(B_x[i], B_y[i]);
      listB.add(bB);
    }
    //��C������listC
    PointBean bC ;
    for(int i=0;i<5;i++){
      bC=new PointBean(C_x[i], C_y[i]);
      listC.add(bC);
    }
    //��D�е����listD
    PointBean bD ;
    for(int i=0;i<5;i++){
      bD=new PointBean(D_x[i], D_y[i]);
      listD.add(bD);
    }
  }
}