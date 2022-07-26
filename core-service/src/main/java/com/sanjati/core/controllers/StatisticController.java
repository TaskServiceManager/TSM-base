package com.sanjati.core.controllers;

import com.sanjati.api.auth.UserLightDto;
import com.sanjati.core.services.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/statistic")
@RequiredArgsConstructor
public class StatisticController {

    private final StatisticService statisticService;

    @GetMapping("/employment")
    public List<UserLightDto> test(){
        return statisticService.getAllExecutorSortedByEmployment();
    }
}
