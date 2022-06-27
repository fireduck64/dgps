import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.io.File;
import duckutil.ConfigFile;

public class AddPath
{
  public static void main(String args[])
    throws Exception
  {
    KMLDoc doc = new KMLDoc(new FileInputStream(args[0]));

    List<Point> path = Conv.readPathFile(new File(args[1]));
    String path_name=args[2];
    doc.addPath("Paths", path_name, path);

    doc.write(new FileOutputStream(args[3]));

  }

}
