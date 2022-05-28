package com.foonk.Kindergarten_corporate_website.database.repository;

import com.foonk.Kindergarten_corporate_website.database.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

}
