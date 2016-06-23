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
@Table(name= "work_experience")
@XmlRootElement
public @Data class WorkExperience  implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name= "position")
    private String position;

    @Column(name = "company_name")
    private String company_name;

    @Column(name="date_start")
    private Date date_start;

    @Column(name="date_end")
    private Date date_end;

    @JoinColumn(name="person_id")
    @ManyToOne(targetEntity = Person.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Person person_id;
}
