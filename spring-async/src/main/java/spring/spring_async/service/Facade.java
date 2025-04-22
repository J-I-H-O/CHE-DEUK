package spring.spring_async.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import spring.spring_async.domain.Notification;

@Slf4j
@Service
public class Facade {

    private String threadName;
    private String transactionName;
    private final SyncService syncService;
    private final AsyncService asyncService;

    public Facade(SyncService syncService, AsyncService asyncService) {
        this.syncService = syncService;
        this.asyncService = asyncService;
    }

    @Transactional
    public void process() {
        this.threadName = Thread.currentThread().getName();
        this.transactionName = TransactionSynchronizationManager.getCurrentTransactionName();

        log.info("outer sync method thread : {}", this.threadName);
        log.info("outer sync method transaction : {}", this.transactionName);

        Notification notification1 = new Notification();
        Notification notification2 = new Notification();

        syncService.process(notification1);
        asyncService.processAsync(notification2);
    }

    @Transactional
    public void processButAsyncException(Notification syncNotification, Notification asyncNotification) {
        this.threadName = Thread.currentThread().getName();
        this.transactionName = TransactionSynchronizationManager.getCurrentTransactionName();

        log.info("outer sync method thread : {}", this.threadName);
        log.info("outer sync method transaction : {}", this.transactionName);
        syncService.process(syncNotification);
        asyncService.processAsyncException(asyncNotification);
    }

    public String getFacadeThreadName() {
        return this.threadName;
    }

    public String getSyncServiceThreadName() {
        return syncService.getThreadName();
    }

    public String getAsyncServiceThreadName() {
        return asyncService.getThreadName();
    }

    public String getFacadeTransactionName() {
        return this.transactionName;
    }

    public String getSyncServiceTransactionName() {
        return syncService.getTransactionName();
    }

    public String getAsyncServiceTransactionName() {
        return asyncService.getTransactionName();
    }
}
