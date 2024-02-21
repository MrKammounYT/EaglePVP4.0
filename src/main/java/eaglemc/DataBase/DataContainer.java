package eaglemc.DataBase;

public class DataContainer {


    private String customName;

    private int points;
    private int deaths;
    private String name;
    private  int kills;

    private int top;

    public DataContainer(int top,String name, int points, int deaths,int kills, String customName) {
        this.top =top;
        this.customName = customName;
        this.name = name;
        this.points = points;
        this.deaths = deaths;
        this.kills = kills;
    }

    public int getPoints() {
        return points;
    }

    public int getTop() {
        return top;
    }

    public int getKills() {
        return kills;
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
