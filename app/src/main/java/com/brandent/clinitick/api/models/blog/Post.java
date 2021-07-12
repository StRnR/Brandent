package com.brandent.clinitick.api.models.blog;

public class Post {
    private int id;
    //    private Date date;
//    private Date date_gmt;
//    private Guid guid;
//    private Date modified;
//    private Date modified_gmt;
//    private String slug;
//    private String status;
//    private String type;
//    private String link;
    private Title title;
    //    private Content content;
//    private Excerpt excerpt;
//    private int author;
    private int featured_media;
//    private String comment_status;
//    private String ping_status;
//    private boolean sticky;
//    private String template;
//    private String format;
//    private List<Object> meta;
//    private List<Integer> categories;
//    private List<Integer> tags;
//    private Links _links;

    public Post(int id, Title title, int featured_media) {
        this.id = id;
        this.title = title;
        this.featured_media = featured_media;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public int getFeatured_media() {
        return featured_media;
    }

    public void setFeatured_media(int featured_media) {
        this.featured_media = featured_media;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //    public Post(int id, Date date, Date date_gmt, Guid guid, Date modified, Date modified_gmt
//            , String slug, String status, String type, String link, Title title, Content content
//            , Excerpt excerpt, int author, int featured_media, String comment_status
//            , String ping_status, boolean sticky, String template, String format, List<Object> meta
//            , List<Integer> categories, List<Integer> tags, Links _links) {
//        this.id = id;
//        this.date = date;
//        this.date_gmt = date_gmt;
//        this.guid = guid;
//        this.modified = modified;
//        this.modified_gmt = modified_gmt;
//        this.slug = slug;
//        this.status = status;
//        this.type = type;
//        this.link = link;
//        this.title = title;
//        this.content = content;
//        this.excerpt = excerpt;
//        this.author = author;
//        this.featured_media = featured_media;
//        this.comment_status = comment_status;
//        this.ping_status = ping_status;
//        this.sticky = sticky;
//        this.template = template;
//        this.format = format;
//        this.meta = meta;
//        this.categories = categories;
//        this.tags = tags;
//        this._links = _links;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public Date getDate() {
//        return date;
//    }
//
//    public void setDate(Date date) {
//        this.date = date;
//    }
//
//    public Date getDate_gmt() {
//        return date_gmt;
//    }
//
//    public void setDate_gmt(Date date_gmt) {
//        this.date_gmt = date_gmt;
//    }
//
//    public Guid getGuid() {
//        return guid;
//    }
//
//    public void setGuid(Guid guid) {
//        this.guid = guid;
//    }
//
//    public Date getModified() {
//        return modified;
//    }
//
//    public void setModified(Date modified) {
//        this.modified = modified;
//    }
//
//    public Date getModified_gmt() {
//        return modified_gmt;
//    }
//
//    public void setModified_gmt(Date modified_gmt) {
//        this.modified_gmt = modified_gmt;
//    }
//
//    public String getSlug() {
//        return slug;
//    }
//
//    public void setSlug(String slug) {
//        this.slug = slug;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public String getLink() {
//        return link;
//    }
//
//    public void setLink(String link) {
//        this.link = link;
//    }
//
//    public Title getTitle() {
//        return title;
//    }
//
//    public void setTitle(Title title) {
//        this.title = title;
//    }
//
//    public Content getContent() {
//        return content;
//    }
//
//    public void setContent(Content content) {
//        this.content = content;
//    }
//
//    public Excerpt getExcerpt() {
//        return excerpt;
//    }
//
//    public void setExcerpt(Excerpt excerpt) {
//        this.excerpt = excerpt;
//    }
//
//    public int getAuthor() {
//        return author;
//    }
//
//    public void setAuthor(int author) {
//        this.author = author;
//    }
//
//    public int getFeatured_media() {
//        return featured_media;
//    }
//
//    public void setFeatured_media(int featured_media) {
//        this.featured_media = featured_media;
//    }
//
//    public String getComment_status() {
//        return comment_status;
//    }
//
//    public void setComment_status(String comment_status) {
//        this.comment_status = comment_status;
//    }
//
//    public String getPing_status() {
//        return ping_status;
//    }
//
//    public void setPing_status(String ping_status) {
//        this.ping_status = ping_status;
//    }
//
//    public boolean isSticky() {
//        return sticky;
//    }
//
//    public void setSticky(boolean sticky) {
//        this.sticky = sticky;
//    }
//
//    public String getTemplate() {
//        return template;
//    }
//
//    public void setTemplate(String template) {
//        this.template = template;
//    }
//
//    public String getFormat() {
//        return format;
//    }
//
//    public void setFormat(String format) {
//        this.format = format;
//    }
//
//    public List<Object> getMeta() {
//        return meta;
//    }
//
//    public void setMeta(List<Object> meta) {
//        this.meta = meta;
//    }
//
//    public List<Integer> getCategories() {
//        return categories;
//    }
//
//    public void setCategories(List<Integer> categories) {
//        this.categories = categories;
//    }
//
//    public List<Integer> getTags() {
//        return tags;
//    }
//
//    public void setTags(List<Integer> tags) {
//        this.tags = tags;
//    }
//
//    public Links get_links() {
//        return _links;
//    }
//
//    public void set_links(Links _links) {
//        this._links = _links;
//    }
}
