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
@Table(name="education")
@XmlRootElement
public @Data class Education  implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name= "place")
    private String place;

    @Column(name= "country")
    private String country;

    @Column(name= "faculty")
    private String faculty;

    @Column(name= "speciality")
    private String speciality;

    @Column(name= "date_start")
    private Date start_date;

    @Column(name = "end_date")
    private Date end_date;

    @JoinColumn(name = "person_id")
    @ManyToOne(targetEntity = Person.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Person person_id;
}
