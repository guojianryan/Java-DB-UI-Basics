import java.util.ArrayList;

public class AtheleteDB {
    private static ArrayList<Athlete> data;
    static{
        data = new ArrayList<>();
        data.add(new Athlete(100, "Declan", "Brown", "Football"));
        data.add(new Athlete( 101, "David", "Wong", "Basketball"));
        data.add(new Athlete( 102, "Austin", "Wu", "Snowboarding"));
        data.add(new Athlete( 103, "Owen", "Chu", "Chess"));
    }

    public static ArrayList<Athlete> findAll() {
        // This is a dummy function.
        // You are supposed to read the data from the database.
        return data;
    }

    public static void update(int rowIndex, Athlete a) {
        // This is a dummy function.
        // You are supposed to update the data in the database;
        // You do not need the first rowIndex parameter if you findAll gets the data
        // from the database.
        System.out.println(a + " is being updated");
        data.set(rowIndex, a);
    }

    public static void delete(Athlete a) {
        // This is a dummy function.
        // You are supposed to update the data in the database.
        System.out.println(a + " is being deleted");
        data.remove(a);
    }
}
