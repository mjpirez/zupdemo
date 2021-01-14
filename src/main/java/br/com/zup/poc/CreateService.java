package br.com.zup.poc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateService {

    private final Logger logger = LoggerFactory.getLogger(CreateService.class);

    @Autowired
    private PendingRenewalsRepository repo;

    @Transactional
    public void createRenewal(String node, PendingRenewalStatus status) {
        logger.info("[{}]_SAVED: {}", node, this.repo.save(PendingRenewals.newRenewal()).getServiceOrderId());
    }

}
