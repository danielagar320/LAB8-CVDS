package edu.eci.cvds.sampleprj.dao.mybatis.mappers;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import edu.eci.cvds.samples.entities.Cliente;

public interface ClienteMapper {
    
    public Cliente consultarCliente(@Param("idcli") long id);
    
    /**
     * Registrar un nuevo item rentado asociado al cliente identificado
     * con 'idc' y relacionado con el item identificado con 'idi'
     * @param id
     * @param idit
     * @param fechainicio
     * @param fechafin 
     */
	 
	 
    public void agregarItemRentadoACliente(@Param("id") long id,
        @Param("idit") int idit, 
        @Param("fechaInicio") Date fechainicio,
        @Param("fechaFin") Date fechafin);

    /**
     * Consultar todos los clientes
     * @return 
     */
    public List<Cliente> consultarClientes();

    public void insertarCliente(@Param("cliente") Cliente cl);
    
}
