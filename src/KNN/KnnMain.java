package KNN;
import java.util.ArrayList;
/**
 * KNNʵ�ֵķ��������㷨�൱��K=1������ҵ�����Ե������ĳ��p��p������һ�࣬���Ե��������һ��
 * @author Administrator
 *
 */
public class KnnMain {
  public double getPointLength(ArrayList<PointBean> list,PointBean bb){
    int b_x=bb.getX();
    int b_y=bb.getY();
    double temp=(b_x -list.get(0).getX())*(b_x -list.get(0).getX())+
        (b_y -list.get(0).getY())*(b_y -list.get(0).getY());
    // �ҳ���С�ľ���
    for(int i=1;i<list.size();i++){
      if(temp<((b_x -list.get(i).getX())*(b_x -list.get(i).getX())+
          (b_y -list.get(i).getY())*(b_y -list.get(i).getY()))){
        temp=(b_x -list.get(i).getX())*(b_x -list.get(i).getX())+
            (b_y -list.get(i).getY())*(b_y -list.get(i).getY());
      }
    }
    return Math.sqrt(temp);
  }
  /**
   * ��ȡ���ȣ��ҳ���С��һ�����й���
   * @param list1
   * @param list2
   * @param list3
   * @param bb
   */
  public void getContent(ArrayList<PointBean> list1,ArrayList<PointBean> list2,
      ArrayList<PointBean> list3,PointBean bb){
    double A=getPointLength(list1,bb);//��A����е��������
    double B=getPointLength(list2,bb);//��B����е��������
    double C=getPointLength(list3,bb);//��C����е��������
    //�����Ƚ�
    if(A>B){
      if(B>C){
        System.out.println("�����:"+bb.getX()+" , "+bb.getY()+" " +"����C");
      }else {
        System.out.println("�����:"+bb.getX()+" , "+bb.getY()+" " +"����B");
      }
    }else {
      if(A>C){
        System.out.println("�����:"+bb.getX()+" , "+bb.getY()+" " +"����C");
      }else {
        System.out.println("�����:"+bb.getX()+" , "+bb.getY()+" " +"����A");
      }
    }
  }
}