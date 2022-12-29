package ru.clevertec.ecl.cluster.commitLog;

import lombok.Getter;
import lombok.Setter;
import ru.clevertec.ecl.model.entity.CommitLog;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CommitLogList {
    private List<CommitLog> commitLogs;

    public CommitLogList() {
        commitLogs = new ArrayList<>();
    }

}
