package ejercicio02;

import utilidades.Teclado;

import java.sql.*;

public class Main02 {

    public static void main(String[] args) {

        String tabla, nula;
        Connection connmysql;
        Statement sents;
        ResultSet rs, pK;
        ResultSetMetaData rsmd;
        DatabaseMetaData meta;
        int nColumnas;

        System.out.println("¿Sobre qué desea la información? (asignatura, curso, horario, ofertaeducativa, profesor, reparto, tramohorario)");
        tabla = Teclado.leerString();



        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e1) {
            System.out.println("ERROR Driver:" + e1.getMessage());
        }

        try {
            connmysql = DriverManager.getConnection("jdbc:mysql://localhost/horario?useSSL=false", "java", "java");
            sents = connmysql.createStatement();

            rs = sents.executeQuery(String.format("SELECT * FROM %s", tabla));
            rsmd = rs.getMetaData();

            System.out.println(String.format("\nInformación sobre la tabla \"%s\" :",tabla));

            nColumnas = rsmd.getColumnCount();
            System.out.printf("Número de columnas: %d\n\n", nColumnas);

            for (int i = 1; i <= nColumnas; i++) {
                System.out.printf("Columna %d: %n ", i);
                System.out.printf(" Nombre: %s %n  Tipo: %s %n ", rsmd.getColumnName(i), rsmd.getColumnTypeName(i));
                if (rsmd.isNullable(i) == 0) {
                    nula = "SI";
                } else {
                    nula = "NO";
                }

                System.out.printf(" Puede ser nula?: %s %n ", nula);
                System.out.printf(" Máximo ancho de la columna: %d \n\n", rsmd.getColumnDisplaySize(i));
            }

            meta = connmysql.getMetaData();
            pK = meta.getPrimaryKeys(null, null, tabla);

            System.out.println(String.format("\n\nClave primaria:"));
            while (pK.next()) {
                System.out.println(String.format("\t%s", pK.getString("COLUMN_NAME")));
            }

            pK = meta.getImportedKeys(null,null,tabla);

            System.out.println(String.format("\n\nClaves importadas:"));
            while (pK.next()) {
                System.out.println(String.format("\tTabla: %s | Campo: %s", pK.getString("PKTABLE_NAME"),pK.getString("FKCOLUMN_NAME")));
            }

            pK = meta.getExportedKeys(null,null,tabla);

            System.out.println(String.format("\n\nClaves exportadas:"));
            while (pK.next()) {
                System.out.println(String.format("\tTabla: %s | Campo: %s", pK.getString("FKTABLE_NAME"),pK.getString("PKCOLUMN_NAME")));
            }


            pK.close();
            rs.close();
            sents.close();
            connmysql.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
