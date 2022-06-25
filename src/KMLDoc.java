
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.w3c.dom.Document;


import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;

public class KMLDoc
{

  Document root_doc;

  public KMLDoc(InputStream input)
    throws Exception
  {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    root_doc = builder.parse(input);

  }

  public void write(OutputStream output)
    throws Exception
  {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    DOMSource source = new DOMSource(root_doc);
    StreamResult result = new StreamResult(output);

    transformer.transform(source, result);
  }

	public static void main(String args[])
    throws Exception
	{
    new KMLDoc(new FileInputStream(args[0])).write(new FileOutputStream(args[1]));
	}	

}
