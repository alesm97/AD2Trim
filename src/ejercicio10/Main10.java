package ejercicio10;

import java.sql.*;

public class Main10 {
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
            resultado = sents.executeQuery("SELECT o.nombre, c.codCurso, p.nombre FROM curso AS c LEFT JOIN profesor AS p ON codTutor = codProf JOIN ofertaeducativa AS o ON c.codOe = o.codOe");

            System.out.println("Listado de cursos\n");
            System.out.printf("%-75s %-20s %-20s\n","Nombre de la oferta","Código del curso","Nombre del profesor");

            while(resultado.next()){
                System.out.printf("%-75s %-20s %-20s\n",resultado.getString(1),resultado.getString(2),resultado.getString(3));
            }

            sents.close();
            connmysql.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
