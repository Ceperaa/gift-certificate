package ru.clevertec.ecl.cluster.commitLog;

import lombok.Getter;
import lombok.Setter;
import ru.clevertec.ecl.model.entity.OrderCommitLog;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderCommitLogList {

    private List<OrderCommitLog> commitLogs;

    public OrderCommitLogList() {
        commitLogs = new ArrayList<>();
    }
}
