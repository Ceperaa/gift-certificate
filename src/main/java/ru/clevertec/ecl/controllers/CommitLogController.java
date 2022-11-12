package ru.clevertec.ecl.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.cluster.commitLog.CommitLogList;
import ru.clevertec.ecl.model.entity.CommitLog;
import ru.clevertec.ecl.services.CommitLogService;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/commit-logs")
public class CommitLogController {

    private final CommitLogService commitLogService;

    @GetMapping("/sequence")
    public Long getSequence() {
        return commitLogService.getSequence();
    }

    @GetMapping("/range")
    @ApiIgnore
    public ResponseEntity<CommitLogList> findByFirstIdAndLastId(@RequestParam Long firstId,
                                                                @RequestParam Long lastId) {
        List<CommitLog> byFirstIdAndLastId = commitLogService.findByFirstIdAndLastId(firstId, lastId);
        CommitLogList employeeList = new CommitLogList();
        employeeList.setCommitLogs(byFirstIdAndLastId);
        return new ResponseEntity<>(employeeList,
                HttpStatus.OK);
    }

    @PostMapping
    @ApiIgnore
    public ResponseEntity<CommitLog> saveAll(@RequestBody CommitLog list) {
        return new ResponseEntity<>(commitLogService.saveCommitLog(list),
                HttpStatus.OK);
    }
}
