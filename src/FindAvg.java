import duckutil.Config;

import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;
import java.math.BigDecimal;
import java.io.FileOutputStream;

public class FindAvg
{

  public static Point calc(File src)
    throws Exception
  {
  
    long good = 0;

    BigDecimal sum_x=BigDecimal.ZERO;
    BigDecimal sum_y=BigDecimal.ZERO;

    Scanner scan = new Scanner(new FileInputStream(src));

    while(scan.hasNextLine())
    {
      String line = scan.nextLine();
      Point p = Conv.convertGNGGA(line);
      if (p != null)
      {
        good++;
        sum_x = sum_x.add(new BigDecimal(p.x));
        sum_y = sum_y.add(new BigDecimal(p.y));
      }
    }

    System.out.println("Total data points: " + good);
    double total = good;
    double x = sum_x.divide(new BigDecimal(good), BigDecimal.ROUND_HALF_UP).doubleValue();
    double y = sum_y.divide(new BigDecimal(good), BigDecimal.ROUND_HALF_UP).doubleValue();
    return new Point(x,y);
  }

  public static void main(String args[]) throws Exception
  {
    Point base = skewTimeReport(new File(args[0]));
    KMLDoc doc = new KMLDoc(new FileInputStream(args[1]));
    doc.addPoint("Spots", "Perg", base);
    doc.write(new FileOutputStream(args[2]));

  }

  public static Point skewTimeReport(File src)
    throws Exception
  {
    Point base = calc(src);
    System.out.println("Base point: " + base);
  
    long good = 0;

    BigDecimal sum_x=BigDecimal.ZERO;
    BigDecimal sum_y=BigDecimal.ZERO;

    Scanner scan = new Scanner(new FileInputStream(src));
    Long start_time = null;
    Long last_time = null;
    long print_ms = 0;
    long report_every_ms = 20000;

    while(scan.hasNextLine())
    {
      String line = scan.nextLine();
      Point p = Conv.convertGNGGA(line);
      if (p != null)
      {
        good++;
        sum_x = sum_x.add(new BigDecimal(p.x));
        sum_y = sum_y.add(new BigDecimal(p.y));
      }
      Long t = Conv.readTime(line);
      if (t != null)
      {
        if (start_time == null) start_time = t;
        last_time = t;
        {
          if (print_ms + report_every_ms < last_time)
          {
            print_ms = last_time;
            if (good > 0)
            {
              double x = sum_x.divide(new BigDecimal(good), BigDecimal.ROUND_HALF_UP).doubleValue();
              double y = sum_y.divide(new BigDecimal(good), BigDecimal.ROUND_HALF_UP).doubleValue();
              Point c = new Point(x,y);
              double delta = Conv.getPointDistMeters(c, base);
              long sec = (print_ms - start_time) / 1000;

              System.out.println("At " + sec + " skew is " + delta + " m");
            }
          }

        }
      }
    }

    System.out.println("Total data points: " + good);
    double x = sum_x.divide(new BigDecimal(good), BigDecimal.ROUND_HALF_UP).doubleValue();
    double y = sum_y.divide(new BigDecimal(good), BigDecimal.ROUND_HALF_UP).doubleValue();

    return base;


  }

}
