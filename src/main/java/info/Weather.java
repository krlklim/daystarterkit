package info;

import info.Models.ModelWeather;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class Weather {
    // 6fff53a641b9b9a799cfd6b079f5cd4e
    public static String getWeather(String message, ModelWeather modelWeather) throws IOException {


        System.out.println("System = " + modelWeather.getUnits());

        String unitIndex = "°C";
        String unit = modelWeather.getUnits();
        if(unit=="imperial"){
            unitIndex="°F";
        }

        System.out.println("System = " + unit);
        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" +
                message + "&units=" + unit +"&appid=6fff53a641b9b9a799cfd6b079f5cd4e");

        Scanner in = new Scanner((InputStream) url.getContent());
        String result = "";
        while (in.hasNext()) {
            result += in.nextLine();
        }

        JSONObject jsObject = new JSONObject(result);
        modelWeather.setName(jsObject.getString("name"));

        JSONObject main = jsObject.getJSONObject("main");
        modelWeather.setTemperature(main.getDouble("temp"));
        modelWeather.setHumidity(main.getDouble("humidity"));

        JSONArray getArray = jsObject.getJSONArray("weather");
        for(int i = 0; i < getArray.length(); i++){
            JSONObject obj = getArray.getJSONObject(i);
            modelWeather.setIcon((String) obj.get("icon"));
            modelWeather.setMain((String) obj.get("main"));
        }

        return "City: " + modelWeather.getName() + "\n" +
                "Temperature: " + modelWeather.getTemperature() + unitIndex + "\n" +
                "Humidity: " + modelWeather.getHumidity() + "%\n" +
                "Weather conditions: " + modelWeather.getMain() + "\n" +
                "http://openweathermap.org/img/w/" + modelWeather.getIcon() + ".png";
    }
}
