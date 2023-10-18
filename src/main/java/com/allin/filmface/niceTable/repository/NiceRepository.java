package com.allin.filmface.niceTable.repository;

import com.allin.filmface.niceTable.entity.Nice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NiceRepository extends JpaRepository<Nice, Integer> {
    Optional<Nice> findBySearch_SearchNo(int searchNo);
}
