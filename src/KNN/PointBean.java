package KNN;
/**
 * 点的坐标 x 、y
 * @author Administrator
 *
 */
public class PointBean {
int x;
int y;
public int getX() {
  return x;
}
public void setX(int x) {
  this.x = x;
}
public int getY() {
  return y;
}
public void setY(int y) {
  this.y = y;
}
public PointBean(int x, int y) {
  super();
  this.x = x;
  this.y = y;
}
public PointBean() {
  super();
}
@Override
public String toString() {
  return "PointBean [x=" + x + ", y=" + y + "]";
}
}