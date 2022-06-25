import java.util.Scanner;
import java.text.DecimalFormat;

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

}
