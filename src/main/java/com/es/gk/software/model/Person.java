package com.es.gk.software.model;

import lombok.Data;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.sql.Date;

/**
 * Created by klevytska on 6/7/2016.
 */
@Entity
@Table(name="person")
@XmlRootElement
public @Data class Person implements Serializable{

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "first_name")
    private String first_name;

    @Column(name = "last_name")
    private String last_name;

    @Column(name = "birth_date")
    private Date birth_date;

    @Column(name = "country")
    private String country;
}
