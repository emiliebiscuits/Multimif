package beans;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import model.AssociationUserEvent;
import model.Conference;
import model.Utilisateur;
import model.Event;
import model.Publication;
import org.springframework.stereotype.Repository;

@Repository("DAOController")
public class DAOController {

    @PersistenceContext
    private EntityManager em;

    public DAOController() {
    }

    public List<Utilisateur> getAllUser() {
        em = Persistence.createEntityManagerFactory("multimif").createEntityManager();
        em.getTransaction().begin();
        List<Utilisateur> arrl = (List<Utilisateur>) em.createNamedQuery("Utilisateur.getAllUser").getResultList();
        em.getTransaction().commit();
        em.close();
        return arrl;
    }

    public List<Utilisateur> getAllUserLike(String name) {
        em = Persistence.createEntityManagerFactory("multimif").createEntityManager();
        em.getTransaction().begin();
        List<Utilisateur> arrl = (List<Utilisateur>) em.createNamedQuery("Utilisateur.getAllUserLike").setParameter("name", name + "%").getResultList();
        em.getTransaction().commit();
        em.close();
        return arrl;
    }

    public List<Conference> getAllConf() {
        em = Persistence.createEntityManagerFactory("multimif").createEntityManager();
        em.getTransaction().begin();
        List<Conference> arrl = (List<Conference>) em.createNamedQuery("Conference.getAll").getResultList();
        em.getTransaction().commit();
        em.close();
        return arrl;
    }

    public List<Event> getAllEvent() {
        em = Persistence.createEntityManagerFactory("multimif").createEntityManager();
        em.getTransaction().begin();
        List<Event> arrl = (List<Event>) em.createNamedQuery("Event.getAll").getResultList();
        em.getTransaction().commit();
        em.close();
        return arrl;
    }
    
    public List<Publication> getAllPublication() {
        em = Persistence.createEntityManagerFactory("multimif").createEntityManager();
        em.getTransaction().begin();
        List<Publication> arrl = (List<Publication>) em.createNamedQuery("Publication.getAll").getResultList();
        em.getTransaction().commit();
        em.close();
        return arrl;
    }

    public List<Event> getAllEventByParent(String id) {
        em = Persistence.createEntityManagerFactory("multimif").createEntityManager();
        em.getTransaction().begin();

        try {
            int idInt = Integer.parseInt(id);
            List<Event> arrl = (List<Event>) em.createNamedQuery("Event.getAllByParent").setParameter("id", idInt).getResultList();
            em.close();
            return arrl;
        } catch (NoResultException e) {
            em.close();
            return null;
        }
    }

    public List<AssociationUserEvent> getAllAssoc() {
        em = Persistence.createEntityManagerFactory("multimif").createEntityManager();
        em.getTransaction().begin();
        List<AssociationUserEvent> arrl = (List<AssociationUserEvent>) em.createNamedQuery("Assoc.getAll").getResultList();
        em.getTransaction().commit();
        em.close();
        return arrl;
    }

    public List<Conference> getAllConf(Utilisateur u) {
        em = Persistence.createEntityManagerFactory("multimif").createEntityManager();
        em.getTransaction().begin();
        List<Conference> arrl = (List<Conference>) em.createNamedQuery("Conference.getAllConf").setParameter("user", u).getResultList();
        em.getTransaction().commit();
        em.close();
        return arrl;
    }

    public List<Conference> getAllConfChair(Utilisateur u) {
        em = Persistence.createEntityManagerFactory("multimif").createEntityManager();
        em.getTransaction().begin();
        List<Conference> arrl = (List<Conference>) em.createNamedQuery("Conference.getAllConfChair").setParameter("idUser", u.getId()).getResultList();
        em.getTransaction().commit();
        em.close();
        return arrl;
    }

    public Utilisateur getUserByMail(String mail) {
        em = Persistence.createEntityManagerFactory("multimif").createEntityManager();
        em.getTransaction().begin();

        try {
            Utilisateur u = (Utilisateur) em.createNamedQuery("Utilisateur.getUserByMail").setParameter("mail", mail).getSingleResult();
            em.close();
            return u;
        } catch (NoResultException e) {
            em.close();
            return null;
        }
    }

