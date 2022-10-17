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
package edu.eci.cvds.samples.services.client;



import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Date;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.ClienteMapper;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.ItemMapper;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.TipoItemMapper;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.ItemRentadoMapper;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.TipoItem;

/**
 *
 * @author hcadavid
 */
public class MyBatisExample {

    /**
     * Método que construye una fábrica de sesiones de MyBatis a partir del
     * archivo de configuración ubicado en src/main/resources
     *
     * @return instancia de SQLSessionFactory
     */
    public static SqlSessionFactory getSqlSessionFactory() {
        SqlSessionFactory sqlSessionFactory = null;
        if (sqlSessionFactory == null) {
            InputStream inputStream;
            try {
                inputStream = Resources.getResourceAsStream("mybatis-config.xml");
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            } catch (IOException e) {
                throw new RuntimeException(e.getCause());
            }
        }
        return sqlSessionFactory;
    }

    /**
     * Programa principal de ejempo de uso de MyBATIS
     * @param args
     * @throws SQLException 
     */
    public static void main(String args[]) throws SQLException {
        SqlSessionFactory sessionfact = getSqlSessionFactory();
        
        //Crear el mapper y usarlo: 
        //ClienteMapper cm=sqlss.getMapper(ClienteMapper.class)
        //cm...
        
        try(SqlSession sqlss = sessionfact.openSession();){
            ClienteMapper cm=sqlss.getMapper(ClienteMapper.class);
            ItemMapper itemMapper = sqlss.getMapper(ItemMapper.class);
            TipoItemMapper tipoItemMapper = sqlss.getMapper(TipoItemMapper.class);
            ItemRentadoMapper iRMapper = sqlss.getMapper(ItemRentadoMapper.class);

            /**CONSULTAR CLIENTES */
            System.out.println("------------------------------------------------\n");
            System.out.println(cm.consultarClientes());

            /**CONSULTAR CLIENTE */
            System.out.println("------------------------------------------------\n");
            System.out.println(cm.consultarCliente(5));

            /** CONSULTAR ITEMS */
            System.out.println("------------------------------------------------\n");
            System.out.println(itemMapper.consultarItems());

            /** CONSULTAR ITEM */
            System.out.println("------------------------------------------------\n");
            System.out.println(itemMapper.consultarItem(21677088));

            /**CONSULTAR TIPOS ITEMS */
            System.out.println("------------------------------------------------\n");
            System.out.println(tipoItemMapper.getTiposItems());

            /**CONSULTAR TIPO ITEM */
            System.out.println("------------------------------------------------\n");
            System.out.println(tipoItemMapper.getTipoItem(2143447));

            /**CONSULTAR ITEMS RENTADOS */
            System.out.println("------------------------------------------------\n");
            System.out.println(iRMapper.totalItemsRentados());
            
            /** AGREGAR TIPO ITEM */
            //tipoItemMapper.addTipoItem("La Lechona Mecanica");

            //TipoItem tipo = tipoItemMapper.getTipoItem(2143447);
            //itemMapper.insertarItem(new Item(tipo,21677088, "CR7", "SIUUUUU", new Date(), 99999, "FORMATORENTA", "GOAT"));
            //cm.agregarItemRentadoACliente(1, 21677088, new Date(), new Date());

            sqlss.commit();
        
        
            sqlss.close();


        }catch(Exception e){
                e.printStackTrace();
        }        
        
    }


}
