import java.sql.*;

public class UserDAO{
    /**
     * Return a User object if correct user name and password were given.
     * Return null otherwise.
     * @param userName
     * @param password
     * @return
     */
    public static User login(String userName, String password){
        // Declare the varibles before the try block
        // so that they can be referenced in "finally".
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet set = null;
        User user = null;

        try {
            // Local the JDBC driver, which is needed to connect to the database.
            Class.forName("org.sqlite.JDBC");

            // create a connection to the database
            conn = DriverManager.getConnection("jdbc:sqlite:db.sqlite");

            // Prepare the SQL statement.
            stmt = conn.prepareStatement("select * from users where user_name = ? and password = ?");

            // Set the parameters.
            stmt.setString(1, userName);
            stmt.setString(2, password);

            // Execute the query.
            set = stmt.executeQuery();

            // At most one user is returned from the query.
            if(set.next()) {
                // Create a User and set the attributes.
                user = new User();
                user.setUserName(set.getString("user_name"));
                user.setPassword(set.getString("password"));
                user.setRealName(set.getString("real_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // "finally" is guaranteed to be executed.
            // So it is a good place to make sure the resources are closed.
            if(conn != null) try {conn.close();} catch(Exception e) {e.printStackTrace();}
            if(stmt != null) try {stmt.close();} catch(Exception e) {e.printStackTrace();}
            if(set != null) try {set.close();} catch(Exception e) {e.printStackTrace();}
        }
        return user;
    }

    public static void main(String[] args) {
        User user = UserDAO.login("jguo", "12345");
        System.out.println(user.getUserName());
    }
}