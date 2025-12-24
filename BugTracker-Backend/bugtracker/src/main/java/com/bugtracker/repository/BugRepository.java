package com.bugtracker.repository;

import com.bugtracker.entity.Bug;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BugRepository extends JpaRepository<Bug, Long> {

    // search by title containing a keyword or description containing a keyword
    List<Bug> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String title, String description);

    // Filer By Status
    List<Bug> findByStatusIgnoreCase(String status);

    // Filter By Difficulty
    List<Bug> findByDifficultyIgnoreCase(String difficulty);

    // Find User Assigned ID(changed)
    Page<Bug> findByAssignedTo_Id(Long userId, Pageable pageable);
    Optional<Bug> findByIdAndAssignedToId(Long bugId, Long userId);
}

























