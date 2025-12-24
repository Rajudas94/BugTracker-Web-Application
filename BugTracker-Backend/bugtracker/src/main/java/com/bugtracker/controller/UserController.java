package com.bugtracker.controller;

import com.bugtracker.entity.Bug;
import com.bugtracker.service.BugService;
import com.bugtracker.service.UserService;
import com.bugtracker.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController{

    @Autowired
    private BugService bugService;

    @Autowired
    private UserService userService;

    // 1. USER: View only the bugs assigned to THEM
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/bugs")
    public ResponseEntity<List<Bug>> getAssignedBugs(
            Authentication auth,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {


        if(auth == null){
            System.out.println("Error: Authentication is null");
            return ResponseEntity.status(401).build();
        }

        User user = (User) auth.getPrincipal();
        Long userId = user.getId();

        Pageable pageable = PageRequest.of(page, size);
        List<Bug> bugs = bugService.getBugsAssignedToUser(userId, pageable);

        // Added line above on 17/12/25
        return ResponseEntity.ok(bugs);
    }

    // 2. USER: View bug by ID (only if assigned to them)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/bugs/{id}")
    public ResponseEntity<?> getBugById(@PathVariable Long id, Authentication auth) {

        String username = auth.getName();
        User user = userService.getUserByUsername(username);

        Bug bug = bugService.getBugAssignedToUser(id, user.getId());
        if (bug == null) {
            return ResponseEntity.status(403).body("Not authorized to view this bug");
        }

        return ResponseEntity.ok(bug);
    }

    // 3. USER: Mark bug as COMPLETED
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/bugs/{id}/complete")
    public ResponseEntity<?> completeBug(@PathVariable Long id, Authentication auth){

        String username = auth.getName();
        User user = userService.getUserByUsername(username);

        Bug updated = bugService.markBugCompleted(id, user.getId());

        if (updated == null) {
            return ResponseEntity.status(403).body("You cannot complete this bug");
        }

        return ResponseEntity.ok("Bug marked as COMPLETED");
    }


    // ------------------------------------------
    // Existing features (search, filter)
    // ------------------------------------------

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/bugs/search")
    public ResponseEntity<List<Bug>> searchBugs(@RequestParam String keyword){
        return ResponseEntity.ok(bugService.searchBugs(keyword));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/bugs/difficulty")
    public ResponseEntity<List<Bug>> filterByDifficulty(@RequestParam String difficulty){
        return ResponseEntity.ok(bugService.filterBugsByDifficulty(difficulty));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/bugs/status")
    public ResponseEntity<List<Bug>> filterByStatus(@RequestParam String status){
        return ResponseEntity.ok(bugService.getBugsByStatus(status));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/debug")
    public ResponseEntity<?> debug(Authentication auth) {
        System.out.println("DEBUG: Principal = " + auth.getPrincipal());
        System.out.println("DEBUG: Principal class = " + auth.getPrincipal().getClass());
        return ResponseEntity.ok(auth.getPrincipal());
    }

}
