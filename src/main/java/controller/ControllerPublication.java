package controller;

import beans.DAOController;
import beans.DAOJenaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Conference;
import model.Utilisateur;
import model.jena.Event;
import model.jena.Person;
import model.jena.Publication;
import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Emilie
 */
@Controller
@RequestMapping("/account/publications")
public class ControllerPublication {

    @Autowired
    private DAOController dao;
    
    @Autowired
    private DAOJenaController daoJena;
    
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getAccountListPublication(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        HttpSession session = req.getSession();
        if (session.getAttribute("accountid") != null) {
            String idSession = session.getAttribute("accountid").toString();
            Utilisateur user = dao.getUserById(idSession);
            if (user != null) {
                List<Conference> list_publi = dao.getAllConfChair(user);
                return new ModelAndView("account/list_publications", "list_publi", list_publi);
            }
        }
        return new ModelAndView("redirect:/");
    }
    
    @RequestMapping(value = "/newPublication/{idPubli}", method = RequestMethod.GET)
    public ModelAndView getAccountNewPublication(@PathVariable("idPubli") String id, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        return new ModelAndView("account/addPublication", "event", id);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView addPublication(HttpServletRequest req, HttpServletResponse resp) throws IOException, JSONException {

//        boolean add = true;
//
//        HttpSession session = req.getSession();
//        String id = req.getParameter("id");
//        String idEvent = req.getParameter("idEvent");
//        String idSession = session.getAttribute("accountid").toString();
//        if (id != null && !id.equals("") && id.equals(idSession)) {
//            Utilisateur user = dao.getUserById(id);
//            if (user != null) {
//                
//                
//                String title = req.getParameter("title");
//                String desc = req.getParameter("desc");
//
//                if (title == null || title.equals("")) {
//                    add = false;
//                }
//
//                if (desc == null) {
//                    desc = "";
//                }
//
//                if (add) {
//                    Publication p = new Publication("id");
//                    p.setTitle(title);
//
//                    daoJena.addPublication(p);
//                    return new ModelAndView("redirect:/account/viewPublication/" + p.getId());
//                }
//
//            }
//        }
//        return new ModelAndView("redirect:/account/newPublication", "error", true);
        
        
        
        boolean add = true;

        HttpSession session = req.getSession();
        String id = req.getParameter("id");
        String idEvent = req.getParameter("idEvent");
        String idSession = session.getAttribute("accountid").toString();
        if (id != null && !id.equals("") && id.equals(idSession)) {
            Utilisateur user = dao.getUserById(id);
            model.Event event = dao.getEventById(idEvent);
            model.Event parent = dao.getEventById(event.getIdConf()+"");
            if (parent == null) {
                parent = dao.getEventByConfId(idEvent);
            }
            if (user != null) {
                if (parent != null) {
                String title = req.getParameter("title");
                String desc = req.getParameter("desc");
                    if (title == null || title.equals("")) {
                        add = false;
                    } 
                    if (desc == null) {
                        desc = "";
                    }

                    if (add) {

                        String sc = req.getServletContext().getRealPath("");
                        String root = sc.substring(0, sc.length() - 28);
                        if (tools.OSValidator.isUnix()) {
                            sc = root + "datasets/";
                        } else if (tools.OSValidator.isWindows()) {
                            sc = root + "datasets\\";
                        }
                        daoJena.newConnection(sc + parent.getUri());
                        System.out.println("parent uri: " + parent.getUri());
                        Event eventJena = daoJena.getEvent("http://data.semanticweb.org/conference/eswc/2015/event/" + event.getUri());
                        Event parentJena = daoJena.getEvent("http://data.semanticweb.org/conference/eswc/2015/event/" + parent.getUri());
                        System.out.println("parentJena id: " + parentJena.getId());
                        String oldDir = sc + parent.getUri();

                        model.Publication ev = new model.Publication(event.getId(), title, desc);
                        model.Publication create = dao.createOrUpdate(ev);
                        if (create != null) {
                            System.out.println("create id:" + create.getId() + ", uri:" + create.getUri());
                            model.jena.Publication e = new model.jena.Publication(create);
                            System.out.println("e id : " + e.getId());
                            eventJena.getPapers().add(e.getUri());

                            Person p = new Person(user.getMail());
                            e.getAuthors().add(user.getMail());
 
                            try {
                                FileUtils.deleteDirectory(new java.io.File(oldDir));
                            } catch (IOException ex) {
                                System.err.println("delete:" + ex);
                            }

                            daoJena.newConnection(sc + parent.getUri());
                            daoJena.addPerson(p);
                            daoJena.newConnection(sc + parent.getUri());
                            daoJena.addPublication(e);
//                            daoJena.newConnection(sc + parent.getUri());
//                            daoJena.addEvent(eventJena);
//                            daoJena.newConnection(sc + parent.getUri());
//                            daoJena.addEvent();
                            return new ModelAndView("redirect:/account/publications/viewPublication/" + create.getId());
                        }
                        System.err.println("create null");
                    }
                    System.err.println("add false");
                }
                System.err.println("parent null");
            }
            System.err.println("user null");
        }
        return new ModelAndView("redirect:/account/events/viewEvent/" + idEvent, "error", true);
        
        
        
    }
    
    @RequestMapping(value = "/viewPublication/{id}", method = RequestMethod.GET)
    public ModelAndView viewPubli(@PathVariable("id") String id, HttpServletRequest req, HttpServletResponse resp) throws IOException {

        HttpSession session = req.getSession();
        if (session.getAttribute("accountid") != null) {
            String idSession = session.getAttribute("accountid").toString();
            Utilisateur user = dao.getUserById(idSession);
            if (user != null) {

//                Publication p = daoJena.getPublication(id);
//                if (conf != null) {
//                    return new ModelAndView("account/viewPublication", "publication", p);
                return new ModelAndView("account/viewPublication");
//                }
            }
        }
        return new ModelAndView("redirect:/account/conferences");
    }
    
    @RequestMapping(value = "/updatePublication", method = RequestMethod.POST)
    public void updatePublication(HttpServletRequest req, HttpServletResponse resp) throws IOException, JSONException {

        resp.setContentType("application/json");
        JSONObject retour = new JSONObject();
        PrintWriter out = resp.getWriter();
        boolean update = true;

        HttpSession session = req.getSession();
        if (session.getAttribute("accountid") != null) {
            String id = session.getAttribute("accountid").toString();
            Utilisateur user = dao.getUserById(id);
            String idConf = req.getParameter("pk");
            Conference conf = dao.getConfById(idConf);
            if (user != null && conf != null) {

                switch (req.getParameter("name")) {
                    case "title":
                        conf.setNom(req.getParameter("value"));
                        break;
                    case "description":
                        conf.setDescription(req.getParameter("value"));
                        break;
                    default:
                        update = false;
                        retour.put("error", "Field not found in database");
                        break;
                }

                if (update && dao.createOrUpdate(conf) == null) {
                    update = false;
                    retour.put("error", "CreateOrUpdate function fail");
                }
            } else {
                update = false;
                retour.put("error", "User or Publication not found");
            }
        } else {
            update = false;
            retour.put("error", "Session expired");
        }

        if (update) {
            retour.put("name", req.getParameter("name"));
            retour.put("update", true);
        } else {
            retour.put("update", false);
        }

        out.write(retour.toString());
    }
}
