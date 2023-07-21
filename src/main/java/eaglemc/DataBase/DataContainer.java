package eaglemc.DataBase;

public class DataContainer {


    private String customName;

    private int points;
    private int deaths;
    private String name;

    public DataContainer(String name, int points, int deaths, String customName) {
        this.customName = customName;
        this.name = name;
        this.points = points;
        this.deaths = deaths;
    }

    public int getPoints() {
        return points;
    }

    public int getDeaths() {
        return deaths;
    }

    public String getCustomName() {
        return customName;
    }

    public String getName() {
        return name;
    }
}
