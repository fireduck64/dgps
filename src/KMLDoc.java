
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.io.OutputStream;
import org.w3c.dom.Document;

import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import java.text.DecimalFormat;

public class KMLDoc
{

  Document root_doc;
  Node kml_root;
  Node folder_root;

  public KMLDoc(InputStream input)
    throws Exception
  {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    root_doc = builder.parse(input);

    kml_root = findNode(root_doc, "kml");

    folder_root = findNode(kml_root, "Document");

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

  public void addPath(String folder_name, String path_name, List<Point> path)
  {
    Element top_folder = createOrOpenFolder(folder_name);

    Element folder = root_doc.createElement("Folder");
    top_folder.appendChild(folder);

    Element name = root_doc.createElement("name");
    name.setTextContent(path_name);
    folder.appendChild(name);

    Element placemark = root_doc.createElement("Placemark");
    folder.appendChild(placemark);

    Element multigeo = root_doc.createElement("MultiGeometry");
    placemark.appendChild(multigeo);

    Element line_string = root_doc.createElement("LineString");
    multigeo.appendChild(line_string);

    Element clamp = root_doc.createElement("altitudeMode");
    clamp.setTextContent("clampedToGround");
    line_string.appendChild(clamp);

    Element coord = root_doc.createElement("coordinates");
    line_string.appendChild(coord);

    StringBuilder sb = new StringBuilder();
    DecimalFormat df = new DecimalFormat("0.000000000");
    for(Point p : path)
    {
      sb.append(String.format("%s,%s,0.000", df.format(p.y), df.format(p.x)));
      sb.append("\n");
    }
    coord.setTextContent(sb.toString());

  }

  public void addPoint(String folder_name, String point_name, Point p)
  {
    Element top_folder = createOrOpenFolder(folder_name);

    Element folder = root_doc.createElement("Folder");
    top_folder.appendChild(folder);


    Element placemark = root_doc.createElement("Placemark");
    folder.appendChild(placemark);

    Element name = root_doc.createElement("name");
    name.setTextContent(point_name);
    placemark.appendChild(name);

    Element point = root_doc.createElement("Point");
    placemark.appendChild(point);

    Element clamp = root_doc.createElement("altitudeMode");
    clamp.setTextContent("clampedToGround");
    point.appendChild(clamp);

    Element coord = root_doc.createElement("coordinates");
    point.appendChild(coord);

    StringBuilder sb = new StringBuilder();
    DecimalFormat df = new DecimalFormat("0.000000000");
    sb.append(String.format("%s,%s,0.000", df.format(p.y), df.format(p.x)));
    coord.setTextContent(sb.toString());

  }


  private Node findNode(Node start, String name)
  {
    
    NodeList nl = start.getChildNodes();
    for(int i=0; i<nl.getLength(); i++)
    {
      Node n = nl.item(i);
      if (n.getNodeName().equals(name)) return n;
    }

    return null;

  }

  private Element createOrOpenFolder(String folder_name)
  {
    NodeList nl = folder_root.getChildNodes();
    for(int i=0; i<nl.getLength(); i++)
    {
      Node n = nl.item(i);
      if (n instanceof Element)
      if (n.getNodeName().equals("Folder"))
      {
        Node name_node = findNode(n, "name");
        if (name_node.getTextContent().equals(folder_name))
        {
          return (Element) n;
        }
      }
    }

    Element e = root_doc.createElement("Folder");
    Element name = root_doc.createElement("name");
    name.setTextContent(folder_name);
    e.appendChild(name);
    folder_root.appendChild(e);

    
    return e;
  }

	public static void main(String args[])
    throws Exception
	{
    new KMLDoc(new FileInputStream(args[0])).write(new FileOutputStream(args[1]));
	}	

}
