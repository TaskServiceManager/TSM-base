package com.sanjati.core.services;

import com.sanjati.api.auth.UserLightDto;
import com.sanjati.core.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticService {

    private final TaskRepository taskRepository;
    private final AuthService authService;

    public List<UserLightDto> getAllExecutorsSortedByEmployment(){
        Map<Long, Long> employment = taskRepository.getExecutorsIdsWithAmountActiveTasks()
                .stream().collect(Collectors.toMap(k -> k.get(0), v -> v.get(1)));
        List<UserLightDto> executors = authService.getAllUsersByRole("ROLE_EXECUTOR");
        for (UserLightDto e : executors) {
            if (employment.containsKey(e.getId())){
                e.setAmountActiveTasks(employment.get(e.getId()));
            }
        }
        executors.sort(Comparator.comparing(UserLightDto::getAmountActiveTasks, Collections.reverseOrder()));
        return executors;
    }

}
