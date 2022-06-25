import java.text.DecimalFormat;

public class Point implements Comparable<Point>
{
  final double x;
  final double y;

  public Point(double x, double y)
  {
    this.x = x;
    this.y = y;
  }

  @Override
  public String toString()
  {
    DecimalFormat df = new DecimalFormat("0.00000000");
    return String.format("(%s %s)", df.format(x),df.format(y));
  }

  @Override
  public int hashCode()
  {
    return toString().hashCode();
  }

  @Override
  public boolean equals(Object o)
  {
    return toString().equals(o.toString());
  }

  @Override
  public int compareTo(Point p)
  {
    if (x < p.x) return -1;
    if (x > p.x) return 1;

    if (y < p.y) return -1;
    if (y > p.y) return 1;

    return 0;
  }

  public Point add(Point p)
  {
    return new Point(x + p.x, y +  p.y);
  }
  public Point subtract(Point p)
  {
    return add(p.mult(-1.0));
  }
  public Point mult(double m)
  {
    return new Point(x * m, y * m);
  }

  public double getDist(Point p)
  {
    return Math.sqrt(getDist2(p));
  }

  public double getDist2(Point p)
  {
    double dx=Math.abs(p.x - x);
    double dy=Math.abs(p.y - y); 

    return dx*dx+dy*dy;

  }
  public double getDistM(Point p)
  {
    double dx=Math.abs(p.x - x); 
    double dy=Math.abs(p.y - y); 

    return dx+dy;

  }


}
