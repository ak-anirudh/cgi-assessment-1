package org.cgi.assesment.repository;

import org.cgi.assesment.model.Logs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogsRepository extends JpaRepository<Logs, Integer> {
    List<Logs> findByLogLevel(String level);
}
