import duckutil.Config;
import duckutil.ConfigFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;
import java.math.BigDecimal;
import java.util.TreeMap;
import java.util.List;
import java.util.LinkedList;

public class CorrectPath
{

  public static void main(String args[]) throws Exception
  {
    List<Point> list = calc(new ConfigFile(args[0]), new File(args[1]));

    System.out.println(list);

    Point min=new Point(1000,1000);
    Point max = new Point(-1000,-1000);
    for(Point p : list)
    {
      min = new Point(Math.min(p.x, min.x), Math.min(p.y, min.y));
      max = new Point(Math.max(p.x, max.x), Math.max(p.y, max.y));
    }
    Point delta = max.subtract(min);
    System.out.println("Range: " + delta);
    System.out.println("min: " + min);
    System.out.println("max: " + max);

  }

  public static List<Point> calc(Config config, File session_file)
    throws Exception
  {
    config.require("base_data_path");

    File dir = new File(config.get("base_data_path"));

    Point base_point = FindBase.calc(config);

    TreeMap<Long, Point> drift_map=new TreeMap<>();

    for(File f : dir.listFiles())
    {
      if (f.getName().endsWith(".log"))
      {
        Long tm=null;
        
        Scanner scan = new Scanner(new FileInputStream(f));

        while(scan.hasNextLine())
        {
          String line = scan.nextLine();
          Long t = Conv.readTime(line);
          if (t != null) tm = t;
          
          Point p = Conv.convertGNGGA(line);
          if (p != null)
          if (tm != null)
          {
            Point delta = base_point.subtract(p);
            drift_map.put(tm, delta);
            tm = null;
          }
        }
        scan.close();
      }
    }

    Scanner scan = new Scanner(new FileInputStream(session_file));
    Long tm=null;
    LinkedList<Point> lst = new LinkedList<>();


    while(scan.hasNextLine())
    {
      String line = scan.nextLine();
      Long t = Conv.readTime(line);
      if (t != null) tm = t;
        
      Point p = Conv.convertGNGGA(line);
      if (p != null) 
      if (tm != null)
      {
        Point delta = drift_map.floorEntry(tm).getValue();
        System.out.println("Delta: " + delta);

        Point corrected = p.add(delta);
        lst.add(corrected);
        //lst.add(p);
      }
   

    }
    scan.close();
    return lst;

  }

}
