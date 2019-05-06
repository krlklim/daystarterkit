package info;
import info.Models.ModelITNews;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class BusinessNews {
    public static String getBusinessNews(ModelITNews model) throws IOException {
        URL url = new URL("https://newsapi.org/v2/top-headlines?q=business&apiKey=c4d37aaecdc5475d83813414e39c973d");
        Scanner in = new Scanner((InputStream) url.getContent());
        String res = "";
        StringBuilder str = new StringBuilder();

        while (in.hasNext()) {
            res += in.nextLine();
        }

        System.out.println(res);

        JSONObject jsonObject = new JSONObject(res);
        JSONArray getArray = jsonObject.getJSONArray("articles");
        if (getArray.length() >= 0) {
            JSONObject obj = getArray.getJSONObject(0);
            model.setTitle((String) obj.get("title"));
            model.setDescription((String) obj.get("description"));
            model.setImage((String) obj.get("urlToImage"));

        }

        str.append("Title: " + model.getTitle() + "\n" +
                "Description: " + model.getDescription() + "\n" +
                model.getImage());
        return str.toString();
    }
}

