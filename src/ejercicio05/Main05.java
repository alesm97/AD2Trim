package ejercicio05;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main05 {

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e1) {
            System.out.println("ERROR Driver:" + e1.getMessage());
        }

        Connection connmysql = null;
        Statement sents;

        try {
            connmysql = DriverManager.getConnection("jdbc:mysql://localhost/horario?allowMultiQueries=true&useSSL=false", "java", "java");
            sents = connmysql.createStatement();
            sents.executeUpdate("UPDATE asignatura a LEFT JOIN reparto r ON r.codAsig = a.codAsig SET hTotales = hTotales * 1.1, hSemanales = hSemanales * 1.1 WHERE a.nombre LIKE 'M%' AND r.codOe = 'FPB'");

            System.out.println("Actualización realizada con éxito.");

            sents.close();
            connmysql.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
