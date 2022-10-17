package edu.eci.cvds.test;
import java.util.ArrayList;
import java.util.List;
import com.google.inject.Inject;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.samples.entities.Cliente; 
import edu.eci.cvds.samples.entities.ItemRentado;
import edu.eci.cvds.samples.services.ExcepcionServiciosAlquiler;
import edu.eci.cvds.samples.services.ServiciosAlquiler; 
import edu.eci.cvds.samples.services.ServiciosAlquilerFactory; 
import org.apache.ibatis.session.SqlSession; 
import org.junit.Before;
import org.junit.Test; 
import org.junit.Assert; 


public class ServiciosAlquilerTest {
	@Inject
 	private SqlSession sqlSession;
	ServiciosAlquiler serviciosAlquiler;
 
	public ServiciosAlquilerTest() {
 		serviciosAlquiler = ServiciosAlquilerFactory.getInstance().getServiciosAlquilerTesting();
 	}

	@Before
 	public void setUp() {
	}

 	@Test
 	public void emptyDB() {
		for(int i = 0; i < 100; i += 10){ 
			boolean r = false;
			try {
 				Cliente cliente = serviciosAlquiler.consultarCliente(documento);
 			} catch(ExcepcionServiciosAlquiler e) {
				r = true;
 			} catch(IndexOutOfBoundsException e) {
				r = true;
 			}
 			// Validate no Client was found;
 			Assert.assertTrue(r);
 		};
 	}


    @Test
    public void shouldRegisterClient(){
        try{
            Cliente cl = new Cliente("Daniela", 1003752635 ,"8017789963", "Groove Street ", "daniela@gmail.com");
            serviciosAlquiler.registrarCliente(cl);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void shouldRegisterItem(){
        try {
            Item item = new Item(new TipoItem(76, "Bogota" ),150,
                    "test", "test", new SimpleDateFormat("yyyy/MM/dd").parse("2022/7/4"),
                    100,"test","test");
            serviciosAlquiler.registrarItem(item);
            Assert.assertEquals(150, serviciosAlquiler.consultarItem(150).getId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void shouldGiveRentCost() throws ExcepcionServiciosAlquiler{
        try {
            Item item = new Item(new TipoItem(2165481, "KFC" ),6,
                    "CR7", "GOAT", new SimpleDateFormat("yyyy/MM/dd").parse("2022/10/17"),
                    1000,"test","test");
            serviciosAlquiler.registrarItem(item);
            Assert.assertEquals(1000*10, serviciosAlquiler.consultarCostoAlquiler(6, 10));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void shouldConsultClient(){
        try{
            Cliente cl = new Cliente("Daniela", 1000532111 ,"1003752635", "Groove Street ", "daniela@gmail.com");
            serviciosAlquiler.registrarCliente(cl);
            Cliente consulta = serviciosAlquiler.consultarCliente(cl.getDocumento());
            Assert.assertTrue(cl.getDocumento() == consulta.getDocumento());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void shouldUpdateItemRate(){
        try {
            Item item = new Item(new TipoItem(2165481, "KFC" ),6,
                    "CR7", "GOAT", new SimpleDateFormat("yyyy/MM/dd").parse("2022/10/17"),
                    1000,"test","test");
            serviciosAlquiler.registrarItem(item);
            serviciosAlquiler.actualizarTarifaItem(6, 999999);
            Assert.assertEquals(999999, serviciosAlquiler.consultarItem(6).getTarifaxDia());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }



}
