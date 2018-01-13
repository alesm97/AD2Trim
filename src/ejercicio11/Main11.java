package ejercicio11;

import utilidades.Teclado;

import java.sql.*;

public class Main11 {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e1) {
            System.out.println("ERROR Driver:" + e1.getMessage());
        }

        Connection connmysql = null;
        Statement sents;
        ResultSet resultado;
        String asignatura,oferta,curso;

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
        }while(asignatura.length()>5);


        //asignatura = "SGE";
        //oferta = "DAM";
        //curso = "2A";

        try {
            connmysql = DriverManager.getConnection("jdbc:mysql://localhost/horario?allowMultiQueries=true&useSSL=false", "java", "java");
            sents = connmysql.createStatement();
            resultado = sents.executeQuery(String.format("SELECT codTramo FROM horario WHERE codAsig = '%s' AND codOe = '%s' AND codCurso = '%s' ORDER BY 1",asignatura,oferta,curso));

            if(resultado.getMetaData().getColumnCount()==0){
                System.out.println("No ha habido resultados.");
            }else{
                System.out.printf("\nTramos:\n");

                while (resultado.next()) {
                    System.out.printf("%s ", resultado.getString(1));
                }
            }

            sents.close();
            connmysql.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
