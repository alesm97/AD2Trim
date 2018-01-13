package ejercicio08;

import java.sql.*;

public class Main08 {

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
            resultado = sents.executeQuery("SELECT * FROM profesor LEFT JOIN curso ON codTutor = codProf");

            System.out.println("PROFESORES ORDENADOS Y TUTORÍA\n");
            System.out.printf("%-10s %-20s %-20s %-25s %-15s %-15s\n","Código","Nombre","Apellidos","Fecha Alta","Código Oferta","Código Curso");

            while(resultado.next()){
                if(resultado.getString("codOe") == null){
                    System.out.printf("%-10s %-20s %-20s %-25s %-10s\n",resultado.getString(1),resultado.getString(2),resultado.getString(3),resultado.getString(4),"No es tutor");
                }else{
                    System.out.printf("%-10s %-20s %-20s %-25s %-15s %-15s\n",resultado.getString(1),resultado.getString(2),resultado.getString(3),resultado.getString(4),resultado.getString(5),resultado.getString(6));
                }
            }

            sents.close();
            connmysql.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
