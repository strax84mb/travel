package com.htec.travel.cities;

import com.htec.travel.data.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "city", indexes = {
        @Index(name = "city_name_country_index", columnList = "name,country", unique = true)
})
public class City extends BaseEntity {

    @Column(length = 30, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String country;

    @Column(nullable = false)
    private String description;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "city")
    @BatchSize(size = 10)
    @OrderBy("created DESC")
    private List<Comment> comments;
}
