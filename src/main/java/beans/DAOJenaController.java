/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import model.jena.Category;
import model.jena.Event;
import model.jena.Location;
import model.jena.Organization;
import model.jena.Person;
import model.jena.Publication;
import org.apache.jena.graph.Graph;
import org.apache.jena.graph.Node;
import org.apache.jena.graph.NodeFactory;
import org.apache.jena.graph.Triple;
import org.apache.jena.query.Dataset;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.sparql.JenaTransactionException;
import org.apache.jena.sparql.core.DatasetGraph;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.util.FileManager;
import org.springframework.stereotype.Repository;

/**
 *
 * @author françois
 */
@Repository("DAOJenaController")
public class DAOJenaController {

    private DatasetGraph dataset;
    private String directory;

    public DAOJenaController() {
    }

    public DAOJenaController(String directory) {
        setDirectory(directory);
        dataset = TDBFactory.createDatasetGraph(this.directory);
    }

    public void importFromRDF(String file, String nameConf) {
        // Définit le chemin où est stocké le RDF
        String loc;
        if (tools.OSValidator.isUnix()) {
            loc = "data/conferences/" + file;
        } else if (tools.OSValidator.isWindows()) {
            loc = "data\\conferences\\" + file;
        } else {
            return;
        }

        setDirectory(nameConf);

        Model model = getModel();
        InputStream in = FileManager.get().open(loc);
        if (in == null) {
            throw new IllegalArgumentException("Fichier: " + loc + " non trouvé");
        }

        // lit le fichier RDF/XML
        model.read(in, null);

        // connecte à la base et ajoute le model
        try {
            Dataset dataset = TDBFactory.createDataset(loc);
            dataset.setDefaultModel(model);
            dataset.close();
            newConnection(directory);
//            Graph g = dataset.getDefaultGraph();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void printDataset() {
        Model m = getModel();
        m.write(System.out);
    }

    public void setDirectory(String directory) {
//        if(tools.OSValidator.isUnix()){
//            this.directory =  "datasets/" + directory ;
//        }
//        else if(tools.OSValidator.isWindows()){
//            this.directory =  "datasets\\" + directory;
//        }
        this.directory = directory;
    }

    public Model getModel() {
        dataset = TDBFactory.createDatasetGraph(this.directory);
        Model m = ModelFactory.createModelForGraph(dataset.getDefaultGraph());
        return m;
    }

    public void closeConnection() {
        dataset.close();
    }

    public void newConnection(String directory) {
        if (dataset != null && directory != null) {
            try {
                dataset.close();
            } catch (JenaTransactionException c) {
                System.err.println("dataset already close");
            } catch (Exception e) {
                System.err.println("dataset already close");
            }
        }
        setDirectory(directory);
        dataset = TDBFactory.createDatasetGraph(directory);
    }

    public void addCategory(Category c) {
        newConnection(directory);
        Graph g = dataset.getDefaultGraph();

        Node sujet = NodeFactory.createURI(c.getId());
        Node predicat;
        Node objet;

        if (c.getLabel() != null) {
            predicat = NodeFactory.createURI("http://www.w3.org/2000/01/rdf-schema#label");
            objet = NodeFactory.createLiteral(c.getLabel());
            g.add(Triple.create(sujet, predicat, objet));
        }

        if (c.getName() != null) {
            predicat = NodeFactory.createURI("http://xmlns.com/foaf/0.1/name");
            objet = NodeFactory.createLiteral(c.getName());
            g.add(Triple.create(sujet, predicat, objet));
        }

        dataset.close();
    }

    public void addEvent(Event e) {
        newConnection(directory);
        Graph g = dataset.getDefaultGraph();

        Node sujet = NodeFactory.createURI(e.getId());
        Node predicat;
        Node objet;

        //chair
        predicat = NodeFactory.createURI("http://data.semanticweb.org/ns/swc/ontology#heldby");
        if (e.getChair() != null) {
            objet = NodeFactory.createLiteral(e.getChair());

            Triple t = Triple.create(sujet, predicat, objet);
            g.add(t);
        }

//      ArrayList<Category> categories;
        predicat = NodeFactory.createURI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
        for (String c : e.getCategories()) {
            objet = NodeFactory.createLiteral(c);
            g.add(Triple.create(sujet, predicat, objet));
        }

//      Event children;
        if (e.getChildren() != null) {
            predicat = NodeFactory.createURI("http://data.semanticweb.org/ns/swc/ontology#isSuperEventOf");
            for (String ev : e.getChildren()) {
                objet = NodeFactory.createLiteral(ev);
                g.add(Triple.create(sujet, predicat, objet));
            }
        }

//      String createdAt;
        if (e.getCreatedAt() != null) {
            predicat = NodeFactory.createURI("http://www.w3.org/2002/12/cal/icaltzd#dtstamp");
            objet = NodeFactory.createLiteral(e.getCreatedAt());
            g.add(Triple.create(sujet, predicat, objet));
        }

//      String description;
        if (e.getDescription() != null) {
            predicat = NodeFactory.createURI("http://www.w3.org/2002/12/cal/icaltzd#description");
            objet = NodeFactory.createLiteral(e.getDescription());
            g.add(Triple.create(sujet, predicat, objet));
        }

//      String duration;                
        if (e.getDuration() != null) {
            predicat = NodeFactory.createURI("http://www.w3.org/2002/12/cal/icaltzd#duration");
            objet = NodeFactory.createLiteral(e.getDuration());
            g.add(Triple.create(sujet, predicat, objet));
        }

//      String endsAt;        
        if (e.getEndsAt() != null) {
            predicat = NodeFactory.createURI("http://www.w3.org/2002/12/cal/icaltzd#dtend");
            objet = NodeFactory.createLiteral(e.getEndsAt());
            g.add(Triple.create(sujet, predicat, objet));
        }

//      String homepage;        
        if (e.getHomepage() != null) {
            predicat = NodeFactory.createURI("http://xmlns.com/foaf/0.1/homepage");
            objet = NodeFactory.createLiteral(e.getHomepage());
            g.add(Triple.create(sujet, predicat, objet));
        }

//      String lastModifiedAt;    
        if (e.getLastModifiedAt() != null) {
            predicat = NodeFactory.createURI("http://www.w3.org/2002/12/cal/icaltzd#lastModified");
            objet = NodeFactory.createLiteral(e.getLastModifiedAt());
            g.add(Triple.create(sujet, predicat, objet));
        }

//      ArrayList<Location> locations;        
        predicat = NodeFactory.createURI("http://www.w3.org/2002/12/cal/icaltzd#location");
        for (String l : e.getLocations()) {
            objet = NodeFactory.createLiteral(l);
            g.add(Triple.create(sujet, predicat, objet));
        }

//      String name;        
        if (e.getName() != null) {
            predicat = NodeFactory.createURI("http://xmlns.com/foaf/0.1/name");
            objet = NodeFactory.createLiteral(e.getName());
            g.add(Triple.create(sujet, predicat, objet));
        }

//      ArrayList<Publication> papers;        
        predicat = NodeFactory.createURI("http://www.ontologydesignpatterns.org/ont/eswc/ontology.owl#presents");
        for (String p : e.getPapers()) {
            objet = NodeFactory.createLiteral(p);
            g.add(Triple.create(sujet, predicat, objet));
        }

//      Event parent;        
        if (e.getParent() != null) {
            predicat = NodeFactory.createURI("http://data.semanticweb.org/ns/swc/ontology#isSubEventOf");
            objet = NodeFactory.createLiteral(e.getParent());
            g.add(Triple.create(sujet, predicat, objet));
        }

//        private String Resource;
        //TODO
//      ArrayList<String> roles;        
        predicat = NodeFactory.createURI("http://data.semanticweb.org/ns/swc/ontology#hasRole");
        for (String r : e.getRoles()) {
            objet = NodeFactory.createLiteral(r);
            g.add(Triple.create(sujet, predicat, objet));
        }

//      String startsAt;        
        if (e.getStartsAt() != null) {
            predicat = NodeFactory.createURI("http://www.w3.org/2002/12/cal/icaltzd#dtstart");
            objet = NodeFactory.createLiteral(e.getStartsAt());
            g.add(Triple.create(sujet, predicat, objet));
        }

//      ArrayList<String> topics;        
        predicat = NodeFactory.createURI("http://purl.org/dc/elements/1.1/subject");
        for (String t : e.getTopics()) {
            objet = NodeFactory.createLiteral(t);
            g.add(Triple.create(sujet, predicat, objet));
        }

//      String twitterWidgetToken;
        if (e.getTwitterWidgetToken() != null) {
            predicat = NodeFactory.createURI("http://www.ontologydesignpatterns.org/ont/eswc/ontology.owl#hashtag");
            objet = NodeFactory.createLiteral(e.getTwitterWidgetToken());
            g.add(Triple.create(sujet, predicat, objet));
        }

    }

    public void addLocation(Location l) {
    newConnection(directory);
        Graph g = dataset.getDefaultGraph();

        Node sujet = NodeFactory.createURI(l.getId());

        if (l.getName() != null) {

            Node predicat = NodeFactory.createURI("http://xmlns.com/foaf/0.1/name");

            Node objet = NodeFactory.createLiteral(l.getName());

            g.add(Triple.create(sujet, predicat, objet));

        }

        dataset.close();

    }

    public void addOrganization(Organization o) {
        newConnection(directory);
        Graph g = dataset.getDefaultGraph();

        Node sujet;

        Node predicat;

        Node objet;

        //On initialise le sujet
        sujet = NodeFactory.createURI(o.getId());

        //Relation label
        if (o.getLabel() != null) {

            predicat = NodeFactory.createURI("http://www.w3.org/2000/01/rdf-schema#label");

            objet = NodeFactory.createLiteral(o.getLabel());

            //Ajout du label
            g.add(Triple.create(sujet, predicat, objet));

        }

        //Relation members
        predicat = NodeFactory.createURI("http://xmlns.com/foaf/0.1/member");

        for (String p : o.getMembers()) {

            objet = NodeFactory.createLiteral(p);

            //Ajout des membres
            g.add(Triple.create(sujet, predicat, objet));

        }

        dataset.close();

    }

    public void addPerson(Person p) {
        newConnection(directory);
        Graph g = dataset.getDefaultGraph();

        Node sujet;

        Node predicat;

        Node objet;

        //Initialisation du sujet
        sujet = NodeFactory.createURI(p.getId());

        //Account
        if (p.getAccounts() != null) {

            predicat = NodeFactory.createURI("http://xmlns.com/foaf/0.1/account");

            objet = NodeFactory.createLiteral(p.getAccounts());

            g.add(Triple.create(sujet, predicat, objet));

        }

        //Affiliation
        if (p.getAffiliation() != null) {

            predicat = NodeFactory.createURI("http://swrc.ontoware.org/ontology#affiliation");

            objet = NodeFactory.createLiteral(p.getAffiliation());

            g.add(Triple.create(sujet, predicat, objet));

        }

        //Depiction
        // ??
        //HoldsRole
        predicat = NodeFactory.createURI("http://data.semanticweb.org/ns/swc/ontology#holdsRole");

        for (String s : p.getHoldsRole()) {

            objet = NodeFactory.createLiteral(s);

            g.add(Triple.create(sujet, predicat, objet));

        }

        //made
        predicat = NodeFactory.createURI("http://xmlns.com/foaf/0.1/made");

        for (String pub : p.getMade()) {

            objet = NodeFactory.createLiteral(pub);

            g.add(Triple.create(sujet, predicat, objet));

        }

        //mbox
        if (p.getMbox() != null) {

            predicat = NodeFactory.createURI("http://xmlns.com/foaf/0.1/mbox_sha1sum");

            objet = NodeFactory.createLiteral(p.getMbox());

            g.add(Triple.create(sujet, predicat, objet));

        }

        //name
        if (p.getName() != null) {

            predicat = NodeFactory.createURI("http://xmlns.com/foaf/0.1/name");

            objet = NodeFactory.createLiteral(p.getName());

            g.add(Triple.create(sujet, predicat, objet));

        }

        //websites
        predicat = NodeFactory.createURI("http://xmlns.com/foaf/0.1/homepage");

        for (String s : p.getWebsites()) {

            objet = NodeFactory.createLiteral(s);

            g.add(Triple.create(sujet, predicat, objet));

        }

        dataset.close();

    }

    public void addPublication(Publication p) {
        newConnection(directory);
        Graph g = dataset.getDefaultGraph();

        Node sujet;

        Node predicat;

        Node objet;

        //Initialisation du sujet
        sujet = NodeFactory.createURI(p.getId());

        //abstract
        if (p.getAbs() != null) {

            predicat = NodeFactory.createURI("http://swrc.ontoware.org/ontology#abstract");

            objet = NodeFactory.createLiteral(p.getAbs());

            g.add(Triple.create(sujet, predicat, objet));

        }

        //authors
        predicat = NodeFactory.createURI("http://xmlns.com/foaf/0.1/member");

        for (String per : p.getAuthors()) {

            objet = NodeFactory.createLiteral(per);

            g.add(Triple.create(sujet, predicat, objet));

        }

        //hashtag
        if (p.getHashtag() != null) {

            predicat = NodeFactory.createURI("http://www.ontologydesignpatterns.org/ont/eswc/ontology.owl#hashtag");

            objet = NodeFactory.createLiteral(p.getHashtag());

            g.add(Triple.create(sujet, predicat, objet));

        }

        //presentedIn
        predicat = NodeFactory.createURI("http://www.ontologydesignpatterns.org/ont/eswc/ontology.owl#isPresented");

        for (String e : p.getPresentedIn()) {

            objet = NodeFactory.createLiteral(e);

            g.add(Triple.create(sujet, predicat, objet));

        }

        //thumbnail
        // ??
        //title 
        if (p.getTitle() != null) {

            predicat = NodeFactory.createURI("http://purl.org/dc/elements/1.1/title");

            objet = NodeFactory.createLiteral(p.getTitle());

            g.add(Triple.create(sujet, predicat, objet));

        }

        //track
        // ??
        //ui
        // ??
        dataset.close();

    }

    public String exportToJson(String path) {

        Model m = getModel();

        String fileLoc = "";

        // Recupère le nom de la conférence et définit le chemin
        String fileName = "";
        if (tools.OSValidator.isUnix()) {

            fileName = directory.substring(directory.lastIndexOf("/") + 1) + ".json";
            fileLoc = path + fileName;

        } else if (tools.OSValidator.isWindows()) {

            fileName = directory.substring(directory.lastIndexOf("\\") + 1) + ".json";
            fileLoc = path + fileName;
        }
        // Exporte la conférence au format JSON
        File file = new File(fileLoc);
        file.getParentFile().mkdirs();
//        try (PrintWriter fileJson = new PrintWriter(new FileWriter(fileLoc))) {
        try (PrintWriter fileJson = new PrintWriter(file)) {

            m.write(fileJson, "JSON-LD");
            dataset.close();
            return fileName;

        } catch (Exception e) {

            System.out.println(e);
            dataset.close();
            return null;

        }

    }

    public String exportToRDF(String path) {

        Model m = getModel();

        String fileLoc = "";

        // Recupère le nom de la conférence et définit le chemin
        String fileName = "";
        if (tools.OSValidator.isUnix()) {

            fileName = directory.substring(directory.lastIndexOf("/") + 1) + ".rdf";
            fileLoc = path + fileName;

        } else if (tools.OSValidator.isWindows()) {

            fileName = directory.substring(directory.lastIndexOf("\\") + 1) + ".rdf";
            fileLoc = path + fileName;
        }

        // Exporte la conférence au format RDF
        File file = new File(fileLoc);
        file.getParentFile().mkdirs();
//        try (PrintWriter fileJson = new PrintWriter(new FileWriter(fileLoc))) {
        try (PrintWriter fileJson = new PrintWriter(file)) {

            m.write(fileJson, "RDF/XML");
            dataset.close();
            return fileName;

        } catch (Exception e) {

            System.out.println(e);
            dataset.close();
            return null;

        }

    }

    public ArrayList<Category> getAllCategories() {

        Model m = getModel();

        Map<String, Category> resMap = new HashMap<>();

        StmtIterator it = m.listStatements();

        while (it.hasNext()) {

            Statement s = it.nextStatement();

            Resource sujet = s.getSubject();

            Property predicat = s.getPredicate();

            RDFNode objet = s.getObject();

            String[] tmp = sujet.toString().split("/");

            if (tmp[tmp.length - 2].equals("category")) {

                Category cTmp = resMap.get(sujet.toString());

                if (cTmp == null) {

                    cTmp = new Category(sujet.toString());

                }

                if (predicat.toString().equals("http://www.w3.org/2000/01/rdf-schema#label")) {

                    cTmp.setLabel(objet.toString());

                }

                if (predicat.toString().equals("http://xmlns.com/foaf/0.1/name")) {

                    cTmp.setName(objet.toString());

                }

                resMap.put(sujet.toString(), cTmp);

            }

        }

        ArrayList<Category> res = new ArrayList<>(resMap.values());

        return res;

    }

    public Category getCategory(String id) {

        ArrayList<Category> res = getAllCategories();

        for (Category r : res) {

            if (r.getId().equals(id)) {

                return r;

            }

        }

        return null;

    }

    public ArrayList<Event> getAllEvents() {

        Model m = getModel();

        Map<String, Event> resMap = new HashMap<>();

        StmtIterator it = m.listStatements();

        while (it.hasNext()) {

            try {
                Statement s = it.nextStatement();

                Resource sujet = s.getSubject();

                Property predicat = s.getPredicate();

                RDFNode objet = s.getObject();

                String[] tmp = sujet.toString().split("/");

                if (tmp[tmp.length - 2].equals("event")) {

                    Event eTmp = resMap.get(sujet.toString());

                    if (eTmp == null) {

                        String[] idEv = sujet.toString().split("/");

                        eTmp = new Event(idEv[idEv.length - 1]);

                    }

//      chair        
                    if (predicat.toString().equals("http://data.semanticweb.org/ns/swc/ontology#heldby")) {

                        eTmp.setChair(objet.toString());

                    }

//      ArrayList<String> categories;        
                    if (predicat.toString().equals("http://www.w3.org/1999/02/22-rdf-syntax-ns#type")) {

                        eTmp.getCategories().add(objet.toString());

                    }

//      String children;
                    if (predicat.toString().equals("http://data.semanticweb.org/ns/swc/ontology#isSuperEventOf")) {

                        eTmp.getChildren().add(objet.toString());

                    }

//      String createdAt;
                    if (predicat.toString().equals("http://www.w3.org/2002/12/cal/icaltzd#dtstamp")) {

                        eTmp.setCreatedAt(objet.toString());

                    }

//      String description;
                    if (predicat.toString().equals("http://www.w3.org/2002/12/cal/icaltzd#description")) {

                        eTmp.setDescription(objet.toString());

                    }

//      String duration;                
                    if (predicat.toString().equals("http://www.w3.org/2002/12/cal/icaltzd#duration")) {

                        eTmp.setDuration(objet.toString());

                    }

//      String endsAt;        
                    if (predicat.toString().equals("http://www.w3.org/2002/12/cal/icaltzd#dtend")) {

                        eTmp.setEndsAt(objet.toString());

                    }

//      String homepage;        
                    if (predicat.toString().equals("http://xmlns.com/foaf/0.1/homepage")) {

                        eTmp.setHomepage(objet.toString());

                    }

//      String lastModifiedAt;    
                    if (predicat.toString().equals("http://www.w3.org/2002/12/cal/icaltzd#lastModified")) {

                        eTmp.setLastModifiedAt(objet.toString());

                    }

//      ArrayList<Location> locations;        
                    if (predicat.toString().equals("http://www.w3.org/2002/12/cal/icaltzd#location")) {

                        eTmp.getLocations().add(objet.toString());

                    }

//      String name;        
                    if (predicat.toString().equals("http://xmlns.com/foaf/0.1/name")) {

                        eTmp.setName(objet.toString());

                    }

//      ArrayList<Publication> papers;        
                    if (predicat.toString().equals("http://www.ontologydesignpatterns.org/ont/eswc/ontology.owl#presents")) {

                        eTmp.getPapers().add(objet.toString());

                    }

//      Event parent;        
                    if (predicat.toString().equals("http://data.semanticweb.org/ns/swc/ontology#isSubEventOf")) {

                        eTmp.setParent(objet.toString());

                    }

//        private String Resource;
                    //TODO
//      ArrayList<String> roles;        
                    if (predicat.toString().equals("http://data.semanticweb.org/ns/swc/ontology#hasRole")) {

                        eTmp.getRoles().add(objet.toString());

                    }

//      String startsAt;        
                    if (predicat.toString().equals("http://www.w3.org/2002/12/cal/icaltzd#dtstart")) {

                        eTmp.setStartsAt(objet.toString());

                    }

//      ArrayList<String> topics;        
                    if (predicat.toString().equals("http://purl.org/dc/elements/1.1/subject")) {

                        eTmp.getTopics().add(objet.toString());

                    }

//      String twitterWidgetToken;
                    if (predicat.toString().equals("http://www.ontologydesignpatterns.org/ont/eswc/ontology.owl#hashtag")) {

                        eTmp.setTwitterWidgetToken(objet.toString());

                    }

                    resMap.put(sujet.toString(), eTmp);

                }
            } catch (org.apache.jena.atlas.lib.InternalErrorException ex) {
                System.err.println(ex);
            }

        }

        ArrayList<Event> res = new ArrayList<>(resMap.values());

        return res;

    }

    public Event getEvent(String id) {

        ArrayList<Event> res = getAllEvents();
        for (Event r : res) {
            System.out.println(r.getId());
            if (r.getId().equals(id)) {
                return r;
            }
        }
        return null;
    }

    public ArrayList<Location> getAllLocations() {

        Model m = getModel();

        Map<String, Location> resMap = new HashMap<>();

        StmtIterator it = m.listStatements();

        while (it.hasNext()) {

            Statement s = it.nextStatement();

            Resource sujet = s.getSubject();

            Property predicat = s.getPredicate();

            RDFNode objet = s.getObject();

            String[] tmp = sujet.toString().split("/");

            if (tmp[tmp.length - 2].equals("location")) {

                Location lTmp = resMap.get(sujet.toString());

                if (lTmp == null) {

                    lTmp = new Location(sujet.toString());

                }

                if (predicat.toString().equals("http://xmlns.com/foaf/0.1/name")) {

                    lTmp.setName(objet.toString());

                }

                resMap.put(sujet.toString(), lTmp);

            }

        }

        ArrayList<Location> res = new ArrayList<>(resMap.values());

        return res;

    }

    public Location getLocation(String id) {

        ArrayList<Location> res = getAllLocations();

        for (Location r : res) {

            if (r.getId().equals(id)) {

                return r;

            }

        }

        return null;

    }

    public ArrayList<Organization> getAllOrganizations() {

        Model m = getModel();

        Map<String, Organization> resMap = new HashMap<>();

        StmtIterator it = m.listStatements();

        while (it.hasNext()) {

            Statement s = it.nextStatement();

            Resource sujet = s.getSubject();

            Property predicat = s.getPredicate();

            RDFNode objet = s.getObject();

            String[] tmp = sujet.toString().split("/");

            if (tmp[tmp.length - 2].equals("organization")) {

                Organization oTmp = resMap.get(sujet.toString());

                if (oTmp == null) {

                    oTmp = new Organization(sujet.toString());

                }

                if (predicat.toString().equals("http://www.w3.org/2000/01/rdf-schema#label")) {

                    oTmp.setLabel(objet.toString());

                }

                if (predicat.toString().equals("http://xmlns.com/foaf/0.1/member")) {

                    oTmp.getMembers().add(objet.toString());

                }

                resMap.put(sujet.toString(), oTmp);

            }

        }

        ArrayList<Organization> res = new ArrayList<>(resMap.values());

        return res;

    }

    public Organization getOrganization(String id) {

        ArrayList<Organization> res = getAllOrganizations();

        for (Organization r : res) {

            if (r.getId().equals(id)) {

                return r;

            }

        }

        return null;

    }

    public ArrayList<Person> getAllPersons() {

        Model m = getModel();

        Map<String, Person> resMap = new HashMap<>();

        StmtIterator it = m.listStatements();

        while (it.hasNext()) {

            Statement s = it.nextStatement();

            Resource sujet = s.getSubject();

            Property predicat = s.getPredicate();

            RDFNode objet = s.getObject();

            String[] tmp = sujet.toString().split("/");

            if (tmp[tmp.length - 2].equals("person")) {

                Person pTmp = resMap.get(sujet.toString());

                if (pTmp == null) {

                    String[] idtmp = sujet.toString().split("/");

                    pTmp = new Person(idtmp[idtmp.length - 1]);

                }

                if (predicat.toString().equals("http://xmlns.com/foaf/0.1/name")) {

                    pTmp.setName(objet.toString());

                }

                if (predicat.toString().equals("http://swrc.ontoware.org/ontology#affiliation")) {

                    pTmp.setAffiliation(objet.toString());

                }

                //Depiction ??
                if (predicat.toString().equals("http://data.semanticweb.org/ns/swc/ontology#holdsRole")) {

                    pTmp.getHoldsRole().add(objet.toString());

                }

                if (predicat.toString().equals("http://xmlns.com/foaf/0.1/made")) {

                    pTmp.getMade().add(objet.toString());

                }

                if (predicat.toString().equals("http://xmlns.com/foaf/0.1/mbox_sha1sum")) {

                    pTmp.setMbox(objet.toString());

                }

                if (predicat.toString().equals("http://xmlns.com/foaf/0.1/homepage")) {

                    pTmp.getWebsites().add(objet.toString());

                }

                resMap.put(sujet.toString(), pTmp);

            }

        }

        ArrayList<Person> res = new ArrayList<>(resMap.values());

        return res;

    }

    public Person getPerson(String id) {

        ArrayList<Person> res = getAllPersons();

        for (Person r : res) {

            if (r.getId().equals(id)) {

                return r;

            }

        }

        return null;

    }

    public ArrayList<Publication> getAllPublication() {

        Model m = getModel();

        Map<String, Publication> resMap = new HashMap<>();

        StmtIterator it = m.listStatements();

        while (it.hasNext()) {

            Statement s = it.nextStatement();

            Resource sujet = s.getSubject();

            Property predicat = s.getPredicate();

            RDFNode objet = s.getObject();

            String[] tmp = sujet.toString().split("/");

            if (tmp[tmp.length - 2].equals("paper")) {

                Publication pTmp = resMap.get(sujet.toString());

                if (pTmp == null) {

                    pTmp = new Publication(sujet.toString());

                }

                if (predicat.toString().equals("http://swrc.ontoware.org/ontology#abstract")) {

                    pTmp.setAbs(objet.toString());

                }

                if (predicat.toString().equals("http://xmlns.com/foaf/0.1/member")) {

                    pTmp.getAuthors().add(objet.toString());

                }

                if (predicat.toString().equals("http://www.ontologydesignpatterns.org/ont/eswc/ontology.owl#hashtag")) {

                    pTmp.setHashtag(objet.toString());

                }

                if (predicat.toString().equals("http://www.ontologydesignpatterns.org/ont/eswc/ontology.owl#isPresented")) {

                    pTmp.getPresentedIn().add(objet.toString());

                }

                //thumbnail ??  
                if (predicat.toString().equals("http://purl.org/dc/elements/1.1/title")) {

                    pTmp.setTitle(objet.toString());

                }

                //track ??
                //ui ??
                resMap.put(sujet.toString(), pTmp);

            }

        }

        ArrayList<Publication> res = new ArrayList<>(resMap.values());

        return res;

    }

    public Publication getPublication(String id) {

        ArrayList<Publication> res = getAllPublication();

        for (Publication r : res) {

            if (r.getId().equals(id)) {

                return r;

            }

        }

        return null;

    }

    public void clearDataset() {

        dataset.clear();

    }

    public static void main(String[] args) {

        DAOJenaController jena = new DAOJenaController("MyLittlePoney");

//        jena.clearDataset();
//        jena.importFromRDF("complete.rdf", "conf2");
        jena.printDataset();

//        Category c = new Category("http://data.semanticweb.org/conference/category/test");
//        c.setLabel("testLabel");
//        c.setName("testName");
//        jena.addCategory(c);
//        
//        Category c2 = new Category("http://data.semanticweb.org/conference/category/shmilblick");
//        c2.setLabel("Ma categorie");
//        c2.setName("Les toilettes");
//        jena.addCategory(c2);
//        
//        Location l = new Location("http://data.semanticweb.org/conference/multimif/2015/location/testLoc");        
//        l.setName("testLocName");
//        jena.addLocation(l);
//        
//        Location l2 = new Location("http://data.semanticweb.org/conference/multimif/2015/location/testLoc2");        
//        l2.setName("testLocName2");
//        jena.addLocation(l2);
//        
//        Event e = new Event("http://data.semanticweb.org/conference/eswc/2015/event/testEvent");
//        e.setName("lol");
//        jena.addEvent(e);
//        
//        Event e2 = new Event("http://data.semanticweb.org/conference/eswc/2015/event/testEvent2");
//        e2.setName("mdr");
//        jena.addEvent(e2);
////       
////        Organization o1 = new Organization("http://data.semanticweb.org/organization/ucbl");
////        o1.setLabel("UCBL");
////        o1.getMembers().add("http://data.semanticweb.org/person/antoine-gaget");
////        jena.addOrganization(o1);
////        
////        Organization o2 = new Organization("http://data.semanticweb.org/organization/DOTA");
////        o2.setLabel("DOTA");
////        o2.getMembers().add("http://data.semanticweb.org/person/antoine-gaget");
////        jena.addOrganization(o2);
////        
////        Person p = new Person("http://data.semanticweb.org/person/antoine-gaget");
////        p.setName("Antoine Gaget");
////        p.getWebsites().add("www.mimi.com");
////        p.getWebsites().add("www.momo.com");
////        p.getWebsites().add("www.mama.com");
////        jena.addPerson(p);
////        
////        Publication pub1 = new Publication("http://data.semanticweb.org/conference/multimif/2015/paper/publi");
////        pub1.setTitle("Cation");
////        pub1.getAuthors().add("http://data.semanticweb.org/person/antoine-gaget");
////        pub1.getPresentedIn().add("http://data.semanticweb.org/conference/eswc/2015/session/testEvent");
////        jena.addPublication(pub1);
////        
////        Publication pub2 = new Publication("http://data.semanticweb.org/conference/multimif/2015/paper/publi2");
////        pub2.setTitle("Cation2");
////        pub2.getAuthors().add("http://data.semanticweb.org/person/antoine-gaget");
////        pub2.getPresentedIn().add("http://data.semanticweb.org/conference/eswc/2015/session/testEvent");
////        jena.addPublication(pub2);
//        
//        jena.printDataset();
//        jena.exportToJson();
//        ArrayList<Event> list = jena.getAllEvents();
//        for (Event r : list) {
//            System.out.println(r.toString());
//        }        
        jena.closeConnection();

        //jena.newConnection("conf2");
        //jena.printDataset();  
    }

}
