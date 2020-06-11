package com.exsportsmanager.exsportsmanager.models;

import com.exsportsmanager.exsportsmanager.models.enums.Regions;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Countries")
@JsonIgnoreProperties(ignoreUnknown=true, value = {"hibernateLazyInitializer", "handler","sport"})
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Integer id;

    @Column(name = "countryName" , nullable = false)
    private String countryName;

    @Enumerated(EnumType.STRING)
    private Regions region;

    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL ,orphanRemoval = true)
    @JsonManagedReference
    @JsonIgnore
    private Set<Sport> sports;

    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL ,orphanRemoval = true)
    @JsonManagedReference
    @JsonIgnore
    private Set<City> cities;


}
