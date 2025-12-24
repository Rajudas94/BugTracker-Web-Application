package com.bugtracker.service;

import com.bugtracker.entity.Bug;
import com.bugtracker.repository.BugRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.bugtracker.repository.UserRepository;
import com.bugtracker.model.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;

import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = {"bugs","userBugs"})
public class BugServiceImpl implements BugService {

    @Autowired
    private BugRepository bugRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Bug addBug(Bug bug) {
        return bugRepository.save(bug);
    }

    @Override
    public List<Bug> getAllBugs() {
        return bugRepository.findAll();
    }

    @Cacheable(value = "bugs", key = "#id")
    @Override
    public Optional<Bug> getBugById(Long id) {
        return bugRepository.findById(id);
    }

    @Override
    public Bug updateBug(Long id, Bug updatedBug) {
        Optional<Bug> existingBug = bugRepository.findById(id);

        if (existingBug.isPresent()) {
            Bug bug = existingBug.get();
            bug.setTitle(updatedBug.getTitle());
            bug.setDescription(updatedBug.getDescription());
            bug.setStatus(updatedBug.getStatus());
            return bugRepository.save(bug);
        } else {
            return null;
        }
    }

    @Override
    public void deleteBug(Long id) {
        bugRepository.deleteById(id);
    }

    @Override
    public Page<Bug> getAllBugs(Pageable pageable) {
        return bugRepository.findAll(pageable);
    }

    @Override
    public List<Bug> searchBugs(String keyword){
        return bugRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword);
    }

    @Override
    public List<Bug> getBugsByStatus(String status){
        return bugRepository.findByStatusIgnoreCase(status);
    }

    @Override
    public List<Bug> filterBugsByDifficulty(String difficulty){
        return bugRepository.findByDifficultyIgnoreCase(difficulty);
    }

    @Caching(evict = {
            @CacheEvict(value = "bugs", key = "#bugId"),
            @CacheEvict(value = "userBugs", allEntries = true)
    })
    @Override
    public Bug assignBug(Long bugId, Long userId){
        Bug bug = bugRepository.findById(bugId)
                .orElseThrow(() -> new RuntimeException("Bug with ID " + bugId + " not found !!"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User with Id " + userId + " not found !!"));

        bug.setAssignedTo(user);
        return bugRepository.save(bug);
    }

    // needs changing
    @Cacheable(value = "userBugs", key = "#userId + '_page_' + #pageable.pageNumber + '_size_' + #pageable.pageSize")
    @Override
    public List<Bug> getBugsAssignedToUser(Long userId, Pageable pageable) {
        //System.out.println("DB Query: Fetching page " + pageable.getPageNumber() + " for user " + userId);
        System.out.println("=".repeat(50));
        System.out.println("🚨 CACHE MISS - DB QUERY");
        System.out.println("User ID: " + userId);
        System.out.println("Page: " + pageable.getPageNumber());
        System.out.println("Size: " + pageable.getPageSize());
        System.out.println("Key would be: userBugs::" + userId +
                "_page_" + pageable.getPageNumber() +
                "_size_" + pageable.getPageSize());
        System.out.println("=".repeat(50));

        Page<Bug> page = bugRepository.findByAssignedTo_Id(userId, pageable);
        return page.getContent();
    }

    @Override
    public Bug getBugAssignedToUser(Long bugId, Long userId){
        Bug bug = bugRepository.findById(bugId).orElse(null);

        if(bug == null) return null;
        if(bug.getAssignedTo() == null) return null;
        if(!bug.getAssignedTo().getId().equals(userId)) return null;

        return bug;
    }

    @Caching(evict = {
            @CacheEvict(value = "bugs", key = "#bugId"),
            @CacheEvict(value = "userBugs", allEntries = true)
    })
    @Override
    public Bug markBugCompleted(Long bugId, Long userId){

        Bug bug = bugRepository.findById(bugId)
                .orElseThrow(() -> new RuntimeException("Bug Not found !!"));

        // if assigned id(foreign key) in the bug table is not equal to the id(primary key) in the user table
        if(bug.getAssignedTo() == null || !bug.getAssignedTo().getId().equals(userId)){
            throw new RuntimeException("This Bug is not assigned to the current user !!");
        }

        bug.setStatus("COMPLETED");
        return bugRepository.save(bug);
    }
}

