package ejercicio17;

import utilidades.Teclado;

import java.sql.*;

public class Main17 {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e1) {
            System.out.println("ERROR Driver:" + e1.getMessage());
        }

        Connection connmysql = null;
        String oferta,curso;

        do{
            System.out.println("¿De que oferta educativa quiere hacer la búsqueda? (máximo 3 caracteres)");
            oferta = Teclado.leerString();
        }while(oferta.length()>3);

        do{
            System.out.println("¿De que curso? (máximo 2 caracteres con formato 1A)");
            curso = Teclado.leerString();
        }while(curso.length()>2 || curso.matches("[1-9][A-Z]]"));

        /*oferta = "SMR";
        curso = "2A";*/

        try {
            connmysql = DriverManager.getConnection("jdbc:mysql://localhost/horario?allowMultiQueries=true&useSSL=false", "java", "java");

            String sql= " { ? = call obtenerTutor (?, ?) } ";
            CallableStatement llamada= connmysql.prepareCall(sql);
            llamada.registerOutParameter(1,Types.VARCHAR);
            llamada.setString(2,oferta);
            llamada.setString(3,curso);

            llamada.execute();

            if (llamada.getString(1) == null) {
                System.out.println("No hay resultados.");
            } else {
                System.out.printf("El curso %s %s tiene como tutor/a a %s.",oferta,curso,llamada.getString(1));
            }


            llamada.close();
            connmysql.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
