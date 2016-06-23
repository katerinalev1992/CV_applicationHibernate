package com.es.gk.software.model;

import lombok.Data;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by klevytska on 6/7/2016.
 */
@Entity
@Table(name= "skill")
@XmlRootElement
public @Data class Skill  implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name= "skill_name")
    private String skill_name;

    @Column(name= "skill_level")
    private String skill_level;

    @JoinColumn(name="person_id")
    @ManyToOne(targetEntity = Person.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Person person_id;
}
