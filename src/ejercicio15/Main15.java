package ejercicio15;

import utilidades.Teclado;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Main15 {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e1) {
            System.out.println("ERROR Driver:" + e1.getMessage());
        }

        Connection connmysql = null;
        Statement sents;
        ResultSet resultado;
        String prof, tramo = null;

        do{
            System.out.println("¿De que profesor quiere hacer la búsqueda? (máximo 3 caracteres)");
            prof = Teclado.leerString();
        }while(prof.length()>3);


        /*prof = "DQS";*/

        try {
            connmysql = DriverManager.getConnection("jdbc:mysql://localhost/horario?allowMultiQueries=true&useSSL=false", "java", "java");
            sents = connmysql.createStatement();
            tramo = obtenerTramo();

            if (tramo.matches("finde")) {
                System.out.println("No hay clases, es finde.");
            } else if (tramo.matches("[LMXJV]recreo")) {
                System.out.println("Es el recreo.");
            } else if (tramo.matches("[LMXJV]0")) {
                System.out.println("No es tiempo para estar en clase ¡Sal a la calle!");
            } else {
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
            }

            sents.close();
            connmysql.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String obtenerTramo() {
        String dia = null, hora = null, res = null;

        LocalDateTime fecha = LocalDateTime.now();
        if (fecha.getDayOfWeek() == DayOfWeek.SATURDAY || fecha.getDayOfWeek() == DayOfWeek.SUNDAY) {
            res = "finde";
        } else if (fecha.getDayOfWeek() == DayOfWeek.MONDAY) {
            dia = "L";
            hora = obtenerHora();
            res.concat(dia);
            res.concat(hora);
        } else if (fecha.getDayOfWeek() == DayOfWeek.TUESDAY) {
            dia = "M";
            hora = obtenerHora();
            res.concat(dia);
            res.concat(hora);
        } else if (fecha.getDayOfWeek() == DayOfWeek.WEDNESDAY) {
            dia = "X";
            hora = obtenerHora();
            res.concat(dia);
            res.concat(hora);
        } else if (fecha.getDayOfWeek() == DayOfWeek.THURSDAY) {
            dia = "J";
            hora = obtenerHora();
            res.concat(dia);
            res.concat(hora);
        } else if (fecha.getDayOfWeek() == DayOfWeek.FRIDAY) {
            dia = "V";
            hora = obtenerHora();
            res.concat(dia);
            res.concat(hora);
        }


        return res;
    }

    private static String obtenerHora() {
        LocalTime fecha = LocalTime.now();
        String hora = null;
        LocalTime inicio = LocalTime.of(8, 15);
        LocalTime primeraHora = LocalTime.of(9, 15);
        LocalTime segundaHora = LocalTime.of(10, 15);
        LocalTime terceraHora = LocalTime.of(11, 15);
        LocalTime recreo = LocalTime.of(11, 45);
        LocalTime cuartaHora = LocalTime.of(12, 45);
        LocalTime quintaHora = LocalTime.of(13, 45);
        LocalTime sextaHora = LocalTime.of(14, 45);

        if (fecha.isBefore(inicio) || fecha.isAfter(sextaHora)) {
            hora = "0";
        } else if (fecha.isAfter(inicio) && fecha.isBefore(primeraHora)) {
            hora = "1";
        } else if (fecha.isAfter(primeraHora) && fecha.isBefore(segundaHora)) {
            hora = "2";
        } else if (fecha.isAfter(segundaHora) && fecha.isBefore(terceraHora)) {
            hora = "3";
        } else if (fecha.isAfter(terceraHora) && fecha.isBefore(recreo)) {
            hora = "recreo";
        } else if (fecha.isAfter(recreo) && fecha.isBefore(cuartaHora)) {
            hora = "4";
        } else if (fecha.isAfter(cuartaHora) && fecha.isBefore(quintaHora)) {
            hora = "5";
        } else if (fecha.isAfter(quintaHora) && fecha.isBefore(sextaHora)) {
            hora = "6";
        }

        return hora;
    }
}
