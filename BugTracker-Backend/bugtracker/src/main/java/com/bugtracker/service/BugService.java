package com.bugtracker.service;

import com.bugtracker.entity.Bug;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;

public interface BugService {

    Bug addBug(Bug bug);

    List<Bug> getAllBugs();

    Optional<Bug> getBugById(Long id);

    Bug updateBug(Long id, Bug updatedBug);

    void deleteBug(Long id);

    Page<Bug> getAllBugs(Pageable pageable);

    List<Bug> searchBugs(String keyword);

    List<Bug> getBugsByStatus(String status);

    List<Bug> filterBugsByDifficulty(String keyword);

    Bug assignBug(Long bugId, Long userId);

    List<Bug> getBugsAssignedToUser(Long userId, Pageable pageable); // checked

    Bug getBugAssignedToUser(Long bugId, Long userId); // checked

    Bug markBugCompleted(Long bugId, Long userId); //checked


}























































