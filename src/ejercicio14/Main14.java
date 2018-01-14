package ejercicio14;

import utilidades.Teclado;

import java.sql.*;

public class Main14 {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e1) {
            System.out.println("ERROR Driver:" + e1.getMessage());
        }

        Connection connmysql = null;
        Statement sents;
        ResultSet resultado;
        String prof, tramo;

        /*do{
            System.out.println("¿De que profesor quiere hacer la búsqueda? (máximo 3 caracteres)");
            prof = Teclado.leerString();
        }while(prof.length()>3);*/

        /*do{
            System.out.println("¿De que tramo horario? (máximo 2 caracteres formato L1-V6)");
            tramo = Teclado.leerString();
        }while(prof.length()>2 && !tramo.matches("[LMXJV][1-6]"));*/


        prof = "DQS";
        tramo = "L1";

        try {
            connmysql = DriverManager.getConnection("jdbc:mysql://localhost/horario?allowMultiQueries=true&useSSL=false", "java", "java");
            sents = connmysql.createStatement();
            resultado = sents.executeQuery(String.format("SELECT h.codOe, h.codCurso FROM horario h INNER JOIN reparto r ON h.codAsig=r.codAsig WHERE codProf='%s' and codTramo='%s'", prof, tramo));

            if (resultado.getMetaData().getColumnCount() == 0) {
                System.out.println("No hay resultados.");
            } else {
                System.out.printf("\nClase en la que se encuentra el profesor:\n");

                while (resultado.next()) {
                    System.out.printf("%s ", resultado.getString(1));
                    System.out.printf("%s\n", resultado.getString(2));
                }
            }

            sents.close();
            connmysql.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
