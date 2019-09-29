package com.seashellapps.toptendownloads;

public class FeedEntry {
    private String title;
    private String category;
    private String link;
    private String description;
    private String pubdate;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    @Override
    public String toString() {
        return "title= " + title + "\n" +
                "category= " + category + '\n' +
                "link= " + link + '\n' +
                "pubdate= " + pubdate + '\n';
    }
}

