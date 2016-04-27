package controller;

import beans.DAOController;
import beans.DAOJenaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Conference;
import model.Utilisateur;
import model.jena.Event;
import model.jena.Person;
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
 * @author Anna
 */
@Controller
@RequestMapping("/account/events")
public class ControllerEvent {

    @Autowired
    private DAOController dao;

    @Autowired
    private DAOJenaController daoJena;

    @RequestMapping(value = "/addEvent/{idConf}", method = RequestMethod.GET)
    public ModelAndView addEventPage(@PathVariable("idConf") String idConf, HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Conference conf = dao.getConfById(idConf);
        if (conf != null) {
            return new ModelAndView("account/addEvent", "conference", conf);
        }
        return new ModelAndView("redirect:/account/viewConference/" + idConf);
    }

    @RequestMapping(value = "/addEvent/{idConf}", method = RequestMethod.POST)
    public ModelAndView newEvent(@PathVariable("idConf") String idParent, HttpServletRequest req, HttpServletResponse resp) throws IOException {

        boolean add = true;

        HttpSession session = req.getSession();
        String id = req.getParameter("id");
        String idSession = session.getAttribute("accountid").toString();
        if (id != null && !id.equals("") && id.equals(idSession)) {
            Utilisateur user = dao.getUserById(id);
            model.Event parent = dao.getEventById(idParent);
            if (parent == null) {
                parent = dao.getEventByConfId(idParent);
            }
            if (user != null) {
                if (parent != null) {
                    String title = req.getParameter("title");
                    String desc = req.getParameter("desc");
                    String startdate = req.getParameter("startdate");
                    String enddate = req.getParameter("enddate");
                    String tags = req.getParameter("tags");
                    String website = req.getParameter("website");
                    String place = req.getParameter("place");
                    String type = req.getParameter("type");
                    if (title == null || title.equals("")) {
                        add = false;
                    } else if (startdate == null || startdate.equals("")) {
                        add = false;
                    } else if (enddate == null || enddate.equals("")) {
                        add = false;
                    } else if (place == null || place.equals("")) {
                        add = false;
                    } else if (startdate.compareTo(enddate) > 0) {
                        add = false;
                    } else if (tags == null || tags.equals("")) {
                        add = false;
                    } else if (type == null || type.equals("")) {
                        add = false;
                    }
                    if (desc == null) {
                        desc = "";
                    }
                    if (website == null) {
                        website = "";
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
                        Event parentJena = daoJena.getEvent("http://data.semanticweb.org/conference/eswc/2015/event/" + parent.getUri());
                        System.out.println("parentJena id: " + parentJena.getId());
                        String oldDir = sc + parent.getUri();

                        model.Event ev = new model.Event(parent.getId(), type, title);
                        model.Event create = dao.createOrUpdate(ev);
                        if (create != null) {
                            System.out.println("create id:" + create.getId() + ", uri:" + create.getUri());
                            model.jena.Event e = new model.jena.Event(create);
                            System.out.println("e id : " + e.getId());
                            parentJena.getChildren().add(e.getId());
                            System.out.println("parentJena id: " + parentJena.getId());
                            e.setParent(parentJena.getId());

                            Person p = new Person(user.getMail());
                            e.addRole(p.getId());
                            e.getCategories().add(type);
                            e.setStartsAt(startdate);
                            e.setEndsAt(enddate);
                            e.setDescription(desc);
                            String[] temp = tags.split(",");
                            e.getTopics().addAll(Arrays.asList(temp));
                            String[] temp2 = place.split(",");
                            e.getLocations().addAll(Arrays.asList(temp2));
                            e.setHomepage(website);

                            try {
                                FileUtils.deleteDirectory(new java.io.File(oldDir));
                            } catch (IOException ex) {
                                System.err.println("delete:" + ex);
                            }

                            daoJena.newConnection(sc + parent.getUri());
                            daoJena.addPerson(p);
                            daoJena.newConnection(sc + parent.getUri());
                            daoJena.addEvent(parentJena);
                            daoJena.newConnection(sc + parent.getUri());
                            daoJena.addEvent(e);
                            return new ModelAndView("redirect:/account/events/viewEvent/" + create.getId());
                        }
                        System.err.println("create null");
                    }
                    System.err.println("add false");
                }
                System.err.println("parent null");
            }
            System.err.println("user null");
        }
        return new ModelAndView("redirect:/account/events/addEvent/" + idParent, "error", true);
    }

