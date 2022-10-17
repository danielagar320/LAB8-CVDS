package edu.eci.cvds.sampleprj.dao;

import edu.eci.cvds.samples.entities.TipoItem;

import java.util.List;


public interface TipoItemDAO {

    public List<TipoItem> getTiposItems() throws PersistenceException;

    public TipoItem getTipoItem(int id) throws PersistenceException;

    public void addTipoItem(String des) throws PersistenceException;
}