    public List<Utilisateur> getUserByPseudo(String pseudo) {
        em = Persistence.createEntityManagerFactory("multimif").createEntityManager();
        em.getTransaction().begin();
        try {
            List<Utilisateur> l = (List<Utilisateur>) em.createNamedQuery("Utilisateur.getUserByPseudo").setParameter("pseudo", pseudo).getResultList();
            em.close();
            return l;
        } catch (NoResultException e) {
            em.close();
            return null;
        }
    }

    public Utilisateur getUserById(String id) {
        em = Persistence.createEntityManagerFactory("multimif").createEntityManager();
        em.getTransaction().begin();
        try {
            int idInt = Integer.parseInt(id);
            Utilisateur u = (Utilisateur) em.createNamedQuery("Utilisateur.getUserById").setParameter("id", idInt).getSingleResult();
            em.close();
            return u;
        } catch (NoResultException e) {
            em.close();
            return null;
        }
    }

    public Event getEventById(String id) {
        em = Persistence.createEntityManagerFactory("multimif").createEntityManager();
        em.getTransaction().begin();
        try {
            int idInt = Integer.parseInt(id);
            Event e = (Event) em.createNamedQuery("Event.getEventById").setParameter("id", idInt).getSingleResult();
            em.close();
            return e;
        } catch (NoResultException e) {
            em.close();
            return null;
        }
    }
    
    public Publication getPublicationById(String id) {
        em = Persistence.createEntityManagerFactory("multimif").createEntityManager();
        em.getTransaction().begin();
        try {
            int idInt = Integer.parseInt(id);
            Publication e = (Publication) em.createNamedQuery("Publication.getPublicationById").setParameter("id", idInt).getSingleResult();
            em.close();
            return e;
        } catch (NoResultException e) {
            em.close();
            return null;
        }
    }

    public Event getEventByConfId(String id) {
        em = Persistence.createEntityManagerFactory("multimif").createEntityManager();
        em.getTransaction().begin();
        try {
            int idInt = Integer.parseInt(id);
            Event e = (Event) em.createNamedQuery("Event.getEventByConfId").setParameter("id", idInt).setMaxResults(1).getSingleResult();
            em.close();
            return e;
        } catch (NoResultException e) {
            em.close();
            return null;
        }
    }
    
    public Publication getPublicationByEventId(String id) {
        em = Persistence.createEntityManagerFactory("multimif").createEntityManager();
        em.getTransaction().begin();
        try {
            int idInt = Integer.parseInt(id);
            Publication e = (Publication) em.createNamedQuery("Event.getPublicationByEventId").setParameter("id", idInt).setMaxResults(1).getSingleResult();
            em.close();
            return e;
        } catch (NoResultException e) {
            em.close();
            return null;
        }
    }

    public List<Conference> getConfByNom(String nom) {
        em = Persistence.createEntityManagerFactory("multimif").createEntityManager();
        em.getTransaction().begin();
        try {
            List<Conference> c = (List<Conference>) em.createNamedQuery("Conference.getConferenceByName").setParameter("name", nom).getResultList();
            em.close();
            return c;
        } catch (NoResultException e) {
            em.close();
            return null;
        }
    }

    public Conference getConfById(String id) {
        em = Persistence.createEntityManagerFactory("multimif").createEntityManager();
        em.getTransaction().begin();
        try {
            int idInt = Integer.parseInt(id);
            Conference c = (Conference) em.createNamedQuery("Conference.getConferenceById").setParameter("id", idInt).getSingleResult();
            em.close();
            return c;
        } catch (NoResultException e) {
            em.close();
            return null;
        }
    }

    public List<Utilisateur> getAllUserByConf(String idConf) {
        em = Persistence.createEntityManagerFactory("multimif").createEntityManager();
        em.getTransaction().begin();
        try {
            int idInt = Integer.parseInt(idConf);
            List<Utilisateur> list = (List<Utilisateur>) em.createNamedQuery("Utilisateur.getAllUserByConf").setParameter("idConf", idInt).getResultList();
            em.close();
            return list;
        } catch (NoResultException e) {
            em.close();
            return null;
        }
    }

