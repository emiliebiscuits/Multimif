/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.jena;

import java.util.ArrayList;

/**
 *
 * @author Clément
 */
public class Publication {
    private String abs;
    private ArrayList<String> authors;
    private String hashtag;
    private String id;
    private ArrayList<String> presentedIn;
    private String thumbnail;   
    private String title;
    private String track;
    private String uri;

    /**
     * Constructeur de base de Publication
     * @param id L'id de la Publication
     */
    public Publication(String id) {
        this.id     = "http://data.semanticweb.org/conference/eswc/2015/paper/" + id;
        authors     = new ArrayList<>();
        presentedIn = new ArrayList<>();
    }
    
    /**
     * Constructeur par copie de Publication
     * @param p la Publication à copier
     */
    public Publication(Publication p){
        this.abs         = p.abs;
        this.authors     = new ArrayList<>(p.authors);
        this.hashtag     = p.hashtag;
        this.id          = p.id;
        this.presentedIn = new ArrayList<>(p.presentedIn);
        this.thumbnail   = p.thumbnail;
        this.title       = p.title;
        this.track       = p.track;
        this.uri         = p.uri;
    }
    
    public Publication(model.Publication p){
        this.id     = "http://data.semanticweb.org/conference/eswc/2015/paper/" + p.getUri();
        authors     = new ArrayList<>();
        presentedIn = new ArrayList<>();
        title = p.getUri();
        presentedIn.add(p.getIdEvent()+"");
        uri = p.getUri();
        abs = p.getDescription();
    }

    public void setAbs(String abs) {
        this.abs = abs;
    }

    public void setAuthors(ArrayList<String> authors) {
        this.authors = authors;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public void setPresentedIn(ArrayList<String> presentedIn) {
        this.presentedIn = presentedIn;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public String getAbs() {
        return abs;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public String getHashtag() {
        return hashtag;
    }

    public String getId() {
        return id;
    }

    public ArrayList<String> getPresentedIn() {
        return presentedIn;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public String getTrack() {
        return track;
    }

    public String getUri() {
        return uri;
    }

    @Override
    public String toString() {
        return "Publication{" + "abs=" + abs + ", authors=" + authors + ", hashtag=" + hashtag + ", id=" + id + ", presentedIn=" + presentedIn + ", thumbnail=" + thumbnail + ", title=" + title + ", track=" + track + ", uri=" + uri + '}';
    }
    
    
}
