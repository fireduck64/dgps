import java.util.Scanner;
import java.text.DecimalFormat;

public class Average
{

  public static void main(String args[]) throws Exception
  {
    Scanner scan = new Scanner(System.in);
    double count = 0;
    double sum = 0;

    while(scan.hasNextDouble())
    {
      double d = scan.nextDouble();
      count += 1.0;
      sum += d;

    }
    DecimalFormat df = new DecimalFormat("0.0000000");
    System.out.println(df.format(sum / count));


  }

}
