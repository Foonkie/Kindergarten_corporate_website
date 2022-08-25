package com.foonk.Kindergarten_corporate_website.database;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*Сущность для новости.*/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "news")
@Entity
public class News extends AuditingEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String header;

    private String body;

}
