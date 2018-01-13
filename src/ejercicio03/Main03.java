package ejercicio03;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main03 {

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
            sents.executeUpdate("INSERT INTO ofertaeducativa VALUES ('FPB','FP Básica Informática y Comunicaciones','La formación profesional básica de informática y comunicaciones tiene una duración de 2000 horas repartidas entre dos cursos académicos incluyendo 240 horas de Formación en centros de trabajo (FCT) en empresas del Sector','FPB',null)");

            sents.close();
            connmysql.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }

}
