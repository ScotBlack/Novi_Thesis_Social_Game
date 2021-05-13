package com.socialgame.alpha.service;

import com.socialgame.alpha.dto.request.SettingRequest;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface HostService {
    ResponseEntity<?> setGameSetting(SettingRequest request);
    ResponseEntity<?> startGame(HttpServletRequest request);
}
