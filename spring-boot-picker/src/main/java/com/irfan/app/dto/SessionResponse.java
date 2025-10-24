package com.irfan.app.dto;

import lombok.Data;

@Data
public class SessionResponse {

    private Long id;
    private String link;
    private String name;
    private String description;
    private String imageUrl;
    private Boolean active;
    private String result;

    public SessionResponse() {
    }

    public SessionResponse(Long id) {
        this.id = id;
    }

    public SessionResponse(Long id, String link) {
        this.id = id;
        this.link = link;
    }

    public SessionResponse(Long id, String link, String name, String description, String imageUrl, Boolean active, String result) {
        this.id = id;
        this.link = link;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.active = active;
        this.result = result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
