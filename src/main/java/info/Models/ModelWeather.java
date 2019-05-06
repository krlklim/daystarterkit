package info.Models;

public class ModelWeather {
    private String name;
    private static String units = "metric";

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    private Double temperature;
    public Double getTemperature(){
        return  temperature;
    }

    public void setTemperature(Double temperature){
        this.temperature = temperature;
    }

    private Double humidity;
    public Double getHumidity(){
        return humidity;
    }

    public void setHumidity(Double humidity){
        this.humidity = humidity;
    }

    private String icon;
    public String getIcon(){
        return  icon;
    }

    public void setIcon(String icon){
        this.icon = icon;
    }

    private String main;
    public String getMain(){
        return  main;
    }

    public void setMain(String main){
        this.main = main;
    }
}
