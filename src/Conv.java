import java.util.Scanner;
import java.text.DecimalFormat;

public class Conv
{

  public static void main(String args[]) throws Exception
  {
    Scanner scan = new Scanner(System.in);
    double count = 0;
    double sum = 0;

    double nmea = scan.nextDouble();
    String dir = scan.next();
    
    double x = Math.floor(nmea/100.0);
    double rem = ((nmea/100.0) - x)*100.0;
    rem = rem / 60.0;

    x+=rem;

    if (dir.equals("S")) x=-x;
    if (dir.equals("W")) x=-x;

    DecimalFormat df = new DecimalFormat("0.0000000");
    System.out.println(df.format(x));

  }

}
