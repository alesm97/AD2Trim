package ejercicio12;

import utilidades.Teclado;

import java.sql.*;

public class Main12 {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e1) {
            System.out.println("ERROR Driver:" + e1.getMessage());
        }

        Connection connmysql = null;
        Statement sents;
        ResultSet resultado;
        String profesor;

        do{
            System.out.println("¿De que profesor quiere hacer la búsqueda? (máximo 3 caracteres)");
            profesor = Teclado.leerString();
        }while(profesor.length()>3);

        try {
            connmysql = DriverManager.getConnection("jdbc:mysql://localhost/horario?allowMultiQueries=true&useSSL=false", "java", "java");
            sents = connmysql.createStatement();
            resultado = sents.executeQuery(String.format(String.format("SELECT a.nombre FROM asignatura AS a JOIN reparto AS r ON r.codAsig = a.codAsig JOIN profesor AS p ON r.codProf = p.codProf WHERE r.codProf = '%s'",profesor)));

            if(resultado.getMetaData().getColumnCount()==0){
                System.out.println("No ha habido resultados.");
            }else{
                System.out.printf("\nAsignaturas:\n");

                while (resultado.next()) {
                    System.out.printf("\t%s\n", resultado.getString(1));
                }
            }

            sents.close();
            connmysql.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
