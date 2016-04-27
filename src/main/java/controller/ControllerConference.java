package controller;

import beans.DAOController;
import beans.DAOJenaController;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Conference;
import model.Utilisateur;
import model.jena.Event;
import model.jena.Person;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
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
@RequestMapping("/conferences")
public class ControllerConference {

    @Autowired
    private DAOController dao;

    @Autowired
    private DAOJenaController daoJena;

    @RequestMapping(value = "/names", method = RequestMethod.GET)
    public void getConferencesNameList(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        String response = getConferencesNames();
        out.println(response);
    }

    @RequestMapping(value = "{idConf}/listUser", method = RequestMethod.GET)
    public void getListUsers(@PathVariable("idConf") String idConf, HttpServletRequest req, HttpServletResponse resp) throws IOException, JSONException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        JSONObject retour = new JSONObject();

        Conference conf = dao.getConfById(idConf);
        if (conf != null) {
            List<Utilisateur> list_user = dao.getAllUserByConf(idConf);

            JSONArray items = new JSONArray();
            for (Utilisateur u : list_user) {
                JSONObject user = new JSONObject();
                user.put("mail", u.getMail());
                user.put("pseudo", u.getPseudo());
                user.put("id", u.getId());
                items.put(user);
            }

            retour.put("total_count", list_user.size());
            retour.put("items", items);
        }

