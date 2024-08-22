package af.familiarest;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import servicios.IServicioWeather;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ImplementacionRestWeather implements IServicioWeather {

    @Override
    public void displayWeatherData() {
        try{
            Scanner scanner = new Scanner(System.in);
            String city;
            do{
                // Retrieve user input
                System.out.println("===================================================");
                System.out.print("Enter City (Say No to Quit): ");
                city = scanner.nextLine();

                if(city.equalsIgnoreCase("No")) break;

                displayWeatherData(city);
            }while(!city.equalsIgnoreCase("No"));

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static void displayWeatherData(String city){
        try{
            // 1. Fetch the API response based on API Link
            String url = "http://api.weatherapi.com/v1/current.json?key=dea10ee743a84733814201028240108&q="+ city + "&aqi=no";
            HttpURLConnection apiConnection = fetchApiResponse(url);

            // check for response status
            // 200 - means that the connection was a success
            if(apiConnection.getResponseCode() != 200){
                System.out.println("Error: Could not connect to API");
                return;
            }

            // 2. Read the response and convert store String type
            String jsonResponse = readApiResponse(apiConnection);

            // 3. Parse the string into a JSON Object
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(jsonResponse);
            JSONObject currentWeatherJson = (JSONObject) jsonObject.get("current");
//            System.out.println(currentWeatherJson.toJSONString());

            // 4. Store the data into their corresponding data type
            String time = (String) currentWeatherJson.get("last_updated");
            System.out.println("Ultima Actualizacion: " + time);

            double temperature = (double) currentWeatherJson.get("temp_c");
            System.out.println("Current Temperature (C): " + temperature);

            long relativeHumidity = (long) currentWeatherJson.get("humidity");
            System.out.println("Relative Humidity: " + relativeHumidity);

            double windSpeed = (double) currentWeatherJson.get("wind_mph");
            System.out.println("Wind mph: " + windSpeed);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static String readApiResponse(HttpURLConnection apiConnection) {
        try {
            // Create a StringBuilder to store the resulting JSON data
            StringBuilder resultJson = new StringBuilder();

            // Create a Scanner to read from the InputStream of the HttpURLConnection
            Scanner scanner = new Scanner(apiConnection.getInputStream());

            // Loop through each line in the response and append it to the StringBuilder
            while (scanner.hasNext()) {
                // Read and append the current line to the StringBuilder
                resultJson.append(scanner.nextLine());
            }

            // Close the Scanner to release resources associated with it
            scanner.close();

            // Return the JSON data as a String
            return resultJson.toString();

        } catch (IOException e) {
            // Print the exception details in case of an IOException
            e.printStackTrace();
        }

        // Return null if there was an issue reading the response
        return null;
    }

    private static HttpURLConnection fetchApiResponse(String urlString){
        try{
            // attempt to create connection
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // set request method to get
            conn.setRequestMethod("GET");

            return conn;
        }catch(IOException e){
            e.printStackTrace();
        }

        // could not make connection
        return null;
    }
}
