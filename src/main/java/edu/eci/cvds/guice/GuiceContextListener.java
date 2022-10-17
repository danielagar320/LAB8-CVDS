package edu.eci.cvds.guice;
import javax.servlet.ServletContext; 
import javax.servlet.ServletContextEvent; 
import javax.servlet.ServletContextListener; 
import org.mybatis.guice.XMLMyBatisModule; 
import org.mybatis.guice.datasource.helper.JdbcHelper; 

import edu.eci.cvds.sampleprj.dao.*;
import edu.eci.cvds.sampleprj.dao.mybatis.*;
import edu.eci.cvds.samples.services.ServiciosAlquiler;
import edu.eci.cvds.samples.services.impl.ServiciosAlquilerItemsImpl;


import com.google.inject.Guice; 
import com.google.inject.Injector; 

public class GuiceContextListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		ServletContext servletContext = servletContextEvent.getServletContext();
		servletContext.removeAttribute(Injector.class.getName());
 	}

 	public void contextInitialized(ServletContextEvent servletContextEvent) {
 		Injector injector = Guice.createInjector(new XMLMyBatisModule() {
 			@Override 
			protected void initialize() { 
				install(JdbcHelper.MySQL);
				setEnvironmentId("development");

				setClassPathResource("mybatis-config.xml");
 				// TODO Add service class associated to Stub implementation
                		bind(ItemDAO.class).to(MyBATISItemDAO.class);
                		bind(TipoItemDAO.class).to(MyBATISTipoItemDAO.class);
                		bind(ClienteDAO.class).to(MyBATISClienteDAO.class);
                		bind(ItemRentadoDAO.class).to(MyBATISItemRentadoDAO.class);
                		bind(ServiciosAlquiler.class).to(ServiciosAlquilerItemsImpl.class);
 			}
		});

 		servletContextEvent.getServletContext().setAttribute(Injector.class.getName(), injector);
	 }
}