        out.println(retour.toString());
    }

    @RequestMapping(value = "{idConf}/addChair", method = RequestMethod.POST)
    public void addChair(@PathVariable("idConf") String idConf, HttpServletRequest req, HttpServletResponse resp) throws IOException, JSONException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        JSONObject retour = new JSONObject();

        String mail = req.getParameter("chair");
        Conference conf = dao.getConfById(idConf);
        if (conf != null && mail != null) {

            Utilisateur exist = dao.getUserByMail(mail);

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

            if (exist != null) {
                dao.createOrUpdate(exist.getId(), conf.getId());
                retour.put("ok", true);
            } else {
                ArrayList<String> contact = new ArrayList<>();
                contact.add(mail);
                EnvoiMail.invitUsers(conf.getName(), idConf, contact);
                retour.put("ok", "send");
            }

            try {
                FileUtils.deleteDirectory(new java.io.File(oldDir));
            } catch (IOException e) {
                System.err.println(e);
            }
            Person person = new Person(mail);
            existEvent.addRole(person.getId());
            daoJena.newConnection(sc + conf.getName());
            daoJena.addPerson(person);
            daoJena.newConnection(sc + conf.getName());
            daoJena.addEvent(existEvent);

        } else {
            retour.put("ok", false);
        }

        out.println(retour.toString());
    }

    @RequestMapping(method = RequestMethod.GET)
    public void getConferencesList(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        String response = getConferencesNames();
        out.println(response);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView addConference(HttpServletRequest req, HttpServletResponse resp) throws IOException, JSONException {

        boolean add = true;

        HttpSession session = req.getSession();
        String id = req.getParameter("id");
        String idSession = session.getAttribute("accountid").toString();
        if (id != null && !id.equals("") && id.equals(idSession)) {
            Utilisateur user = dao.getUserById(id);
            if (user != null) {

                String title = req.getParameter("title");
                String desc = req.getParameter("desc");
                String startdate = req.getParameter("startdate");
                String enddate = req.getParameter("enddate");
                String tags = req.getParameter("tags");
                String website = req.getParameter("website");
                String place = req.getParameter("place");
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
                }

                if (desc == null) {
                    desc = "";
                }
                if (website == null) {
                    website = "";
                }

                if (add) {
                    Conference conf = new Conference(user, title, tags);
                    conf.setDescription(desc);
                    Date now = new Date();
                    conf.setDate(new java.sql.Date(now.getTime()));

                    Conference create = dao.createOrUpdate(conf);
                    if (create != null) {

                        Event e = new Event(create);
                        Person p = new Person(user.getMail());
                        e.addRole(p.getId());
                        e.getCategories().add("conference");
                        e.setStartsAt(startdate);
                        e.setEndsAt(enddate);
                        String[] temp = place.split(",");
                        e.getLocations().addAll(Arrays.asList(temp));
                        e.setHomepage(website);
                        String sc = req.getServletContext().getRealPath("");
                        if (tools.OSValidator.isUnix()) {
                            sc = sc.substring(0, sc.length() - 28) + "datasets/";
                        } else if (tools.OSValidator.isWindows()) {
                            sc = sc.substring(0, sc.length() - 28) + "datasets\\";
                        }

                        daoJena.newConnection(sc + create.getName());
                        daoJena.addPerson(p);
                        daoJena.newConnection(sc + create.getName());
                        daoJena.addEvent(e);

                        return new ModelAndView("redirect:/account/viewConference/" + create.getId());
                    }
                }

            }
        }
        return new ModelAndView("redirect:/account/newConf", "error", true);
    }

    public String getConferencesNames() {
        String list = "{\"conferences\":[";
        List<Conference> listconf = dao.getAllConf();
        String comma = "";
        for (Conference c : listconf) {
            list = list + comma + "\"" + c.getName() + "\"";
            comma = ",";
        }
        list = list + "]}";
        return list;
    }

    @RequestMapping(value = "/add/imported", method = RequestMethod.POST)
    public ModelAndView addImportedConferences(HttpServletRequest req, HttpServletResponse resp) throws IOException, JSONException, ParseException {

        boolean add = true;

        HttpSession session = req.getSession();
        String idSession = session.getAttribute("accountid").toString();
        if (idSession != null) {
            Utilisateur user = dao.getUserById(idSession);
            if (user != null) {

                String json = req.getParameter("content");
                JSONObject jObj = new JSONObject(json);
                JSONArray jsonMainArr = jObj.getJSONArray("conferences");
                for (int i = 0; i < jsonMainArr.length(); i++) {
                    JSONObject childJSONObject = jsonMainArr.getJSONObject(i);
                    String title = childJSONObject.getString("name");
                    String desc = childJSONObject.getString("description");
                    String tags = childJSONObject.getString("theme");
                    
                    if (title == null || title.equals("")) {
                        add = false;
                    } else if (tags == null || tags.equals("")) {
                        add = false;
                    }
                    if (desc == null) {
                        desc = "";
                    }

                    if (add) {
                        Conference conf = new Conference(user, title, tags);
                        conf.setDescription(desc);
                        Date now = new Date();
                        conf.setDate(new java.sql.Date(now.getTime()));

                        Conference create = dao.createOrUpdate(conf);
                        if (create != null) {

                            Event e = new Event(create);
                            Person p = new Person(user.getMail());
                            e.addRole(p.getId());
                            e.getCategories().add("conference");
//                            e.setStartsAt(startdate);
//                            e.setEndsAt(enddate);
//                            String[] temp = place.split(",");
//                            e.getLocations().addAll(Arrays.asList(temp));
//                            e.setHomepage(website);
                            String sc = req.getServletContext().getRealPath("");
                            if (tools.OSValidator.isUnix()) {
                                sc = sc.substring(0, sc.length() - 28) + "datasets/";
                            } else if (tools.OSValidator.isWindows()) {
                                sc = sc.substring(0, sc.length() - 28) + "datasets\\";
                            }

                            daoJena.newConnection(sc + create.getName());
                            daoJena.addPerson(p);
                            daoJena.newConnection(sc + create.getName());
                            daoJena.addEvent(e);
                            
                        }
                    }
                }
                return new ModelAndView("redirect:/account/conferences/");
            }
        }
        return new ModelAndView("redirect:/account/import", "error", true);

////        ArrayList<Conference> listconf = new ArrayList<>();
//        String json = req.getParameter("content");
//        JSONObject jObj = new JSONObject(json);
//        JSONArray jsonMainArr = jObj.getJSONArray("conferences");
//        for (int i = 0; i < jsonMainArr.length(); i++) {
//            JSONObject childJSONObject = jsonMainArr.getJSONObject(i);
//            String director = childJSONObject.getString("director");
//            String name = childJSONObject.getString("name");
//            String theme = childJSONObject.getString("theme");
//            String description = childJSONObject.getString("description");
//            String date = childJSONObject.getString("date");
//            System.out.println(director + "---" + name + "---" + theme + "---" + description + "---" + date);
//            Utilisateur user = dao.getUserById(director);
//            Conference conf = new Conference(user, name, theme);
//            /*conf.setDescription(description);
//             SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//             Date d = formatter.parse(date);
//             conf.setDate((java.sql.Date) d);*/
//            listconf.add(conf);
//            dao.createOrUpdate(conf);
//        }
//        //System.out.println(json);
//        //System.out.println("servlet@_@");
////        for(Conference c:listconf){
////            dao.createOrUpdate(c);
////            System.out.println("bla");
////        }
//        resp.setContentType("application/json");
//        PrintWriter out = resp.getWriter();
//        String response = "{\"state\":\"success\"}";
//        out.println(response);
    }

    @RequestMapping(value = "export/json/{idConf}", method = RequestMethod.GET)
    public void exportJson(@PathVariable("idConf") String idConf, HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Conference conf = dao.getConfById(idConf);
        if (conf != null) {

            String sc = req.getServletContext().getRealPath("");
            String root = sc.substring(0, sc.length() - 28);
            String dirExpo = "";
            System.out.println("\nsc: " + sc + "\n");
            if (tools.OSValidator.isUnix()) {
                sc = root + "datasets/";
                dirExpo = root + "data/conferences/";
            } else if (tools.OSValidator.isWindows()) {
                sc = root + "datasets\\";
                dirExpo = root + "data\\conferences\\";
            }
            daoJena.newConnection(sc + conf.getName());

            String filename = daoJena.exportToJson(dirExpo);
            String fileToexport = dirExpo + filename;
            returnFile(fileToexport, req, resp);
        }
    }

    @RequestMapping(value = "export/rdf/{idConf}", method = RequestMethod.GET)
    public void exportRdf(@PathVariable("idConf") String idConf, HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Conference conf = dao.getConfById(idConf);
        if (conf != null) {

            String sc = req.getServletContext().getRealPath("");
            String root = sc.substring(0, sc.length() - 28);
            String dirExpo = "";
            System.out.println("\nsc: " + sc + "\n");
            if (tools.OSValidator.isUnix()) {
                sc = root + "datasets/";
                dirExpo = root + "data/conferences/";
            } else if (tools.OSValidator.isWindows()) {
                sc = root + "datasets\\";
                dirExpo = root + "data\\conferences\\";
            }
            daoJena.newConnection(sc + conf.getName());

            String filename = daoJena.exportToRDF(dirExpo);
            String fileToexport = dirExpo + filename;
            returnFile(fileToexport, req, resp);

        }
    }

    private void returnFile(String export, HttpServletRequest req, HttpServletResponse resp) throws IOException {

        int DEFAULT_BUFFER_SIZE = 10240;

        System.out.println("export: " + export);
        if (export != null) {
            File file = new File(export);
            if (file.exists()) {

                String contentType = req.getServletContext().getMimeType(file.getName());
                if (contentType == null) {
                    contentType = "application/octet-stream";
                }

                // Init servlet response.
                resp.reset();
                resp.setContentType(contentType);
                resp.setHeader("Content-Length", String.valueOf(file.length()));
                resp.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

                // Prepare streams.
                BufferedInputStream input = null;
                BufferedOutputStream output = null;

                try {
                    // Open streams.
                    input = new BufferedInputStream(new FileInputStream(file), DEFAULT_BUFFER_SIZE);
                    output = new BufferedOutputStream(resp.getOutputStream(), DEFAULT_BUFFER_SIZE);

                    // Write file contents to response.
                    byte[] bytes = Files.readAllBytes(file.getAbsoluteFile().toPath());
                    output.write(bytes, 0, bytes.length);
                } finally {
                    // Gently close streams.
                    close(output);
                    close(input);
                }

            } else {
                System.out.println("File download null!");
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            System.out.println("Fichier json null!");
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                // Do your thing with the exception. Print it, log it or mail it.
                e.printStackTrace();
            }
        }
    }

}
