/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.Date;
import java.util.List;
import javax.persistence.Persistence;
import model.AssociationUserEvent;
import model.Conference;
import model.Event;
import model.Utilisateur;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author sheep
 */
public class DAOControllerTest {
    
    private static DAOController dao;
    private static DAOController testDao;

    public DAOControllerTest() {
    }
    
    @BeforeClass
    public static void oneTimeSetUp() {
        dao = new DAOController();
        testDao = new DAOController();
    }
    
    @AfterClass
    public static void oneTimeTearDown() {
//        testDao.getEm().close();
    }
    
    @Before
    public void setUp(){
        testDao.setEm(Persistence.createEntityManagerFactory("multimif").createEntityManager());
    }
    
    @After
    public void tearDown(){
        testDao.getEm().close();
    }
    
    /**
     * Test of getAllUser method, of class DAOController.
     */
    @Test
    public void testGetAllUser() {
        List<Utilisateur> res = dao.getAllUser();

        testDao.getEm().getTransaction().begin();
        List<Utilisateur> expectedRes = testDao.getEm()
                                            .createNativeQuery("SELECT * FROM Utilisateur", Utilisateur.class)
                                            .getResultList();
        assertEquals(expectedRes.size(), res.size());
        for(int i = 0; i < res.size(); i++){
            assertEquals(expectedRes.get(i), res.get(i));
        }     
    }

    /**
     * Test of getAllConf method, of class DAOController.
     */
    @Test
    public void testGetAllConf() {
        List<Conference> res = dao.getAllConf();

        testDao.getEm().getTransaction().begin();
        List<Conference> expectedRes = testDao.getEm()
                                            .createNativeQuery("SELECT * FROM Conference c", Conference.class)
                                            .getResultList();
        
        assertEquals(expectedRes.size(), res.size());
        for(int i = 0; i < res.size(); i++){
            assertEquals(expectedRes.get(i), res.get(i));
        }
    }
    
    /**
     * Test of getAllEvent method, of class DAOController.
     */
    @Test
    public void testGetAllEvent() {
        List<Event> res = dao.getAllEvent();

        testDao.getEm().getTransaction().begin();
        List<Event> expectedRes = testDao.getEm()
                                            .createNativeQuery("SELECT * FROM Event e", Event.class)
                                            .getResultList();
        
        assertEquals(expectedRes.size(), res.size());
        for(int i = 0; i < res.size(); i++){
            assertEquals(expectedRes.get(i), res.get(i));
        }
    }

    /**
     * Test of getAllEvent method, of class DAOController.
     */
    @Test
    public void testGetAllAssoc() {
        List<AssociationUserEvent> res = dao.getAllAssoc();

        testDao.getEm().getTransaction().begin();
        List<AssociationUserEvent> expectedRes = testDao.getEm()
                                            .createNativeQuery("SELECT * FROM AssociationUserEvent a", AssociationUserEvent.class)
                                            .getResultList();
        
        assertEquals(expectedRes.size(), res.size());
        for(int i = 0; i < res.size(); i++){
            assertEquals(expectedRes.get(i), res.get(i));
        }
    }
    
    /**
     * Test of getUserByMail method, of class DAOController.
     */
    @Test
    public void testGetUserByMail() {
        Utilisateur res = dao.getUserByMail("test@gmail.com");     
        Utilisateur expectedRes = (Utilisateur) testDao.getEm()
                                    .createNativeQuery("SELECT * FROM Utilisateur u WHERE u.mail LIKE 'test@gmail.com'", Utilisateur.class)
                                    .getSingleResult();     
        assertEquals(expectedRes.getMail(), res.getMail());
        assertEquals(expectedRes, res);
    }

    /**
     * Test of getUserByPseudo method, of class DAOController.
     */
    @Test
    public void testGetUserByPseudo() {
        List<Utilisateur> res = dao.getUserByPseudo("pseudoTest");            
        List<Utilisateur> expectedRes = (List<Utilisateur>) testDao.getEm()
                                    .createNativeQuery("SELECT * FROM Utilisateur u WHERE u.pseudo LIKE \"pseudoTest\"", Utilisateur.class)
                                    .getResultList();
        
        assertEquals(expectedRes, res);
        for(int i = 0; i < res.size(); i++){
            assertEquals(expectedRes.get(i), res.get(i));
        }
    }

    /**
     * Test of getUserById method, of class DAOController.
     */
    @Test
    public void testGetUserById() {
        Utilisateur res = dao.getUserById("1");
        
        testDao.getEm().getTransaction().begin();
        
        Utilisateur expectedRes = (Utilisateur) testDao.getEm()
                                    .createNativeQuery("SELECT * FROM Utilisateur u WHERE u.id = 1", Utilisateur.class)
                                    .getSingleResult();
        
        assertEquals(expectedRes.getId(), res.getId());
        assertEquals(expectedRes, res);
    }

