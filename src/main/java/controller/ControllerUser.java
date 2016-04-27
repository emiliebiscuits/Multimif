/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import beans.DAOController;
import beans.DAOJenaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Conference;
import model.Utilisateur;
import model.jena.Event;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.apache.commons.io.FileUtils;

@Controller
@RequestMapping("account")
public class ControllerUser {

    @Autowired
    private DAOController dao;

    @Autowired
    private DAOJenaController daoJena;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getAccount(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        HttpSession session = req.getSession();
        if (session.getAttribute("accountid") != null) {
            String id = session.getAttribute("accountid").toString();
            Utilisateur user = dao.getUserById(id);
            if (user != null) {
                return new ModelAndView("account/user_account", "user", user);
            }
        }
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView getAccountById(@PathVariable("id") String id, HttpServletRequest req, HttpServletResponse resp) throws IOException {

        HttpSession session = req.getSession();
        if (session.getAttribute("accountid") != null) {
            String idSession = session.getAttribute("accountid").toString();
            if (id.equals(idSession)) {
                Utilisateur user = dao.getUserById(id);
                if (user != null) {
                    return new ModelAndView("account/user_account", "user", user);
                }
            }
        }
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/conferences", method = RequestMethod.GET)
    public ModelAndView getAccountListConf(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        HttpSession session = req.getSession();
        if (session.getAttribute("accountid") != null) {
            String idSession = session.getAttribute("accountid").toString();
            Utilisateur user = dao.getUserById(idSession);
            if (user != null) {

                String sc = req.getServletContext().getRealPath("");
                if (tools.OSValidator.isUnix()) {
                    sc = sc.substring(0, sc.length() - 28) + "datasets/";
                } else if (tools.OSValidator.isWindows()) {
                    sc = sc.substring(0, sc.length() - 28) + "datasets\\";
                }

                List<Conference> list_conf = dao.getAllConfChair(user);
                List<Object> model = new ArrayList<>();

                for (Conference c : list_conf) {
                    daoJena.newConnection(sc + c.getName());
                    Map<String, Object> conf = new HashMap<>();
                    Event confJena = daoJena.getEvent("http://data.semanticweb.org/conference/eswc/2015/event/" + c.getName());
                    conf.put("Sql", c);
                    conf.put("Jena", confJena);
                    model.add(conf);
                }

                return new ModelAndView("account/list_conferences", "list_conf", model);

            }
        }
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/newConf", method = RequestMethod.GET)
    public ModelAndView getAccountNewConf(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        return new ModelAndView("account/addConference");
    }

    @RequestMapping(value = "/import", method = RequestMethod.GET)
    public ModelAndView getImportConf() throws IOException {
        return new ModelAndView("account/import");
    }


    @RequestMapping(value = "/viewConference/{id}", method = RequestMethod.GET)
    public ModelAndView getAccountViewConf(@PathVariable("id") String id, HttpServletRequest req, HttpServletResponse resp) throws IOException {

        HttpSession session = req.getSession();
        if (session.getAttribute("accountid") != null) {
            String idSession = session.getAttribute("accountid").toString();
            Utilisateur user = dao.getUserById(idSession);
            if (user != null) {

                Conference conf = dao.getConfById(id);
                if (conf != null) {
                    String sc = req.getServletContext().getRealPath("");
                    if (tools.OSValidator.isUnix()) {
                        sc = sc.substring(0, sc.length() - 28) + "datasets/";
                    } else if (tools.OSValidator.isWindows()) {
                        sc = sc.substring(0, sc.length() - 28) + "datasets\\";
                    }
                    daoJena.newConnection(sc + conf.getName());
                    Event confJena = daoJena.getEvent("http://data.semanticweb.org/conference/eswc/2015/event/" + conf.getName());
                    if (confJena != null) {
                        
                        ArrayList<Map<String, Object>> listEvents = new ArrayList<>();
                        model.Event parent = dao.getEventByConfId(id);
                        System.out.println("parent: "+parent.getId());
                        System.out.println("parentIDCOf: "+parent.getIdConf());
                        List<model.Event> list = dao.getAllEventByParent(parent.getId()+"");
                        for(model.Event e: list){
                            System.out.println("eventParentId: "+e.getIdConf());
                            Event eJena = daoJena.getEvent("http://data.semanticweb.org/conference/eswc/2015/event/" + e.getUri());
                            Map<String, Object> map = new HashMap<>();
                            map.put("Sql", e);
                            map.put("Jena", eJena);
                            listEvents.add(map);
                        }
                        Map<String, Object> model = new HashMap<>();
                        model.put("Sql", conf);
                        model.put("Jena", confJena);
                        model.put("Events", listEvents);
                        return new ModelAndView("account/viewConference", "conference", model);
                    } else {
                        System.out.println("confJena null");
                    }
                } else {
                    System.out.println("conf null");
                }
            }
        }
        return new ModelAndView("redirect:/account/conferences");
    }


    @RequestMapping(value = "/updateAccount", method = RequestMethod.POST)
    public void updateAccount(HttpServletRequest req, HttpServletResponse resp) throws IOException, JSONException {

        resp.setContentType("application/json");
        JSONObject retour = new JSONObject();
        PrintWriter out = resp.getWriter();
        boolean update = true;

        HttpSession session = req.getSession();
        if (session.getAttribute("accountid") != null) {
            String id = session.getAttribute("accountid").toString();
            Utilisateur user = dao.getUserById(id);
            if (req.getParameter("pk").equals(id) && user != null) {

                switch (req.getParameter("name")) {
                    case "pseudo":
                        user.setPseudo(req.getParameter("value"));
                        break;
                    case "prenom":
                        user.setFirst_name(req.getParameter("value"));
                        break;
                    case "nom":
                        user.setLast_name(req.getParameter("value"));
                        break;
                    case "pays":
                        user.setCountry(req.getParameter("value"));
                        break;
                    case "site_web":
                        user.setWeb_page(req.getParameter("value"));
                        break;
                    case "affiliation":
                        user.setAffiliation(req.getParameter("value"));
                        break;
                    case "email":
                        user.setMail(req.getParameter("value"));
                        break;
                    case "mdp":
                        if (req.getParameter("actuMdp").equals(user.getMotDePasse())) {
                            user.setMotDePasse(req.getParameter("value"));
                            retour.put("mdp", user.getMotDePasse());
                        } else {
                            update = false;
                            retour.put("error", "Incorrect current password");
                        }
                        break;
                    default:
                        update = false;
                        retour.put("error", "Field not found in database");
                        break;
                }

                if (update && dao.createOrUpdate(user) == null) {
                    update = false;
                    retour.put("error", "CreateOrUpdate function fail");
                }
            } else {
                update = false;
                retour.put("error", "User not found");
            }
        } else {
            update = false;
            retour.put("error", "Session expired");
        }

        if (update) {
            retour.put("update", true);
        } else {
            retour.put("update", false);
        }

        out.write(retour.toString());
    }

    @RequestMapping(value = "/updateConference", method = RequestMethod.POST)
    public void updateConference(HttpServletRequest req, HttpServletResponse resp) throws IOException, JSONException {

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
                
                String sc = req.getServletContext().getRealPath("");
                String root = sc.substring(0, sc.length() - 28);
                if (tools.OSValidator.isUnix()) {
                    sc = root + "datasets/";
                } else if (tools.OSValidator.isWindows()) {
                    sc = root + "datasets\\";
                }
                daoJena.newConnection(sc + conf.getName());
                
                Event existEvent = daoJena.getEvent("http://data.semanticweb.org/conference/eswc/2015/event/" + conf.getName());
                
                String oldDir = sc + conf.getName();
                switch (req.getParameter("name")) {
                    case "title":
                        conf.setNom(req.getParameter("value"));
                        existEvent.setName(req.getParameter("value"));
                        existEvent.updateId();
                        break;
                    case "description":
                        conf.setDescription(req.getParameter("value"));
                        existEvent.setDescription(req.getParameter("value"));
                        break;
                    case "startdate":
                        existEvent.setStartsAt(req.getParameter("value"));
                        break;
                    case "enddate":
                        existEvent.setEndsAt(req.getParameter("value"));
                        break;
                    case "tags":
                        String tags = "";
                        int index = 0;
                        for (String t : req.getParameterValues("value[]")) {
                            tags += t;
                            if (index < req.getParameterValues("value[]").length - 1) {
                                tags += ",";
                            }
                            index++;
                        }
                        conf.setTheme(tags);
                        existEvent.getTopics().clear();
                        existEvent.getTopics().addAll(Arrays.asList(req.getParameterValues("value[]")));
                        break;
                    case "website":
                        existEvent.setHomepage(req.getParameter("value"));
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

                if (update && dao.createOrUpdate(conf) == null) {
                    update = false;
                    retour.put("error", "CreateOrUpdate function fail");
                } else {
                    System.out.println("\nDir: " + oldDir + "\n");
                    try {
                        FileUtils.deleteDirectory(new java.io.File(oldDir));
                    } catch (IOException e) {
                        System.err.println(e);
                    }
                    daoJena.newConnection(sc + conf.getName());
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

    @RequestMapping(value = "/listUser", method = RequestMethod.GET)
    public void getListUsers(HttpServletRequest req, HttpServletResponse resp) throws IOException, JSONException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
//        JSONObject retour = new JSONObject();

        String name = req.getParameter("term");
        List<Utilisateur> list_user;
        if (name != null && !name.equals("")) {
            list_user = dao.getAllUserLike(name);
        } else {
            list_user = dao.getAllUser();
        }

        JSONArray retour = new JSONArray();
        for (Utilisateur u : list_user) {
            JSONObject user = new JSONObject();
            user.put("mail", u.getMail());
            user.put("pseudo", u.getPseudo());
            user.put("id", u.getId());
            retour.put(user);
        }

        out.println(retour.toString());
    }
}
