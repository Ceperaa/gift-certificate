package ru.clevertec.ecl.cluster.healsCheck;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.clevertec.ecl.cluster.commitLog.EntityRecovery;
import ru.clevertec.ecl.cluster.commitLog.OrderRecovery;

@Component
@RequiredArgsConstructor
public class SchedulerWorker {

    private final HealsCheck healsCheck;
    private final EntityRecovery recovery;
    private final OrderRecovery orderRecovery;


    @Scheduled(initialDelay = 10000L, fixedDelay = 5000000L)
    @SneakyThrows
    public void doWork() {
        healsCheck.checkShards();
        Thread.sleep(5000);
        recovery.run();
        orderRecovery.run();
    }
}
