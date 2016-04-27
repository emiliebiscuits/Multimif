/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.jena;

import java.util.ArrayList;
import java.util.Arrays;
import model.Conference;

/**
 *
 * @author Clément
 */
public class Event {
    private String chair;
    private ArrayList<String> categories;
    private ArrayList<String> children;
    private String createdAt;
    private String description;
    private String duration;
    private String endsAt;
    private String homepage;
    private String id;
    private String lastModifiedAt;
    private ArrayList<String> locations;
    private String name;
    private ArrayList<String> papers;
    private String parent;
    private String Resource;
    private ArrayList<String> roles;
    private String startsAt;
    private ArrayList<String> topics;
    private String twitterWidgetToken;

    public Event(String id) {
        this.id = "http://data.semanticweb.org/conference/eswc/2015/event/" + id;
        this.categories = new ArrayList<>();
        this.locations = new ArrayList<>();
        this.papers = new ArrayList<>();
        this.roles = new ArrayList<>();
        this.topics = new ArrayList<>(); 
        this.children = new ArrayList<>(); 
    }    
    
    public Event(Event e){
        this.chair = e.chair;
        this.categories = new ArrayList<>(e.categories);
        this.children = new ArrayList<>(e.children);
        this.createdAt = e.createdAt;
        this.description = e.description;
        this.duration = e.duration;
        this.endsAt = e.endsAt;
        this.homepage = e.homepage;
        this.id = e.id;
        this.lastModifiedAt = e.lastModifiedAt;
        this.locations = new ArrayList<>(e.locations);
        this.name = e.name;
        this.papers = new ArrayList<>(e.papers);
        this.parent = e.parent;
        this.Resource = e.Resource;
        this.roles = new ArrayList<>(e.roles);
        this.startsAt = e.startsAt;
        this.topics = new ArrayList<>(e.topics);
        this.twitterWidgetToken = e.twitterWidgetToken;
    }

    public Event(Conference c){
        this.id = "http://data.semanticweb.org/conference/eswc/2015/event/" + c.getName();
        this.categories = new ArrayList<>();
        this.locations = new ArrayList<>();
        this.papers = new ArrayList<>();
        this.roles = new ArrayList<>();
        this.topics = new ArrayList<>(); 
        this.children = new ArrayList<>();
        
        chair = "http://data.semanticweb.org/person/" + c.getDirector().getMail();
        name = c.getName();
        String[] temp = c.getTheme().split(",");
        topics.addAll(Arrays.asList(temp));
        description = c.getDescription();
        createdAt = c.getDate().toString();
    }
    
    public Event(model.Event e){
        this.id = "http://data.semanticweb.org/conference/eswc/2015/event/" + e.getUri();
        this.categories = new ArrayList<>();
        this.locations = new ArrayList<>();
        this.papers = new ArrayList<>();
        this.roles = new ArrayList<>();
        this.topics = new ArrayList<>(); 
        this.children = new ArrayList<>();
        name = e.getUri();
        chair = "http://data.semanticweb.org/person/unknow";
        description = "";
    }
    
    public void updateId() {
        this.id = "http://data.semanticweb.org/conference/eswc/2015/event/" + name;
    }
    
    public void setChair(String chair) {
        this.chair = chair;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public void setChildren(ArrayList<String> children) {
        this.children = children;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setEndsAt(String endsAt) {
        this.endsAt = endsAt;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public void setLastModifiedAt(String lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    public void setLocations(ArrayList<String> locations) {
        this.locations = locations;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPapers(ArrayList<String> papers) {
        this.papers = papers;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public void setResource(String Resource) {
        this.Resource = Resource;
    }

    public void setRoles(ArrayList<String> roles) {
        this.roles = roles;
    }
    public void addRole(String role) {
        this.roles.add(role);
    }

    public void setStartsAt(String startsAt) {
        this.startsAt = startsAt;
    }

    public void setTopics(ArrayList<String> topics) {
        this.topics = topics;
    }

    public void setTwitterWidgetToken(String twitterWidgetToken) {
        this.twitterWidgetToken = twitterWidgetToken;
    }

    public String getChair() {
        return chair;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }
    public String getCategoriesString() {
        String retour = "";
        int index = 0;
        for(String t:getCategories()){
            retour += t;
            if(index < getCategories().size()-1){
                retour += ",";
            }
            index++;
        }
        return retour;
    }

    public ArrayList<String> getChildren() {
        return children;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getDescription() {
        return description;
    }

    public String getDuration() {
        return duration;
    }

    public String getEndsAt() {
        return endsAt;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getId() {
        return id;
    }

    public String getLastModifiedAt() {
        return lastModifiedAt;
    }

    public ArrayList<String> getLocations() {
        return locations;
    }
    public String getLocationsString() {
        String retour = "";
        int index = 0;
        for(String t:getLocations()){
            retour += t;
            if(index < getLocations().size()-1){
                retour += ",";
            }
            index++;
        }
        return retour;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getPapers() {
        return papers;
    }

    public String getParent() {
        return parent;
    }

    public String getResource() {
        return Resource;
    }

    public ArrayList<String> getRoles() {
        return roles;
    }

    public String getStartsAt() {
        return startsAt;
    }

    public ArrayList<String> getTopics() {
        return topics;
    }
    public String getTopicsString() {
        String retour = "";
        int index = 0;
        for(String t:getTopics()){
            retour += t;
            if(index < getTopics().size()-1){
                retour += ",";
            }
            index++;
        }
        return retour;
    }

    public String getTwitterWidgetToken() {
        return twitterWidgetToken;
    }

    @Override
    public String toString() {
        return "Event{" + "chair=" + chair + ", categories=" + categories + ", children=" + children + ", createdAt=" + createdAt + ", description=" + description + ", duration=" + duration + ", endsAt=" + endsAt + ", homepage=" + homepage + ", id=" + id + ", lastModifiedAt=" + lastModifiedAt + ", locations=" + locations + ", name=" + name + ", papers=" + papers + ", parent=" + parent + ", Resource=" + Resource + ", roles=" + roles + ", startsAt=" + startsAt + ", topics=" + topics + ", twitterWidgetToken=" + twitterWidgetToken + '}';
    }
}
