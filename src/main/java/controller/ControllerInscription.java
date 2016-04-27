/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import beans.DAOController;
import beans.DAOJenaController;
import static controller.EnvoiMail.confirmAccount;
import java.io.IOException;
import java.io.PrintWriter;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Emilie
 */
@Controller
public class ControllerInscription {

    @Autowired
    private DAOController dao;

    @Autowired
    private DAOJenaController daoJena;

    @RequestMapping(value = "/inscription", method = RequestMethod.GET)
    public ModelAndView newAccount() {
        return new ModelAndView("inscription");
    }

    @RequestMapping(value = "/inscription", method = RequestMethod.POST)
    public void AddAccount(HttpServletRequest req, HttpServletResponse resp) throws IOException, JSONException {
        String mail = req.getParameter("email");
        String password = req.getParameter("mdp");
        String join = req.getParameter("idConf");

        resp.setContentType("application/json");

        JSONObject retour = new JSONObject();
        PrintWriter out = resp.getWriter();

        if (createAccount(mail, password, join, req)) {

            Utilisateur user = dao.getUserByMail(mail);

            confirmAccount(user);

            HttpSession session = req.getSession(true);
            session.setAttribute("accountid", user.getId());
            session.setAttribute("pseudo", user.getPseudo());

            retour.put("created", true);
            retour.put("pseudo", user.getPseudo());
            retour.put("accountid", user.getId());
        } else {
            retour.put("created", false);
        }
        out.write(retour.toString());
    }

    private boolean createAccount(String mail, String password, String conf, HttpServletRequest req) {

        Utilisateur exist = dao.getUserByMail(mail);
        if (exist != null) {
            return false;
        }

        Utilisateur user = new Utilisateur(mail);
        user.setMotDePasse(password);

        String[] parts = mail.split("@");
        String pseudo = "";
        if (parts[0] != null) {
            pseudo = parts[0];
        }
        user.setPseudo(pseudo);
        dao.createOrUpdate(user);

        if (conf != null && !conf.equals("")) {
            Conference c = dao.getConfById(conf);
            if (c != null) {
                dao.createOrUpdate(user.getId(), c.getId());

                Person person = new Person(mail);

                String sc = req.getServletContext().getRealPath("");
                if (tools.OSValidator.isUnix()) {
                    sc = sc.substring(0, sc.length() - 28) + "datasets/";
                } else if (tools.OSValidator.isWindows()) {
                    sc = sc.substring(0, sc.length() - 28) + "datasets\\";
                }

                daoJena.newConnection(sc + c.getName());

                Event existEvent = daoJena.getEvent("http://data.semanticweb.org/conference/eswc/2015/event/" + c.getName());
                String oldDir = sc + c.getName();
                try {
                    FileUtils.deleteDirectory(new java.io.File(oldDir));
                } catch (IOException e) {
                    System.err.println(e);
                }
                daoJena.addPerson(person);
                existEvent.addRole(person.getId());
                daoJena.addEvent(existEvent);
            }
        }

        return true;
    }
}
