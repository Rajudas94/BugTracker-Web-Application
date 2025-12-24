package com.bugtracker.controller;

import com.bugtracker.entity.Bug;
import com.bugtracker.service.BugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import com.bugtracker.service.UserService;
import com.bugtracker.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private BugService bugService;

    @Autowired
    private UserService userService;

    // Admin: Add new bug
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/bugs")
    public ResponseEntity<Bug> addBug(@RequestBody Bug bug) {
        return ResponseEntity.ok(bugService.addBug(bug));
    }

    // Admin: View all bugs
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/bugs")
    public ResponseEntity<Page<Bug>> getAllBugs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(bugService.getAllBugs(pageable));
    }


    // Admin : Get Bugs By Id
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/bugs/{id}")
    public ResponseEntity<?> getBugById(@PathVariable Long id) {
        Optional<Bug> opt = bugService.getBugById(id);
        if (opt.isPresent()) {
            return ResponseEntity.ok(opt.get());
        }
        else {
            return ResponseEntity.badRequest().body("Bug not found");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<List<Map<String, Object>>> getAllUsers() {

        List<User> users = userService.getAllUsers();

        List<Map<String, Object>> result = new ArrayList<>();

        for(User u : users){
            Map<String, Object> map = new HashMap<>();
            map.put("id", u.getId());
            map.put("username", u.getUsername());
            result.add(map);
        }
        return ResponseEntity.ok(result);
    }

    // Admin: Update bug
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/bugs/{id}")
    public ResponseEntity<?> updateBug(@PathVariable Long id, @RequestBody Bug updatedBug) {
        Bug updated = bugService.updateBug(id, updatedBug);
        if (updated == null) {
            return ResponseEntity.badRequest().body("Bug not found");
        }
        return ResponseEntity.ok(updated);
    }

    // Admin: Delete bug
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/bugs/{id}")
    public ResponseEntity<?> deleteBug(@PathVariable Long id) {
        bugService.deleteBug(id);
        return ResponseEntity.ok("Bug deleted successfully");
    }

    // Admin: Search bugs
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/bugs/search")
    public ResponseEntity<List<Bug>> searchBugs(@RequestParam String keyword) {
        return ResponseEntity.ok(bugService.searchBugs(keyword));
    }

    // Admin: Filter bugs by difficulty
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/bugs/difficulty")
    public ResponseEntity<List<Bug>> filterByDifficulty(@RequestParam String difficulty) {
        return ResponseEntity.ok(bugService.filterBugsByDifficulty(difficulty));
    }

    // Admin: Filter bugs by status
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/bugs/status")
    public ResponseEntity<List<Bug>> getBugsByStatus(@RequestParam String status) {
        return ResponseEntity.ok(bugService.getBugsByStatus(status));
    }

    // Admin: Assign Bugs to User
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/bugs/{bugId}/assign/{userId}")
    public ResponseEntity<?> assignBug(@PathVariable Long bugId, @PathVariable Long userId){

        try{
            Bug assignedBug = bugService.assignBug(bugId, userId);
            return ResponseEntity.ok(assignedBug);
        }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }




















}
