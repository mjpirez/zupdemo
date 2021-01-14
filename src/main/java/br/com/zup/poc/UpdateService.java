package br.com.zup.poc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateService {

    private final Logger logger = LoggerFactory.getLogger(UpdateService.class);

    @Autowired
    private PendingRenewalsRepository repo;

    @Transactional
    public Integer updateRenewal(String node, PendingRenewalStatus status) {
        final AtomicInteger counter = new AtomicInteger(0);
        List<PendingRenewals> renewals = repo.findByStatus(
            PendingRenewalStatus.PENDING,
            PageRequest.of(0, 5, Sort.by(Sort.Direction.ASC, "createdAt"))
        ).getContent();
        for(PendingRenewals pending: renewals) {
            pending.setStatus(status);
            pending.setUpdatedBy(pending.getUpdatedBy() + "," + node);
            pending.setUpdatedAt(LocalDateTime.now());
            repo.save(pending);
            counter.getAndAdd(1);
            logger.info("[{}] updated {}", node, pending.getServiceOrderId());
        }
        return counter.get();
    }

}
