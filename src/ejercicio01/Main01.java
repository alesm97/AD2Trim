package ejercicio01;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main01 {

    public static void main(String[] args) {

        File scriptFile = new File("C:\\Users\\alesm97\\Google Drive\\Desarrollo de aplicaciones\\Segundo\\Acc Datos\\BDD\\SQL\\BDD Horario to pepino.sql");

        BufferedReader entrada = null;

        try {
            entrada = new BufferedReader(new FileReader(scriptFile));
        } catch (FileNotFoundException e) {
            System.out.println("ERROR NO HAY FILE: " + e.getMessage());
        }

        String linea = null;
        StringBuilder stringBuilder = new StringBuilder();
        String salto = System.getProperty("line.separator");

        try {
            while ((linea = entrada.readLine()) != null) {
                stringBuilder.append(linea);
                stringBuilder.append(salto);
            }
        } catch (IOException e) {
            System.out.println("ERROR de E/S, al operar" +
                    e.getMessage());

        }

        String consulta = stringBuilder.toString();
        //System.out.println(consulta);

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e1) {
            System.out.println("ERROR Driver:" + e1.getMessage());
        }

        try {
            Connection connmysql = DriverManager.getConnection("jdbc:mysql://localhost/horario?allowMultiQueries=true&useSSL=false", "java", "java");
            Statement sents = connmysql.createStatement();
            System.out.println("Ejecutando script...");
            sents.executeUpdate(consulta);
            System.out.println("Script ejecutado con éxito.");
            connmysql.close();
            sents.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
