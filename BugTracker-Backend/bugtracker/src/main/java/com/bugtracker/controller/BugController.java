package com.bugtracker.controller;

import com.bugtracker.entity.Bug;
import com.bugtracker.service.BugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Arrays;

@RestController
@RequestMapping("/api/bugs")
public class BugController {

    @Autowired
    private BugService bugService;

    //Add a new bug
    @PostMapping
    public Bug addBug(@RequestBody Bug bug) {
        return bugService.addBug(bug);
    }

    //Add Multiple Bugs
    @PostMapping("/batches")
    public List<Bug> addBugs(@RequestBody List<Bug> bugs) {
        return bugs.stream()
                .map(bugService::addBug)
                .collect(Collectors.toList());
    }

    //Get all bugs
    @GetMapping("/all")
    public List<Bug> getAllBugs() {
        return bugService.getAllBugs();
    }

    @GetMapping("/paginated")
    public Page<Bug> getAllBugsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return bugService.getAllBugs(pageable);
    }

    //Get bug by ID
    @GetMapping("/{id}")
    public Optional<Bug> getBugById(@PathVariable Long id) {
        return bugService.getBugById(id);
    }

    // Update a specific bug
    @PutMapping("/{id}")
    public Bug updateBug(@PathVariable Long id, @RequestBody Bug updatedBug) {
        return bugService.updateBug(id, updatedBug);
    }

    //Delete a bug
    @DeleteMapping("/{id}")
    public void deleteBug(@PathVariable Long id){
        bugService.deleteBug(id);
    }

    // Search
    @GetMapping("/search")
    public List<Bug> searchBugs(@RequestParam String keyword) {
        return bugService.searchBugs(keyword);
    }

    // Filtering based on status
    @GetMapping("/filter/status")
    public List<Bug> filterByStatus(@RequestParam String status) {
        return bugService.getBugsByStatus(status);
    }

    // Filtering based on difficulty
    @GetMapping("/filter/difficulty")
    public List<Bug> filterBugsByDifficulty(@RequestParam String difficulty) {
        return bugService.filterBugsByDifficulty(difficulty);
    }

}
