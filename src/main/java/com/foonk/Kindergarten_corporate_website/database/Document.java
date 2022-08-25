package com.foonk.Kindergarten_corporate_website.database;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*Сущность для документов.*/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "documents")
@Entity
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private Kind kind;

    private String document;
}
