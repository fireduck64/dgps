import java.util.Scanner;
import java.text.DecimalFormat;
import java.io.FileInputStream;
import java.util.List;
import java.util.LinkedList;
import java.io.File;

public class Conv
{
  public static Long readTime(String line)
  {
    if (!line.startsWith("TIME:")) return null;
    try
    {
      return Long.parseLong(line.split(":")[1]);

    }
    catch(NumberFormatException e)
    {
      return null;
    }
  }
  // "$GNGGA,055932.00,4745.66810,N,12202.60829,W,2,11,1.24,152.3,M,-18.5,M,,0000*75"
  public static Point convertGNGGA(String line)
  {
    if (!line.startsWith("$GNGGA")) return null;

    try
    {
      String[] splice = line.split(",");

      double lat = convertNMEA(Double.parseDouble(splice[2]), splice[3]);
      double lng = convertNMEA(Double.parseDouble(splice[4]), splice[5]);

      return new Point(lat,lng);

    }
    catch(NumberFormatException e)
    {
      return null;
    }

  }
  

  public static double convertNMEA(double nmea, String dir)
  {

    double x = Math.floor(nmea/100.0);
    double rem = ((nmea/100.0) - x)*100.0;
    rem = rem / 60.0;

    x+=rem;

    if (dir.equals("S")) x=-x;
    if (dir.equals("W")) x=-x;

    DecimalFormat df = new DecimalFormat("0.0000000");
    return x;

  }

  public static List<Point> readPathFile(File in)
    throws Exception
  {
    Scanner scan = new Scanner(new FileInputStream(in));

    LinkedList<Point> list = new LinkedList<>();
    while(scan.hasNextDouble())
    {
      double x= scan.nextDouble();
      double y =scan.nextDouble();

      list.add(new Point(x,y));


    }
    return list;

  }

  public static final double EARTH_RADIUS_M=6371000;

  public static double getPointDistMeters(Point a, Point b)
  {
    double dx = getX(a) - getX(b);
    double dy = getY(a) - getY(b);
    double dz = getZ(a) - getZ(b);

    double d = Math.sqrt(dx*dx + dy*dy + dz*dz);
    
    return d;
  }

  private static double getX(Point a)
  {
    return EARTH_RADIUS_M * Math.sin(getRho(a)) * Math.cos(getTheta(a));
  }
  private static double getY(Point a)
  {
    return EARTH_RADIUS_M * Math.sin(getRho(a)) * Math.sin(getTheta(a));
  }
  private static double getZ(Point a)
  {
    return EARTH_RADIUS_M * Math.cos(getRho(a));
  }

  private static double getRho(Point a)
  {
    return (90.0 - a.x) * Math.PI / 180.0;
  }
  public static double getTheta(Point a)
  {
    return (a.y) * Math.PI / 180.0;
  }

  public static void main(String args[]) throws Exception
  {
    Point a = new Point(46,-122);
    Point b = new Point(47,-122);

    System.out.println(getPointDistMeters(a,b));
  }

}
