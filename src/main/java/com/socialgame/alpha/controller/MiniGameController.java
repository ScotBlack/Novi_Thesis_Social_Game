package com.socialgame.alpha.controller;

import com.socialgame.alpha.service.MiniGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/minigame")
public class MiniGameController {

    private MiniGameService miniGameService;

    @Autowired
    public void setMiniGameService (MiniGameService miniGameService) { this.miniGameService = miniGameService;}

    @GetMapping(path="/{id}/nextMiniGame")
    public ResponseEntity<?> nextMiniGame(@PathVariable("id") Long id) {
        return miniGameService.nextMiniGame(id);
    }



}
