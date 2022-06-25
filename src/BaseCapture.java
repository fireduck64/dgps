
import duckutil.Config;
import duckutil.ConfigFile;
import duckutil.PeriodicThread;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Date;
import java.text.SimpleDateFormat;

import java.io.File;


public class BaseCapture
{
  public static void main(String args[]) 
    throws Exception
  {
    new BaseCapture(new ConfigFile(args[0]));

  }

  private Config config;

  public BaseCapture(Config config)
    throws Exception
  {
    config.require("gps_dev");
    config.require("base_data_path");

    this.config = config;

    new ReaderThread().start();

    System.out.println(FindBase.calc(config));
  }


  public class ReaderThread extends PeriodicThread
  {
    public ReaderThread()
    {
      super(5000);
    }

    public void runPass() throws Exception
    {
      new File(config.get("base_data_path")).mkdirs();
      System.out.println("Starting pass");
      System.out.println("  In: " + config.get("gps_dev"));
      Scanner in = new Scanner(new FileInputStream(config.get("gps_dev")));

      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
      File out_path = new File(config.get("base_data_path"), sdf.format(new Date()) + ".log" );
      System.out.println("  Output: " + out_path);
      PrintStream out = new PrintStream(new FileOutputStream(out_path));

      boolean good_stream=false;

      while(in.hasNextLine())
      {
        String line = in.nextLine();
        line=line.trim();

        if (line.length() > 0)
        if (!line.startsWith("$GNTXT"))
        {
          if (!good_stream)
          if (line.startsWith("$GNGGA"))
          {
            System.out.println("  GPS Data: " + line);
            good_stream=true;
          }
          out.println("TIME:" + System.currentTimeMillis());
          out.println(line);
        }
      }
    }
  }

}