    @RequestMapping(value = "/viewEvent/{idEvent}", method = RequestMethod.GET)
    public ModelAndView viewEvent(@PathVariable("idEvent") String id, HttpServletRequest req, HttpServletResponse resp) throws IOException {

        HttpSession session = req.getSession();
        if (session.getAttribute("accountid") != null) {
            String idSession = session.getAttribute("accountid").toString();
            Utilisateur user = dao.getUserById(idSession);
            if (user != null) {

                model.Event event = dao.getEventById(id);
                if (event != null) {

                    model.Event parent = dao.getEventById(event.getIdConf() + "");
                    Conference c = dao.getConfById(parent.getIdConf() + "");
                    if (c != null) {
                        String sc = req.getServletContext().getRealPath("");
                        if (tools.OSValidator.isUnix()) {
                            sc = sc.substring(0, sc.length() - 28) + "datasets/";
                        } else if (tools.OSValidator.isWindows()) {
                            sc = sc.substring(0, sc.length() - 28) + "datasets\\";
                        }
                        daoJena.newConnection(sc + c.getName());
                        Event eventJena = daoJena.getEvent("http://data.semanticweb.org/conference/eswc/2015/event/" + event.getUri());
                        if (eventJena != null) {
                            Map<String, Object> model = new HashMap<>();
                            model.put("Sql", event);
                            model.put("Jena", eventJena);
                            model.put("Parent", c);
                            return new ModelAndView("account/viewEvent", "event", model);
                        } else {
                            System.out.println("eventJena null");
                        }
                    } else {
                        System.err.println("c == null");
                    }

                } else {
                    System.out.println("event null");
                }
            }
        }
        return new ModelAndView("redirect:/account/conferences");
    }

    @RequestMapping(value = "/updateEvent", method = RequestMethod.POST)
    public void updateEvent(HttpServletRequest req, HttpServletResponse resp) throws IOException, JSONException {

        resp.setContentType("application/json");
        JSONObject retour = new JSONObject();
        PrintWriter out = resp.getWriter();
        boolean update = true;

        HttpSession session = req.getSession();
        if (session.getAttribute("accountid") != null) {
            String id = session.getAttribute("accountid").toString();
            Utilisateur user = dao.getUserById(id);
            String idEvent = req.getParameter("pk");
            model.Event event = dao.getEventById(idEvent);
            if (user != null && event != null) {

                model.Event parent = dao.getEventById(event.getIdConf() + "");
                Conference c = dao.getConfById(parent.getIdConf() + "");

                String sc = req.getServletContext().getRealPath("");
                String root = sc.substring(0, sc.length() - 28);
                if (tools.OSValidator.isUnix()) {
                    sc = root + "datasets/";
                } else if (tools.OSValidator.isWindows()) {
                    sc = root + "datasets\\";
                }
                daoJena.newConnection(sc + c.getName());

                Event existEvent = daoJena.getEvent("http://data.semanticweb.org/conference/eswc/2015/event/" + event.getUri());
                Event parentEvent = daoJena.getEvent("http://data.semanticweb.org/conference/eswc/2015/event/" + c.getName());
                String oldDir = sc + c.getName();
                
                switch (req.getParameter("name")) {
                    case "title":
                        event.setUri(req.getParameter("value"));
                        existEvent.setName(req.getParameter("value"));
                        existEvent.updateId();
                        break;
                    case "description":
                        existEvent.setDescription(req.getParameter("value"));
                        break;
                    case "startdate":
                        existEvent.setStartsAt(req.getParameter("value"));
                        break;
                    case "enddate":
                        existEvent.setEndsAt(req.getParameter("value"));
                        break;
                    case "tags":
                        existEvent.getTopics().clear();
                        existEvent.getTopics().addAll(Arrays.asList(req.getParameterValues("value[]")));
                        break;
                    case "website":
                        existEvent.setHomepage(req.getParameter("value"));
                        break;
                    case "type":
                        String type = "";
                        int index = 0;
                        for (String t : req.getParameterValues("value[]")) {
                            type += t;
                            if (index < req.getParameterValues("value[]").length - 1) {
                                type += ",";
                            }
                            index++;
                        }
                        event.setType(type);
                        existEvent.getCategories().clear();
                        existEvent.getCategories().addAll(Arrays.asList(req.getParameterValues("value[]")));
                        break;
                    case "place":
                        existEvent.getLocations().clear();
                        existEvent.getLocations().addAll(Arrays.asList(req.getParameterValues("value[]")));
                        break;
                    default:
                        update = false;
                        retour.put("error", "Field not found in database");
                        break;
                }

                if (update && dao.createOrUpdate(event) == null) {
                    update = false;
                    retour.put("error", "CreateOrUpdate function fail");
                } else {
                    System.out.println("\nDir: " + oldDir + "\n");
                    try {
                        FileUtils.deleteDirectory(new java.io.File(oldDir));
                    } catch (IOException e) {
                        System.err.println(e);
                    }
                    daoJena.newConnection(sc + c.getName());
                    daoJena.addEvent(parentEvent);
                    daoJena.newConnection(sc + c.getName());
                    daoJena.addEvent(existEvent);
                }
            } else {
                update = false;
                retour.put("error", "User or Conference not found");
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