    public Utilisateur createOrUpdate(Utilisateur utilisateur) {
        em = Persistence.createEntityManagerFactory("multimif").createEntityManager();
        Utilisateur u = em.find(Utilisateur.class, utilisateur.getId());
        em.getTransaction().begin();
        if (u == null) {
            em.persist(utilisateur);
        } else {
            em.merge(utilisateur);
        }
        em.getTransaction().commit();
        em.close();
        return utilisateur;
    }

    public Utilisateur delete(Utilisateur utilisateur) {
        em = Persistence.createEntityManagerFactory("multimif").createEntityManager();
        Utilisateur u = em.find(Utilisateur.class, utilisateur.getId());
        if (u != null) {
            em.getTransaction().begin();
            em.remove(u);
            em.getTransaction().commit();
            em.close();
        }
        return u;
    }

    public Conference delete(Conference conference) {
        em = Persistence.createEntityManagerFactory("multimif").createEntityManager();
        Conference c = em.find(Conference.class, conference.getId());
        if (c != null) {
            em.getTransaction().begin();
            em.remove(c);
            em.getTransaction().commit();
            em.close();
        }
        return c;
    }

    public Conference createOrUpdate(Conference conference) {
        em = Persistence.createEntityManagerFactory("multimif").createEntityManager();
        Conference c = em.find(Conference.class, conference.getId());
        em.getTransaction().begin();
        if (c == null) {
            em.persist(conference);
        } else {
            em.merge(conference);
        }
        em.getTransaction().commit();
        em.close();
        Event event = new Event(conference.getId(), "conference", conference.getName());
        createOrUpdate(event);
        AssociationUserEvent assoc = new AssociationUserEvent(conference.getDirector().getId(), conference.getId());
        create(assoc);
        return conference;
    }

    public AssociationUserEvent create(AssociationUserEvent assoc) {
        em = Persistence.createEntityManagerFactory("multimif").createEntityManager();
        AssociationUserEvent a = em.find(AssociationUserEvent.class, assoc.getId());
        em.getTransaction().begin();
        if (a == null) {
            em.persist(assoc);
        } else {
            em.merge(assoc);
        }
        em.getTransaction().commit();
        em.close();
        return assoc;
    }

    public Event createOrUpdate(Event event) {
        em = Persistence.createEntityManagerFactory("multimif").createEntityManager();
        Event e = em.find(Event.class, event.getId());
        em.getTransaction().begin();
        if (e == null) {
            em.persist(event);
        } else {
            em.merge(event);
        }
        em.getTransaction().commit();
        em.close();
        return event;
    }
    
    public Publication createOrUpdate(Publication publication) {
        em = Persistence.createEntityManagerFactory("multimif").createEntityManager();
        Publication e = em.find(Publication.class, publication.getId());
        em.getTransaction().begin();
        if (e == null) {
            em.persist(publication);
        } else {
            em.merge(publication);
        }
        em.getTransaction().commit();
        em.close();
        return publication;
    }

    public AssociationUserEvent createOrUpdate(int user, int conf) {
        AssociationUserEvent assoc = new AssociationUserEvent(user, conf);
        em = Persistence.createEntityManagerFactory("multimif").createEntityManager();
        em.getTransaction().begin();
        em.persist(assoc);
        em.getTransaction().commit();
        em.close();
        return assoc;
    }

    public int nextIdUtilisateur() {
        em = Persistence.createEntityManagerFactory("multimif").createEntityManager();
        int value = (Integer) (em.createNamedQuery("Utilisateur.getLastId").getSingleResult());
        em.close();
        return value + 1;
    }

    /**
     * Renvoie l'entityManager, méthode principalement pour les tests.
     *
     * @return EntityManager de la classe DAOController
     */
    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    @SuppressWarnings("empty-statement")
    public static void main(String[] args) {

    }
}
