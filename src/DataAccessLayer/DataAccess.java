/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author FOSTER
 */
public class DataAccess {

//se crea una variable de tipo de la clase.
    private static DataAccess instance;
// Se crea una variable del tipo Connection
    private Connection con = null;
// Se crea una variable del tipo Statement.  
    private Statement statement;
    //Se creo una variable del tipo ResultSet;
    private ResultSet resultSet;
//Constructor privado.

    private DataAccess() {
    }

//Metodo que retorna un objeto de la clase instance y se usa para instanciar solo una vez.
    public static DataAccess Instance() {
        if (instance == null) {
            instance = new DataAccess();
        }
        return instance;
    }

//Conecta a la base de datos local cada que se requiera una accion.
    public void ConectarDB() {
        try {
            //URL de conexon a mysql(para entrar a otro como SQL server, mariaDB, se ocpua otro)
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/farmacia_db?useTimezone=true&serverTimezone=America/Mexico_City",
                    "root",
                    "Divisionmblake32-");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en la conexion: " + e.getMessage());
        }
    }

// Este metodo desconecta a la base de datos cuando se requiera.
    private void DesconectarDB() {
        try {
            statement.close();
            resultSet.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en la desconexion: " + e.getMessage());
        }
    }

    /*Este metodo recibe como argumento una sentencia sql, que usa para agregar
    las columnas y los datos que hay en ellas, y los presenta en un jTable.
     */
    public DefaultTableModel Query(String query) {
        try {
            ConectarDB();
            // Se crea la estructura para poder consultar las sentencias.
            statement = con.createStatement();
            resultSet = statement.executeQuery(query);

            //En este vector guardaremos el nombre de las columnas de la tabla.
            Vector<String> columnNames = new Vector<String>();

            //resultset contiene el numero de columnas que tiene la tabla sql.
            int columnCout = resultSet.getMetaData().getColumnCount();

            //Con este for iteramos el numero de columnas y guardamos el nombre de cada una.
            for (int column = 1; column <= columnCout; column++) {
                columnNames.add(resultSet.getMetaData().getColumnName(column));
            }

            /*El siguiente Vector al parecer es un Vector multidimensional, pero en este caso
            no se porque el profe lo puso asi, por que tambien con el vector comentado en 
            la siguiente linea funciona y es un Vector unidimensional. pero en si este Vector
            guarda los datos que hay en cada fila de la tabla, dependiento de la sentencia sql mandada 
            por argumento al metodo.
           
             */
            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            //Vector<Vector> data = new Vector<Vector>();

            /*resultset.next() mueve el puntero del objeto actual(resultset) a 
            la fila siguiente, si el objeto resulset ya no tiene mas filas obtiene
            un booleano false y sale del while.
             */
            while (resultSet.next()) {
                Vector<Object> vector = new Vector<Object>();
                for (int columnIndex = 1; columnIndex <= columnCout; columnIndex++) {
                    vector.add(resultSet.getObject(columnIndex));
                }
                data.add(vector);
                //System.out.println(data);
            }
            return new DefaultTableModel(data, columnNames);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en la consulta: " + e.getMessage());
            return null;
        } finally {
            DesconectarDB();
        }
    }

    /* Este metodo recibe como argumento una sentencia sql(INSERT, DELETE, UPDATE)
    para modificar la tabla de sql.
     */
    public int Execute(String query) {
        try {
            ConectarDB();
            statement = con.createStatement();

            //Devuelve el valor de filas afectadas.
            return statement.executeUpdate(query);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en el comando: " + e.getMessage());
            return 0;
        } finally {
            DesconectarDB();
        }
    }

    /*Este metodo se utiliza para guardar en un arraylist los datos
    que se encuentran en las filas de una columna o mas columnas de la tabla sql
      dependiendo de la sentencia.
     */
    public ArrayList QueryRow(String query) {

        try {
            ConectarDB();
            statement = con.createStatement();
            resultSet = statement.executeQuery(query);
            int columnCout = resultSet.getMetaData().getColumnCount();

            ArrayList lista = new ArrayList();
            while (resultSet.next()) {
                for (int columnIndex = 1; columnIndex <= columnCout; columnIndex++) {
                    lista.add(resultSet.getObject(columnIndex));
                }
            }
            return lista;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en la consulta: " + e.getMessage());
            return null;
        } finally {
            DesconectarDB();
        }
    }
}
