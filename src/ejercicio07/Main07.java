package ejercicio07;

import java.sql.*;

public class Main07 {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e1) {
            System.out.println("ERROR Driver:" + e1.getMessage());
        }

        Connection connmysql = null;
        Statement sents;
        ResultSet resultado;

        try {
            connmysql = DriverManager.getConnection("jdbc:mysql://localhost/horario?allowMultiQueries=true&useSSL=false", "java", "java");
            sents = connmysql.createStatement();
            resultado = sents.executeQuery("SELECT * FROM profesor ORDER BY 3");

            System.out.println("PROFESORES ORDENADOS POR APELLIDO\n");
            System.out.printf("%-10s %-20s %-20s %-25s\n","Código","Nombre","Apellidos","Fecha Alta");
            while(resultado.next()){
                System.out.printf("%-10s %-20s %-20s %-20s\n",resultado.getString(1),resultado.getString(2),resultado.getString(3),resultado.getString(4));
            }

            resultado = sents.executeQuery("SELECT * FROM profesor ORDER BY 4");

            System.out.println("\nPROFESORES ORDENADOS POR FECHA DE ALTA\n");
            System.out.printf("%-10s %-20s %-20s %-25s\n","Código","Nombre","Apellidos","Fecha Alta");
            while(resultado.next()){
                System.out.printf("%-10s %-20s %-20s %-20s\n",resultado.getString(1),resultado.getString(2),resultado.getString(3),resultado.getString(4));
            }

            sents.close();
            connmysql.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
