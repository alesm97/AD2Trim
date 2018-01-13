package ejercicio04;

import java.sql.*;

public class Main04 {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e1) {
            System.out.println("ERROR Driver:" + e1.getMessage());
        }

        Connection connmysql = null;
        Statement sents;
        String prepCurso,prepAsig, prepReparto;
        PreparedStatement statement;

        try {
            connmysql = DriverManager.getConnection("jdbc:mysql://localhost/horario?allowMultiQueries=true&useSSL=false", "java", "java");
            sents = connmysql.createStatement();

            prepCurso = "INSERT INTO curso VALUES (?, ?, ?)";
            prepAsig = "INSERT INTO asignatura VALUES (?, ?, ?, ?)";
            prepReparto = "INSERT INTO reparto VALUES (?, ?, ?, ?)";

            statement = connmysql.prepareStatement(prepCurso);

            statement.setString(1,"FPB");
            statement.setString(2,"1A");
            statement.setString(3,"DAS");
            statement.executeUpdate();

            statement = connmysql.prepareStatement(prepAsig);

            statement.setString(1,"OCE");
            statement.setString(2,"Operaciones auxiliares para la configuración y la explotación");
            statement.setInt(3,7);
            statement.setInt(4,245);
            statement.executeUpdate();

            statement.setString(1,"MMI");
            statement.setString(2,"Montaje y mantenimiento de sistemas y componentes informáticos");
            statement.setInt(3,9);
            statement.setInt(4,315);
            statement.executeUpdate();

            statement = connmysql.prepareStatement(prepReparto);

            statement.setString(1,"FPB");
            statement.setString(2,"1A");
            statement.setString(3,"OCE");
            statement.setString(4,"DAS");
            statement.executeUpdate();

            statement.setString(1,"FPB");
            statement.setString(2,"1A");
            statement.setString(3,"MMI");
            statement.setString(4,"MGD");
            statement.executeUpdate();

            statement.close();
            sents.close();
            connmysql.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
