package com.ftp.osmserverproj.Repository;


import com.ftp.osmserverproj.Model.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {

}