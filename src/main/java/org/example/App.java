package org.example;
import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    Scanner scn = new Scanner(System.in);
    public void findRest(String location, Statement statement) throws SQLException {
        String query = String.format("SELECT res_name FROM restaurant WHERE res_location = '%S'", location);

//        String query = "SELECT res_name FROM restaurant WHERE res_location IN ("+location+ ")";
        System.out.println(query);
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()){
            System.out.println(resultSet.getString(1));
        }
    }
    public void loadMenu(String res, Statement statement) throws SQLException {

//        String getResidQuery = String.format("SELECT res_id FROM restaurant WHERE res_name = '%s' ", res);

        String Query = String.format("SELECT food_item FROM menu WHERE res_id IN (SELECT res_id FROM restaurant WHERE res_name = '%s' )", res);

        ResultSet resultSet = statement.executeQuery(Query);
        while (resultSet.next()){
            System.out.println(resultSet.getString(1));
        }
    }
    public void placeOrder(int user_id, Statement statement) throws  SQLException {
        String query = String.format("select * from menu join restaurant on menu.res_id = restaurant.res_id");

        boolean selected = false;
        int choice=0;
        while (!selected){
            int index = 1;
            System.out.println("Enter your choice: ");
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                System.out.println(index + ") " +resultSet.getString(2)+ "   " + resultSet.getString(5)+ "   "+ resultSet.getString(6));
                index++;
            }
            choice = scn.nextInt();
            if(0< choice && choice < index){
                selected = true;
                break;
            }else{
                System.out.println("enter valid choice");
            }
        }

        int resindex = 1;
        ResultSet resultSet = statement.executeQuery(query);
        while(resultSet.next()){
            if (resindex == choice){
                String addQuery = String.format("INSERT INTO orders (customer_id, res_id, menu_id) VALUES (%S, %S, %S)",user_id, resultSet.getInt(4), resultSet.getInt(1));
                System.out.println(addQuery);
                int result = statement.executeUpdate(addQuery);
                break;
                //add to db
            }
            resindex++;
        }

    }
    public void orderHistory(int user_id, Statement statement) throws SQLException{
        String query = String.format("select * from menu join restaurant on menu.res_id = restaurant.res_id\n" +
                " where menu.res_id IN(SELECT res_id FROM orders WHERE customer_id=%s)\n" +
                " AND menu.menu_id IN(select menu_id FROM orders where customer_id=%s)\n", user_id, user_id);
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()){
            System.out.println(resultSet.getString(2)+ " " + resultSet.getString(5)+ " " +resultSet.getString(6));
        }
    }
    public static void main( String[] args ) throws ClassNotFoundException, SQLException {

        App app = new App();

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/food_delivery", "root", "pass@word1");

        Statement statement = connection.createStatement();

//        app.findRest("Angamally", statement);
//        app.loadMenu("res 1", statement);
//        app.placeOrder(1, statement);
        app.orderHistory(1, statement);





//        ResultSet resultSet = statement.executeQuery("SELECT * FROM menu");
////        resultSet.absolute(2);
//        while (resultSet.next()){
//            System.out.println(resultSet.getString(2));
//        }


    }
}
