/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessModelLayer;

import DataAccessLayer.DataAccess;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author FOSTER
 */
public class Producto {

    private DataAccess dataAccess = DataAccess.Instance();
    private int idProducto;
    private String nombre;
    private String nombreBuscar;
    private Date caducidad;
    private int stock;
    private int idFarmacia;
    private int activo;

    public Producto() {
    }

    public Producto(int idProducto, String nombre, Date caducidad, int stock, int idFarmacia, int activo) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.caducidad = caducidad;
        this.stock = stock;
        this.idFarmacia = idFarmacia;
        this.activo = activo;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getCaducidad() {
        return caducidad;
    }

    public void setCaducidad(Date caducidad) {
        this.caducidad = caducidad;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getIdFarmacia() {
        return idFarmacia;
    }

    public void setIdFarmacia(int idFarmacia) {
        this.idFarmacia = idFarmacia;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public String getNombreBuscar() {
        return nombreBuscar;
    }

    public void setNombreBuscar(String nombreBuscar) {
        this.nombreBuscar = nombreBuscar;
    }

//El siguietne metodo muestra todos los datos de la tabla sql en un jTable.
    public DefaultTableModel GetAllModel() {
        String query = "SELECT * FROM productos";
        return dataAccess.Query(query);
    }

//El siguiente medoto pasa la fila seleccionada del jTable por medio del id a variables de la clase.
    public void GetById() {
        String query = "SELECT * FROM productos WHERE idProducto = " + idProducto;
        DefaultTableModel res = dataAccess.Query(query);
        idProducto = (int) res.getValueAt(0, 0);
        nombre = (String) res.getValueAt(0, 1);
        caducidad = (Date) res.getValueAt(0, 2);
        stock = (int) res.getValueAt(0, 3);
        idFarmacia = (int) res.getValueAt(0, 4);
        activo = (int) res.getValueAt(0, 5);
    }

//Este metodo añade datos a la base de datos.
    public boolean Add() {
        //INSERT INTO TABLA(C1, C2) VALUES(V1, V2);
        String query = "INSERT INTO productos(nombre, caducidad, stock, id_Farmacia, activo)"
                + "VALUES('" + nombre + "','" + caducidad + "'," + stock + "," + idFarmacia + ","
                + activo + ");";
        return dataAccess.Execute(query) >= 1;
    }

//Este metodo borra informacion de la base de datos
    public boolean Delete() {
        String query = "DELETE FROM productos WHERE idProducto = " + idProducto;
        return dataAccess.Execute(query) >= 1;
    }
    
    public boolean DeleteFK() {
        String query = "DELETE FROM productos WHERE id_Farmacia = " + idFarmacia;
        return dataAccess.Execute(query) >= 1;
    }

//Este metodo modifica la base de datos.
    public boolean Update() {
        //UPDATE TABLA SET c1=v2, c2=v2, c2=v3;
        String query = "UPDATE productos SET "
                + "nombre = '" + nombre + "', "
                + "caducidad = '" + caducidad + "', "
                + "stock = " + stock + ", "
                + "id_Farmacia = " + idFarmacia + ", "
                + "activo = " + activo + " "
                + "WHERE idProducto = " + idProducto;
        return dataAccess.Execute(query) >= 1;
    }

//Este metodo busca algun elemento de la base de datos, basandose en este caso en el nombre y lo muestra en el jTable.
    public DefaultTableModel Search(String nombre) {
        String query = "SELECT * FROM productos where nombre like '%" + nombre + "%'";
        return dataAccess.Query(query);
    }

//Este metodo obtiene los datos de una o mas columnas(modificando la sentencia sql) y las retorna en un arraylist.
    public ArrayList RowData() {
        String query = "SELECT idFarmacia FROM farmacias";
        return dataAccess.QueryRow(query);
    }

}
