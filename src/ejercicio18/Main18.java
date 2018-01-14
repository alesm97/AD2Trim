package ejercicio18;

import utilidades.Teclado;

import java.sql.*;

public class Main18 {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e1) {
            System.out.println("ERROR Driver:" + e1.getMessage());
        }

        Connection connmysql = null;
        String oferta,curso,asignatura;


        do{
            System.out.println("¿De que oferta educativa quiere hacer la búsqueda? (máximo 3 caracteres)");
            oferta = Teclado.leerString();
        }while(oferta.length()>3);

        do{
            System.out.println("¿De que curso? (máximo 2 caracteres con formato 1A)");
            curso = Teclado.leerString();
        }while(curso.length()>2 || curso.matches("[1-9][A-Z]]"));

        do{
            System.out.println("¿De que asignatura? (máximo 5 caracteres, desdobles empiezan por @)");
            asignatura = Teclado.leerString();
        }while(curso.length()>5);

        /*oferta = "DAM";
        curso = "2A";
        asignatura = "DI";*/

        try {
            connmysql = DriverManager.getConnection("jdbc:mysql://localhost/horario?allowMultiQueries=true&useSSL=false", "java", "java");

            String sql= " {call datosAsig (?, ?, ?, ?, ?) } ";
            CallableStatement llamada= connmysql.prepareCall(sql);
            llamada.setString(1,oferta);
            llamada.setString(2,curso);
            llamada.setString(3,asignatura);
            llamada.registerOutParameter(4,Types.INTEGER);
            llamada.registerOutParameter(5,Types.VARCHAR);

            llamada.executeUpdate();

            if (llamada.getString(5) == null) {
                System.out.println("No hay resultados.");
            } else {
                System.out.printf("La asignatura %s del curso %s %s tiene como profesor a %s y un total de %d horas.",oferta,curso,asignatura,llamada.getString(5),llamada.getInt(4));
            }


            llamada.close();
            connmysql.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
