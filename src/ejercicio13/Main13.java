package ejercicio13;

import utilidades.Teclado;

import java.sql.*;

public class Main13 {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e1) {
            System.out.println("ERROR Driver:" + e1.getMessage());
        }

        Connection connmysql = null;
        Statement sents;
        ResultSet resultado;
        String oferta, curso;
        PreparedStatement prepHorario = null;
        boolean desdoble = false;
        String[] tramos = {"L1", "M1", "X1", "J1", "V1", "L2", "M2", "X2", "J2", "V2", "L3", "M3", "X3", "J3", "V3", "L4", "M4", "X4", "J4", "V4", "L5", "M5", "X5", "J5", "V5", "L6", "M6", "X6", "J6", "V6"};

        /*do{
            System.out.println("¿De que oferta educativa quiere hacer la búsqueda? (máximo 3 caracteres)");
            oferta = Teclado.leerString();
        }while(oferta.length()>3);

        do{
            System.out.println("¿De que curso? (máximo 2 caracteres con formato 1A)");
            curso = Teclado.leerString();
        }while(curso.length()>2 || curso.matches("[1-9][A-Z]]"));*/

        oferta = "DAM";
        curso = "1A";

        try {
            connmysql = DriverManager.getConnection("jdbc:mysql://localhost/horario?allowMultiQueries=true&useSSL=false", "java", "java");
            sents = connmysql.createStatement();
            resultado = sents.executeQuery(String.format("SELECT codAsig FROM horario WHERE codOe = '%s' AND codCurso = '%s';", oferta, curso));

            if (resultado.getMetaData().getColumnCount() > 0) {
                prepHorario = connmysql.prepareStatement(String.format("SELECT codAsig FROM horario WHERE codOe = '%s' AND codCurso = '%s' AND codTramo = ?", oferta, curso));

                System.out.printf("\nHorario de %s %s\n\n%-13s%-13s%-13s%-13s%-13s\n\n", oferta, curso, "Lunes", "Martes", "Miércoles", "Jueves", "Viernes");

                for (int i = 1; i <= tramos.length; i++) {
                    prepHorario.setString(1, tramos[i - 1]);

                    resultado = prepHorario.executeQuery();
                    while (resultado.next()) {

                        desdoble = imprimirAsignatura(resultado.getString(1), i, desdoble);

                    }

                }

            } else {
                System.out.printf("El curso %s %s no tiene un horario asignado", oferta, curso);
            }

            prepHorario.close();
            sents.close();
            connmysql.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean imprimirAsignatura(String asig, int hora, boolean desdoble) {
        boolean res = false;
        if (!asig.matches("^@.*")) {
            if (desdoble) {
                System.out.printf("%-12s", asig);
            } else {
                System.out.printf(" %-12s", asig);
            }
        } else {
            System.out.printf("*", asig);
            res = true;
        }
        if (hora % 5 == 0 && hora != 0 && res == false) {
            System.out.println();
        }

        return res;
    }
}
