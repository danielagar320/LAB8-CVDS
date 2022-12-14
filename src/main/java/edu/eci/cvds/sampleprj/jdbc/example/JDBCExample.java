/*
 * Copyright (C) 2015 hcadavid
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.eci.cvds.sampleprj.jdbc.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hcadavid
 */
public class JDBCExample {

    public static void main(String args[]){
        try {
            String url="jdbc:mysql://desarrollo.is.escuelaing.edu.co:3306/bdprueba";
            String driver="com.mysql.jdbc.Driver";
            String user="bdprueba";
            String pwd="prueba2019";

            Class.forName(driver);
            Connection con=DriverManager.getConnection(url,user,pwd);
            con.setAutoCommit(false);


            System.out.println("Valor total pedido 1:"+valorTotalPedido(con, 1));

            List<String> prodsPedido=nombresProductosPedido(con, 1);


            System.out.println("Productos del pedido 1:");
            System.out.println("-----------------------");
            for (String nomprod:prodsPedido){
                System.out.println(nomprod);
            }
            System.out.println("-----------------------");


            int suCodigoECI=2165481;
            registrarNuevoProducto(con, suCodigoECI, "Gabriela", 1000000);
            con.commit();


            con.close();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(JDBCExample.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Agregar un nuevo producto con los par??metros dados
     * @param con la conexi??n JDBC
     * @param codigo
     * @param nombre
     * @param precio
     * @throws SQLException
     */
    public static void registrarNuevoProducto(Connection con, int codigo, String nombre,int precio) throws SQLException{
        String createProduct = "INSERT INTO ORD_PRODUCTOS VALUES(?,?,?)";
        //Crear preparedStatement
        try(PreparedStatement statement = con.prepareStatement(createProduct)) {
            //Asignar par??metros
            statement.setInt(1, codigo);
            statement.setString(2, nombre);
            statement.setInt(3, precio);
            //usar 'execute'
            statement.execute();
            con.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

        con.commit();

    }

    /**
     * Consultar los nombres de los productos asociados a un pedido
     * @param con la conexi??n JDBC
     * @param codigoPedido el c??digo del pedido
     * @return
     */
    public static List<String> nombresProductosPedido(Connection con, int codigoPedido){
        List<String> np=new LinkedList<>();
        String query = "SELECT * from ORD_PRODUCTOS join ORD_DETALLE_PEDIDO ON (ORD_DETALLE_PEDIDO.pedido_fk = ORD_PRODUCTOS.codigo) WHERE ORD_PRODUCTOS.codigo = ?";
        //Crear prepared statement
        try (PreparedStatement nombresPedidos = con.prepareStatement(query)) {
            //asignar par??metros
            nombresPedidos.setInt(1, codigoPedido);
            //usar executeQuery
            ResultSet rs = nombresPedidos.executeQuery();
            //Sacar resultados del ResultSet
            while(rs.next()){
                String name = rs.getString("nombre");
                //Llenar la lista y retornarla
                np.add(name);
            }
        } catch(SQLException e){
            System.out.println("El dato ya existe en la base de datos");
        }
        return np;
    }


    /**
     * Calcular el costo total de un pedido
     * @param con
     * @param codigoPedido c??digo del pedido cuyo total se calcular??
     * @return el costo total del pedido (suma de: cantidades*precios)
     */
    public static int valorTotalPedido(Connection con, int codigoPedido){
        int value = 0;
        String query = "SELECT SUM(ORD_PRODUCTOS.precio * ORD_DETALLE_PEDIDO.cantidad) AS res FROM ORD_DETALLE_PEDIDO JOIN ORD_PRODUCTOS ON ORD_PRODUCTOS.codigo = ORD_DETALLE_PEDIDO.producto_fk WHERE ORD_DETALLE_PEDIDO.pedido_fk = ?";

        //Crear prepared statement
        try(PreparedStatement statement = con.prepareStatement(query)){
            //asignar par??metros
            statement.setInt(1, codigoPedido);
            //usar executeQuery
            ResultSet res = statement.executeQuery();
            //Sacar resultado del ResultSet
            while(res.next()){
                value = res.getInt("res");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }
}