package com.ftp.osmserverproj.Repository;


import com.ftp.osmserverproj.Model.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {
//    boolean existsByFileName(String fileName);
//    boolean existsBySubject(String subject);
//boolean existsByEmailDetailsAttachment(String attachment);
    boolean existsByEmailDetailsAttachment(String attachment);
    boolean existsByEmailDetailsSubject(String subject);
}