    /**
     * Test of getConfByNom method, of class DAOController.
     */
    @Test
    public void testGetConfByNom() {
        List<Conference> res = dao.getConfByNom("nomTest");    
        testDao.getEm().getTransaction().begin();
        
        List<Conference> expectedRes = (List<Conference>) testDao.getEm()
                                    .createNativeQuery("SELECT * FROM Conference c WHERE c.nom LIKE \"nomTest\"", Conference.class)
                                    .getResultList();
        
        assertEquals(expectedRes, res);
        for(int i = 0; i < res.size(); i++){
            assertEquals(expectedRes.get(i), res.get(i));
        }
    }

    
    /**
     * Test of getAllUserByConf method, of class DAOController.
     */
    @Test
    public void testGetAllUserByConfByNom() {
        List<Utilisateur> res = dao.getAllUserByConf("158");    
        testDao.getEm().getTransaction().begin();
        
        List<Utilisateur> expectedRes = (List<Utilisateur>) testDao.getEm()
                                    .createNativeQuery("SELECT * FROM Utilisateur u WHERE u.id = (SELECT a.idUser FROM AssociationUserEvent a WHERE a.idEvent=158)", Utilisateur.class)
                                    .getResultList();
        
        assertEquals(expectedRes, res);
        for(int i = 0; i < res.size(); i++){
            assertEquals(expectedRes.get(i), res.get(i));
        }
    }
    /**
     * Test of getConfById method, of class DAOController.
     */
    @Test
    public void testGetConfById() {
        Conference res = dao.getConfById("1");
        
        testDao.getEm().getTransaction().begin();
        
        Conference expectedRes = (Conference) testDao.getEm()
                                    .createNativeQuery("SELECT * FROM Conference c WHERE c.id = 1", Conference.class)
                                    .getSingleResult();
        
        assertEquals(expectedRes.getId(), res.getId());
        assertEquals(expectedRes, res);
    }

    /**
     * Test of createOrUpdate method, of class DAOController.
     */
    @Test
    public void testCreateOrUpdate_Utilisateur() {
        // create
        int nbUsers = dao.getAllUser().size();
        Utilisateur newUser;
        newUser = new Utilisateur("testmail@"+(Math.random()*10000000));
        dao.createOrUpdate(newUser);
        assertEquals(nbUsers+1, dao.getAllUser().size());
        // update
        Utilisateur u = dao.getUserByMail("nouveau@gmail.com"); 
        String newPseudo = "bg"+(long) (Math.random()*10000000);
        u.setPseudo(newPseudo);
        dao.createOrUpdate(u);
        Utilisateur u2 = testDao.getEm().find(Utilisateur.class, u.getId());
        
        assertTrue(u2.getPseudo().equals(newPseudo));
    }

    /**
     * Test of createOrUpdate method, of class DAOController.
     */
    @Test
    public void testCreateOrUpdate_Conference() {
        //create
        int nbConf = dao.getAllConf().size();
        Conference newConf;
        newConf = new Conference(dao.getUserById("1"),"nom de test","theme de test");
        dao.createOrUpdate(newConf);
        assertEquals(nbConf+1, dao.getAllConf().size());
        
        //update
        Conference c = new Conference(dao.getUserByMail("test@gmail.com"),("confTest"+Math.random()*1000),"les patates");
        c.setDescription("Le test des patates magueule");
        c.setDate(new Date(2015, 11, 11));
        
        dao.createOrUpdate(c);   
        Conference c2 = testDao.getEm().find(Conference.class, c.getId());
        assertEquals(c, c2);
    }
    
    @Test
    public void testCreate_Assoc() {
        //create
        int nbAssoc = dao.getAllAssoc().size();
        AssociationUserEvent assoc;
        Utilisateur u = new Utilisateur("test"+Math.random()*100000000);
        u = dao.createOrUpdate(u);
        assoc = new AssociationUserEvent(u.getId(),dao.getEventById("1").getId());
        dao.create(assoc);
        assertEquals(nbAssoc+1, dao.getAllAssoc().size());
    }

    /**
     * Test of nextIdUtilisateur method, of class DAOController.
     */
    @Test
    public void testNextIdUtilisateur() {
        int res = dao.nextIdUtilisateur();
        int expectedRes = (int) testDao.getEm()
                                .createNativeQuery("SELECT max(u.id) FROM Utilisateur u")
                                .getSingleResult();
        
        assertEquals(expectedRes+1, res);
    }
  
    /**
     * Test of delete for an user
     */
    @Test
    public void testDeleteUtilisateur(){
        int rand = (int) (Math.random()*1000);
        Utilisateur u = new Utilisateur("mailbidon"+rand);
        dao.createOrUpdate(u);
        int nbUsers = dao.getAllUser().size();
        dao.delete(u);
        assertEquals(nbUsers - 1 , dao.getAllUser().size());
    }
    
    /**
     * Test of delete method for a conference
     */
    @Test
    public void testDeleteConference(){
        Conference c = new Conference(dao.getUserByMail("test@gmail.com"),"rand","rand");
        dao.createOrUpdate(c);
        int nbConf = dao.getAllConf().size();
        dao.delete(c);
        assertEquals(nbConf - 1 , dao.getAllConf().size());
    }
}
