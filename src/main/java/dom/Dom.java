package dom;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import file.FileRecord;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;




public class Dom {

    FileRecord file_XML;

    public Dom (FileRecord file_XML)
    {
        this.file_XML = file_XML;
    }

    public void domArtist ()
    {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document= builder.parse(new File(file_XML.getName()));

            //TEST Affiche la version de XML
            System.out.println(document.getXmlVersion());

            //TEST Affiche l'encodage
            System.out.println(document.getXmlEncoding());

            //TEST Affiche la racine récupérée
            Element racine = document.getDocumentElement();
            System.out.println(racine.getNodeName());

            System.out.println(affiche(racine, ""));

        } catch (Exception e)
        {

        }
    }

    public static String affiche(Node nd, String tab){
        String str = new String();

        if(nd instanceof Element){

            Element element = (Element)nd;

            //Récupération du noeud parcouru
            str += "<" + nd.getNodeName();

            //On test si le noeud possède des attributs
            if(nd.getAttributes() != null && nd.getAttributes().getLength() > 0)
            {
                //Récupération de la liste des attributs
                NamedNodeMap ListeAttributs = nd.getAttributes();

                //Affichage des attributs pour chaque noeud
                for(int i = 0; i < ListeAttributs.getLength(); i++)
                {
                    Node noeud = ListeAttributs.item(i);
                    //On récupère le nom de l'attribut et son contenu
                    str += " " + noeud.getNodeName() + "=\""+noeud.getNodeValue()+"\" ";
                }
            }
            str += ">";

            //Si le Noeud n'a qu'un enfant on récupère le texte
            if(nd.getChildNodes().getLength() == 1)
                str += nd.getTextContent();

            //Récupére liste des Noeud enfants du noeud parcouru
            NodeList list = nd.getChildNodes();
            String tab2 = tab + "\t";

            //Parcours de la liste des nœuds
            for(int i = 0; i < nd.getChildNodes().getLength(); i++)
            {
                Node nd2 = list.item(i);

                if (nd2 instanceof Element)
                    //Appel récursif à la méthode pour le traitement du nœud et de ses enfants
                    str += "\n " + tab2 + affiche(nd2, tab2);
            }

            //Fermeture de la balise
            if(nd.getChildNodes().getLength() < 2)
                str += "</" + nd.getNodeName() + ">";
            else
                str += "\n" + tab +"</" + nd.getNodeName() + ">";
        }

        return str;
    }
}
