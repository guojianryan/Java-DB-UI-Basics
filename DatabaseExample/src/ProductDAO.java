import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ProductDAO {
    public static List<Product> getAllProducts() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet set = null;
        try {
            Class.forName("org.sqlite.JDBC");
            // create a connection to the database
            conn = DriverManager.getConnection("jdbc:sqlite:db.sqlite");
            stmt = conn.prepareStatement("select *" +
                    "from products;");
            set = stmt.executeQuery();
            List<Product> list = new LinkedList<>();
            while(set.next()) {
                Product prod = new Product();
                prod.setProdId(set.getInt("prod_id"));
                prod.setProdName(set.getString("prod_name"));
                prod.setPrice(set.getDouble("price"));
                prod.setProdType(set.getString("prod_type"));
                list.add(prod);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(conn != null) try {conn.close();} catch(Exception e) {e.printStackTrace();}
            if(stmt != null) try {stmt.close();} catch(Exception e) {e.printStackTrace();}
            if(set != null) try {set.close();} catch(Exception e) {e.printStackTrace();}
        }
        return null;
    }

    public static List<Product> findProducts(String name) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet set = null;
        try {
            Class.forName("org.sqlite.JDBC");
            // create a connection to the database
            conn = DriverManager.getConnection("jdbc:sqlite:db.sqlite");
            stmt = conn.prepareStatement("select *" +
                    "from products where prod_name like ?;");
            stmt.setString(1, "%" + name + "%");
            set = stmt.executeQuery();
            List<Product> list = new LinkedList<>();
            while(set.next()) {
                Product prod = new Product();
                prod.setProdId(set.getInt("prod_id"));
                prod.setProdName(set.getString("prod_name"));
                prod.setPrice(set.getDouble("price"));
                prod.setProdType(set.getString("prod_type"));
                list.add(prod);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(conn != null) try {conn.close();} catch(Exception e) {e.printStackTrace();}
            if(stmt != null) try {stmt.close();} catch(Exception e) {e.printStackTrace();}
            if(set != null) try {set.close();} catch(Exception e) {e.printStackTrace();}
        }
        return null;
    }

    public static boolean createProduct(Product prod){
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            // create a connection to the database
            conn = DriverManager.getConnection("jdbc:sqlite:db.sqlite");

            stmt = conn.prepareStatement("insert into products (prod_name, price, prod_type)\n" +
                    "values (?, ?, ?);");
            stmt.setString(1, prod.getProdName());
            stmt.setDouble(2, prod.getPrice());
            stmt.setString(3, prod.getProdType());
            stmt.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if(conn != null) try {conn.close();} catch(Exception e) {e.printStackTrace();}
            if(stmt != null) try {stmt.close();} catch(Exception e) {e.printStackTrace();}
        }
    }
    public static boolean updateProduct(Product prod) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            // create a connection to the database
            conn = DriverManager.getConnection("jdbc:sqlite:db.sqlite");
            stmt = conn.prepareStatement("update products set prod_name = ?, prod_type = ?, price = ? where prod_id = ?;");
            stmt.setString(1, prod.getProdName());
            stmt.setString(2, prod.getProdType());
            stmt.setDouble(3, prod.getPrice());
            stmt.setInt(4, prod.getProdId());
            stmt.execute();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if(conn != null) try {conn.close();} catch(Exception e) {e.printStackTrace();}
            if(stmt != null) try {stmt.close();} catch(Exception e) {e.printStackTrace();}
        }
    }
    public static boolean deleteProduct(int id){
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            // create a connection to the database
            conn = DriverManager.getConnection("jdbc:sqlite:db.sqlite");

            stmt = conn.prepareStatement("delete from products where prod_id = ?");
            stmt.setInt(1,id);
            stmt.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if(conn != null) try {conn.close();} catch(Exception e) {e.printStackTrace();}
            if(stmt != null) try {stmt.close();} catch(Exception e) {e.printStackTrace();}
        }
    }

    public static Product getProduct(int id){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet set = null;
        try {
            Class.forName("org.sqlite.JDBC");
            // create a connection to the database
            conn = DriverManager.getConnection("jdbc:sqlite:db.sqlite");

            stmt = conn.prepareStatement("select * from products where prod_id = ?");
            stmt.setInt(1,id);
            set = stmt.executeQuery();
            Product prod = null;
            if(set.next()) {
                prod = new Product();
                prod.setProdId(set.getInt("prod_id"));
                prod.setProdName(set.getString("prod_name"));
                prod.setPrice(set.getDouble("price"));
                prod.setProdType(set.getString("prod_type"));
            }
            return prod;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if(conn != null) try {conn.close();} catch(Exception e) {e.printStackTrace();}
            if(stmt != null) try {stmt.close();} catch(Exception e) {e.printStackTrace();}
            if(set != null) try {set.close();} catch(Exception e) {e.printStackTrace();}
        }
    }

    public static void main(String[] args) {
        // getAllProducts() is a static method.
        List<Product> ps = ProductDAO.getAllProducts();
        for(Product p: ps)
            System.out.println(p.getProdName());
    }
}