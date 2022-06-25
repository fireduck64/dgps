import duckutil.Config;

import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;
import java.math.BigDecimal;

public class FindBase
{

  public static Point calc(Config config)
    throws Exception
  {
    config.require("base_data_path");

    File dir = new File(config.get("base_data_path"));
    long good = 0;

    BigDecimal sum_x=BigDecimal.ZERO;
    BigDecimal sum_y=BigDecimal.ZERO;

    for(File f : dir.listFiles())
    {
      if (f.getName().endsWith(".log"))
      {
        
        Scanner scan = new Scanner(new FileInputStream(f));

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

        scan.close();

      }
    }
    System.out.println("Total data points: " + good);
    double total = good;
    double x = sum_x.divide(new BigDecimal(good), BigDecimal.ROUND_HALF_UP).doubleValue();
    double y = sum_y.divide(new BigDecimal(good), BigDecimal.ROUND_HALF_UP).doubleValue();
    return new Point(x,y);


  }

}
