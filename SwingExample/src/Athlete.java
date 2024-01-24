import javax.swing.*;
import java.util.Objects;

public class Athlete {

    private int id;
    private String firstName;
    private String lastName;
    private String sport;

    @Override
    public String toString() {
        return "Athlete{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", sport='" + sport + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Athlete(int id, String firstName, String lastName, String sport) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sport = sport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Athlete sportsman = (Athlete) o;
        return Objects.equals(firstName, sportsman.firstName) && Objects.equals(lastName, sportsman.lastName) && Objects.equals(sport, sportsman.sport);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, sport);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

}