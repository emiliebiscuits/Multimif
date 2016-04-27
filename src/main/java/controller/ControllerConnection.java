/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import beans.DAOController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Utilisateur;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ControllerConnection {
    
    @Autowired
    private DAOController dao;
    
    @RequestMapping(value = "/connection", method = RequestMethod.POST)
    public void Login(HttpServletRequest req, HttpServletResponse resp) throws IOException, JSONException {
        String mail = req.getParameter("email");
        String password = req.getParameter("mdp");
        resp.setContentType("application/json");

        JSONObject retour = new JSONObject();
        PrintWriter out = resp.getWriter();
        Utilisateur user = authentification(mail, password);
        if ( user != null) {       
            HttpSession session = req.getSession();
            session.setAttribute("accountid", user.getId());
            session.setAttribute("pseudo", user.getPseudo()); 
            
            retour.put("authentified", true);
            retour.put("pseudo", user.getPseudo());
            retour.put("accountid", user.getId());
            
        } else {
            retour.put("authentified", false);
        }
        out.write(retour.toString());
    }
    
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logOut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(true);
        session.invalidate();
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/connection", method = RequestMethod.GET)
    public void printInfo(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/xml");
        PrintWriter out = resp.getWriter();
        out.println("<response>ok</response>");
    }    

    private Utilisateur authentification(String mail, String password) {
        Utilisateur exist = dao.getUserByMail(mail);
        if( exist != null && exist.getMotDePasse().equals(password) ){
            return exist;
        }
        return null;
    }
}
