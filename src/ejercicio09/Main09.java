package ejercicio09;

import java.sql.*;

public class Main09 {

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e1) {
            System.out.println("ERROR Driver:" + e1.getMessage());
        }

        Connection connmysql = null;
        Statement sents;
        ResultSet resultado;
        ResultSetMetaData meta;
        int columnas;
        String nula;

        try {
            connmysql = DriverManager.getConnection("jdbc:mysql://localhost/horario?allowMultiQueries=true&useSSL=false", "java", "java");
            sents = connmysql.createStatement();
            resultado = sents.executeQuery("SELECT * FROM profesor LEFT JOIN curso ON codTutor = codProf");

            meta = resultado.getMetaData();
            columnas = meta.getColumnCount();

            for(int i = 1; i<columnas ; i++){
                System.out.printf("Columna %d: %n ", i);
                System.out.printf("\tNombre: %s %n \tTipo: %s %n ", meta.getColumnName(i), meta.getColumnTypeName(i));
                if (meta.isNullable(i) == 0){
                    nula = "Sí";
                }else{
                    nula = "No";
                }

                System.out.printf("\t¿Puede ser nula?: %s %n ", nula);
                System.out.printf("\tMáximo ancho de la columna: %d %n", meta.getColumnDisplaySize(i));
            }


            sents.close();
            connmysql.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
