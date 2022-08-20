package com.foonk.Kindergarten_corporate_website.database.repository;

import com.foonk.Kindergarten_corporate_website.database.Document;
import com.foonk.Kindergarten_corporate_website.database.Kind;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findAllByKind(Kind kind);
}
