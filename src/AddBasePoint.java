import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.io.File;
import duckutil.ConfigFile;

public class AddBasePoint
{
  public static void main(String args[])
    throws Exception
  {
    KMLDoc doc = new KMLDoc(new FileInputStream(args[0]));
    Point base = FindBase.calc(new ConfigFile(args[1]));
    doc.addPoint("Spots", "Base", base);
    doc.write(new FileOutputStream(args[2]));

  }

}
