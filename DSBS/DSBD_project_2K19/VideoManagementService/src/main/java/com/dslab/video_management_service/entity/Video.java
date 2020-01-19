package com.dslab.video_management_service.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id_video;

    @NotNull(message = "The name_video parameter must not be blank!")
    private String name_video;

    @NotNull(message = "The author parameter must not be blank!")
    private String autor;

    @NotNull(message = "The id_user parameter must not be blank!")
    private String id_user; // id dell'utente che ha postato il video

    private String state; // stringa che definisce se il file è stato aggiornato

    public Integer getId_video() {
        return id_video;
    }

    public void setId_video(Integer id_video) {
        this.id_video = id_video;
    }

    public String getName_video() {
        return name_video;
    }

    public void setName_video(String name_video) {
        this.name_video = name_video;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
