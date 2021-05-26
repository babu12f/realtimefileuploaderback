package com.nrbswift.spring4web.dao;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="pictures")
public class Picture implements Serializable {
    @Id
    private int id;
    private String imagePath;
    private Date createdDate;

    public Picture() {
    }

    public Picture(int id, String imagePath, Date createdDate) {
        this.id = id;
        this.imagePath = imagePath;
        this.createdDate = createdDate;
    }

    public Picture(String imagePath, Date createdDate) {
        this.imagePath = imagePath;
        this.createdDate = createdDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "id=" + id +
                ", imagePath='" + imagePath + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }
}
