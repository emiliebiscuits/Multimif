/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import beans.DAOController;
import beans.DAOJenaController;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Conference;
import model.jena.Event;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value = "/")
public class ControllerViewer {
    
    @Autowired
    private DAOController dao;
    
    @Autowired
    private DAOJenaController daoJena;
    
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest req, HttpServletResponse resp) throws IOException, JSONException {
        
        String sc = req.getServletContext().getRealPath("");
        if (tools.OSValidator.isUnix()) {
            sc = sc.substring(0, sc.length() - 28) + "datasets/";
        } else if (tools.OSValidator.isWindows()) {
            sc = sc.substring(0, sc.length() - 28) + "datasets\\";
        }

        List<Conference> list_conf = dao.getAllConf();
        List<Object> model = new ArrayList<>();

        for (Conference c : list_conf) {
            daoJena.newConnection(sc + c.getName());
            Map<String, Object> conf = new HashMap<>();
            Event confJena = daoJena.getEvent("http://data.semanticweb.org/conference/eswc/2015/event/" + c.getName());
            conf.put("Sql", c);
            conf.put("Jena", confJena);
            model.add(conf);
        }

        return new ModelAndView("index", "list_conf", model);
    }
    
    @RequestMapping(value = "/conference/{idConf}", method = RequestMethod.GET)
    public ModelAndView viewConf(@PathVariable("idConf") String idConf, HttpServletRequest req, HttpServletResponse resp) throws IOException, JSONException {
        System.out.println("viewConf");
        Conference conf = dao.getConfById(idConf);
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
                model.Event parent = dao.getEventByConfId(idConf);
                System.out.println("parent: " + parent.getId());
                System.out.println("parentIDCOf: " + parent.getIdConf());
                List<model.Event> list = dao.getAllEventByParent(parent.getId() + "");
                for (model.Event e : list) {
                    System.out.println("eventParentId: " + e.getIdConf());
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
                return new ModelAndView("conference", "conference", model);
            } else {
                System.out.println("confJena null");
            }
        } else {
            System.out.println("conf null");
        }
        return new ModelAndView("redirect:/");

    }
    
    @RequestMapping(value = "/event/{idEvent}", method = RequestMethod.GET)
    public ModelAndView viewEvent(@PathVariable("idEvent") String idEvent, HttpServletRequest req, HttpServletResponse resp) throws IOException, JSONException {
        model.Event event = dao.getEventById(idEvent);
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
                    return new ModelAndView("event", "event", model);
                } else {
                    System.out.println("eventJena null");
                }
                return new ModelAndView("redirect:/conference/"+c.getId());
            } else {
                System.err.println("c == null");
            }

        } else {
            System.out.println("event null");
        }
        return new ModelAndView("redirect:/");

    }
    
}
