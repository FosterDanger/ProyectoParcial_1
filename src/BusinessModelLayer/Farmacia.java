/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessModelLayer;

import DataAccessLayer.DataAccess;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author FOSTER
 */
public class Farmacia {

    private DataAccess dataAccess = DataAccess.Instance();
    private int idFarmacia;
    private String nombre;
    private String ciudad;
    private String colonia;
    private String calle;
    private String telefono;
    private int activo;

    public Farmacia() {
    }

    public Farmacia(int idFarmacia, String nombre, String ciudad, String colonia, String calle, String telefono, int activo) {
        this.idFarmacia = idFarmacia;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.colonia = colonia;
        this.calle = calle;
        this.telefono = telefono;
        this.activo = activo;
    }

    public DataAccess getDataAccess() {
        return dataAccess;
    }

    public void setDataAccess(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public int getIdFarmacia() {
        return idFarmacia;
    }

    public void setIdFarmacia(int idFarmacia) {
        this.idFarmacia = idFarmacia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public DefaultTableModel GetAllModel() {
        String query = "SELECT * FROM farmacias";
        return dataAccess.Query(query);
    }

    public void GetById() {
        String query = "SELECT * FROM farmacias WHERE idFarmacia = " + idFarmacia;
        DefaultTableModel res = dataAccess.Query(query);
        idFarmacia = (int) res.getValueAt(0, 0);
        nombre = (String) res.getValueAt(0, 1);
        ciudad = (String) res.getValueAt(0, 2);
        colonia = (String) res.getValueAt(0, 3);
        calle = (String) res.getValueAt(0, 4);
        telefono = (String) res.getValueAt(0, 5);
        activo = (int) res.getValueAt(0, 6);
    }

    public boolean Add() {
        String query = "INSERT INTO farmacias(nombre, ciudad, colonia, calle, telefono, activo)"
                + "VALUES('" + nombre + "','" + ciudad + "','" + colonia + "','" + calle + "','"
                + telefono + "'," + activo + ");";
        return dataAccess.Execute(query) >= 1;
    }

    public boolean Delete() {
        String query = "DELETE FROM farmacias WHERE idFarmacia = " + idFarmacia;
        return dataAccess.Execute(query) >= 1;
    }

    public boolean Update() {
        String query = "UPDATE farmacias SET "
                + "nombre = '" + nombre + "', "
                + "ciudad = '" + ciudad + "', "
                + "colonia = '" + colonia + "', "
                + "calle = '" + calle + "', "
                + "telefono = '" + telefono + "', "
                + "activo = " + activo + " "
                + "WHERE idFarmacia = " + idFarmacia;
        return dataAccess.Execute(query) >= 1;
    }

    public DefaultTableModel Search(String nombre) {
        String query = "SELECT * FROM farmacias where nombre like '%" + nombre + "%'";
        return dataAccess.Query(query);
    }
    
    public ArrayList RowData() {
        String query = "SELECT id_Farmacia FROM productos";
        return dataAccess.QueryRow(query);
    }


}
