
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
import duckutil.gpio.GPIO;
import duckutil.gpio.GPIOPin;


public class MobileCapture
{
  public static void main(String args[]) 
    throws Exception
  {
    new MobileCapture(new ConfigFile(args[0]));

  }

  private Config config;

  private Object session_lock = new Object();
  private PrintStream session_out;

  public MobileCapture(Config config)
  {
    config.require("gps_dev");
    config.require("mobile_data_path");
    config.require("record_pin");

    this.config = config;

    new ReaderThread().start();
    new SessionThread().start();
  }

  public class SessionThread extends PeriodicThread
  {
    public SessionThread()
    {
      super(250);

    }

    public void runPass() throws Exception
    {
      GPIOPin pin = GPIO.openPin( config.getInt("record_pin") );
      pin.setDirectionOut();
      new File(config.get("mobile_data_path")).mkdirs();
      if (pin.getValue())
      {
        synchronized(session_lock)
        {
          if (session_out == null)
          {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            File out_path = new File(config.get("mobile_data_path"), "session-" + sdf.format(new Date()) + ".log" );
            System.out.println("Opening session: " + out_path);
            session_out = new PrintStream(new FileOutputStream(out_path));
          }
        }

      }
      else
      {
        synchronized(session_lock)
        {
          if (session_out != null)
          {
            System.out.println("Closing session");
            session_out.close();
            session_out = null;
          }
        }
      }
    }
  }

  public class ReaderThread extends PeriodicThread
  {
    public ReaderThread()
    {
      super(5000);
    }

    public void runPass() throws Exception
    {
      new File(config.get("mobile_data_path")).mkdirs();
      System.out.println("Starting pass");
      System.out.println("  In: " + config.get("gps_dev"));
      Scanner in = new Scanner(new FileInputStream(config.get("gps_dev")));

      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
      File out_path = new File(config.get("mobile_data_path"), sdf.format(new Date()) + ".log" );
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
          long tm = System.currentTimeMillis();
          out.println("TIME:" + tm);
          out.println(line);
          synchronized(session_lock)
          {
            if (session_out != null)
            {
              session_out.println("TIME:" + tm);
              session_out.println(line);
            }

          }
        }
      }
    }
  }

}
