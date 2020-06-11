package com.exsportsmanager.exsportsmanager.models;


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
@Table(name = "sports")
@JsonIgnoreProperties(ignoreUnknown=true, value = {"hibernateLazyInitializer", "handler","date"})
public class Sport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Integer id;

    @Column(name = "sportName" , nullable = false)
    private String sportName;

    @Column(name = "averagePrice")
    private Float averagePrice;

    @OneToMany(mappedBy = "sport", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JsonManagedReference
    @JsonIgnore
    private Set<Date> dates;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "country_id", nullable = false)
    @JsonBackReference
    private Country country;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "city_id", nullable = false)
    @JsonBackReference
    private City city;


    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }
        if (!(o instanceof Sport)) {
            return false;
        }

        Sport other = (Sport) o;
        return  this.sportName.compareTo(other.sportName) == 0
                && this.averagePrice.compareTo(other.averagePrice) == 0;
    }

}
