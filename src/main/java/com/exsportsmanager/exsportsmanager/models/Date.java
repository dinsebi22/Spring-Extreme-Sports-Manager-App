package com.exsportsmanager.exsportsmanager.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Dates")
@JsonIgnoreProperties(ignoreUnknown=true, value = {"hibernateLazyInitializer", "handler","sport"})
public class Date {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;


    private Integer startDay;
    private String startMonth;

    private Integer endDay;
    private String endMonth;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sport_id", nullable = false)
    @JsonBackReference
    private Sport sport;

    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }
        if (!(o instanceof Date)) {
            return false;
        }

        Date other = (Date) o;
        return Integer.compare(this.startDay, other.startDay) == 0
                && Integer.compare(this.endDay, other.endDay) == 0
                && this.startMonth.compareTo(other.startMonth) == 0
                && this.endMonth.compareTo(other.endMonth) == 0;
    }

    @Override
    public String toString() {
        return "Date{" +
                "startDay=" + startDay +
                ", startMonth='" + startMonth + '\'' +
                ", endDay=" + endDay +
                ", endMonth='" + endMonth + '\'' +
                '}';
    }
}
