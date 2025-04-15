package spring.spring_async.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class Facade {

    private String threadName;
    private final SyncService syncService;
    private final AsyncService asyncService;

    public Facade(SyncService syncService,
                  AsyncService asyncService) {
        this.syncService = syncService;
        this.asyncService = asyncService;
    }

    @Transactional
    public void process() {
        this.threadName = Thread.currentThread().getName();
        syncService.process();
        asyncService.process();
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
}
