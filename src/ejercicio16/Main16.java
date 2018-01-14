package ejercicio16;

import java.sql.*;

public class Main16 {
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
            resultado = sents.executeQuery("SELECT a.nombre, a.hSemanales, count(codCurso), count(codOe) FROM asignatura a LEFT JOIN reparto r ON a.codAsig = r.codAsig WHERE a.hSemanales >=3 GROUP BY nombre,hSemanales;");

            if (resultado.getMetaData().getColumnCount() == 0) {
                System.out.println("No hay resultados.");
            } else {

                while (resultado.next()) {
                    System.out.println(String.format("Asignatura: %s\n\tHoras semanales: %s\n\tNº cursos: %s\n\tNº ofertas %s\n\n",resultado.getString(1),resultado.getString(2),resultado.getString(3),resultado.getString(4)));
                }
            }

            sents.close();
            connmysql.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
