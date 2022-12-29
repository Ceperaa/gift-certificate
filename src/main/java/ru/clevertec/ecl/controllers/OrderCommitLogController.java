package ru.clevertec.ecl.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.cluster.commitLog.OrderCommitLogList;
import ru.clevertec.ecl.model.entity.OrderCommitLog;
import ru.clevertec.ecl.services.OrderCommitLogService;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/order-commit-logs")
public class OrderCommitLogController {

    private final OrderCommitLogService commitLogService;

    @GetMapping("/sequence")
    @ApiIgnore
    public Long getSequence() {
        return commitLogService.getSequence();
    }

    @GetMapping("/range")
    public ResponseEntity<OrderCommitLogList> findByFirstIdAndLastId(@RequestParam Long firstId, @RequestParam Long lastId) {
        List<OrderCommitLog> byFirstIdAndLastId = commitLogService.findByFirstIdAndLastId(firstId, lastId);
        OrderCommitLogList employeeList = new OrderCommitLogList();
        employeeList.setCommitLogs(byFirstIdAndLastId);
        return new ResponseEntity<>(employeeList,
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OrderCommitLog> saveAll(@RequestBody OrderCommitLog list) {
        return new ResponseEntity<>(commitLogService.saveCommitLog(list),
                HttpStatus.OK);
    }
}
