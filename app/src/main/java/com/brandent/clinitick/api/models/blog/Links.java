package com.brandent.clinitick.api.models.blog;

import java.util.List;

public class Links {
    private List<Self> self;
    private List<Collection> collection;
    private List<About> about;
    private List<Author> author;
    private List<Reply> replies;
    private List<VersionHistory> versionHistory;
    private List<PredecessorVersion> predecessorVersion;
    private List<WpFeaturedmedia> wpFeaturedmedia;
    private List<WpAttachment> wpAttachment;
    private List<WpTerm> wpTerm;
    private List<Cury> curies;

    public Links(List<Self> self, List<Collection> collection, List<About> about, List<Author> author
            , List<Reply> replies, List<VersionHistory> versionHistory
            , List<PredecessorVersion> predecessorVersion, List<WpFeaturedmedia> wpFeaturedmedia
            , List<WpAttachment> wpAttachment, List<WpTerm> wpTerm, List<Cury> curies) {
        this.self = self;
        this.collection = collection;
        this.about = about;
        this.author = author;
        this.replies = replies;
        this.versionHistory = versionHistory;
        this.predecessorVersion = predecessorVersion;
        this.wpFeaturedmedia = wpFeaturedmedia;
        this.wpAttachment = wpAttachment;
        this.wpTerm = wpTerm;
        this.curies = curies;
    }

    public List<Self> getSelf() {
        return self;
    }

    public void setSelf(List<Self> self) {
        this.self = self;
    }

    public List<Collection> getCollection() {
        return collection;
    }

    public void setCollection(List<Collection> collection) {
        this.collection = collection;
    }

    public List<About> getAbout() {
        return about;
    }

    public void setAbout(List<About> about) {
        this.about = about;
    }

    public List<Author> getAuthor() {
        return author;
    }

    public void setAuthor(List<Author> author) {
        this.author = author;
    }

    public List<Reply> getReplies() {
        return replies;
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }

    public List<VersionHistory> getVersionHistory() {
        return versionHistory;
    }

    public void setVersionHistory(List<VersionHistory> versionHistory) {
        this.versionHistory = versionHistory;
    }

    public List<PredecessorVersion> getPredecessorVersion() {
        return predecessorVersion;
    }

    public void setPredecessorVersion(List<PredecessorVersion> predecessorVersion) {
        this.predecessorVersion = predecessorVersion;
    }

    public List<WpFeaturedmedia> getWpFeaturedmedia() {
        return wpFeaturedmedia;
    }

    public void setWpFeaturedmedia(List<WpFeaturedmedia> wpFeaturedmedia) {
        this.wpFeaturedmedia = wpFeaturedmedia;
    }

    public List<WpAttachment> getWpAttachment() {
        return wpAttachment;
    }

    public void setWpAttachment(List<WpAttachment> wpAttachment) {
        this.wpAttachment = wpAttachment;
    }

    public List<WpTerm> getWpTerm() {
        return wpTerm;
    }

    public void setWpTerm(List<WpTerm> wpTerm) {
        this.wpTerm = wpTerm;
    }

    public List<Cury> getCuries() {
        return curies;
    }

    public void setCuries(List<Cury> curies) {
        this.curies = curies;
    }
}
