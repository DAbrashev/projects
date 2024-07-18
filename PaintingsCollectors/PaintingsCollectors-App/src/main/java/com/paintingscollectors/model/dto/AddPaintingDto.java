package com.paintingscollectors.model.dto;

import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Size;

public class AddPaintingDto {
    @Size(min = 5,max = 40)
    private String name;
    @Size(min = 5,max = 30)
    private String author;
    @URL
    @Size(max = 150)
    private String imageURL;

    private String style;

    public AddPaintingDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }
}
