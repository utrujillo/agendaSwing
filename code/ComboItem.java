package code;

/**
 * Created by codehero on 29/04/17.
 */
public class ComboItem {
    private int id;
    private String option;

    public ComboItem(int id, String option)
    {
        this.id = id;
        this.option = option;
    }

    public int getId()
    {
        return id;
    }

    public String getOption()
    {
        return option;
    }

    public String toString() { return option; }
}
