package af.familiaws;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import servicios.IServicioWeather;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ImplementacionWsWeather implements IServicioWeather {


    private static String getElementValue(NodeList nodeList, String tagName) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            if (nodeList.item(i).getNodeName().equals(tagName)) {
                return nodeList.item(i).getTextContent();
            }
        }
        return null;
    }

    @Override
    public void displayWeatherData() {
        try {

            Scanner scanner = new Scanner(System.in);
            String city;

            // Retrieve user input
            System.out.println("===================================================");
            System.out.print("Enter City (Say No to Quit): ");
            city = scanner.nextLine();

            // URL de la API
            String url = "http://api.weatherapi.com/v1/current.xml?key=dea10ee743a84733814201028240108&q="+city+"&aqi=no";
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");

            // Leer la respuesta
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            conn.disconnect();

            // Parsear la respuesta XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new java.io.ByteArrayInputStream(content.toString().getBytes()));

            // Obtener y mostrar algunos campos de la respuesta
            Element root = doc.getDocumentElement();
            NodeList location = root.getElementsByTagName("location").item(0).getChildNodes();
            NodeList current = root.getElementsByTagName("current").item(0).getChildNodes();

            System.out.println("Location:");
            System.out.println("Name: " + getElementValue(location, "name"));
            System.out.println("Region: " + getElementValue(location, "region"));
            System.out.println("Country: " + getElementValue(location, "country"));

            System.out.println("\nCurrent Weather:");
            System.out.println("Temperature (C): " + getElementValue(current, "temp_c"));
            System.out.println("Condition: " + getElementValue(current, "condition"));
            System.out.println("Humidity: " + getElementValue(current, "humidity"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
