package br.com.zup.poc;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(Application.class);

    @Autowired
    private PendingRenewalsRepository repo;
    @Autowired
    private CreateService createService;
    @Autowired
    private UpdateService updateService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    @Transactional
    public void creator() {
        Thread creator = new Thread(new Creator(this.createService));
        creator.start();
    }

    @Bean
    @Transactional
    public void node1() {
        Thread node1 = new Thread(new InProgress("updater_node_1", this.updateService));
        node1.start();
    }

    @Bean
    @Transactional
    public void node2() {
        Thread node2 = new Thread(new InProgress("updater_node_2", this.updateService));
        node2.start();
    }

    @Override
    public void run(String... args) throws Exception {
        repo.deleteAll();
    }

    public class Creator implements Runnable {

        private final CreateService service;
        private Random random = new Random();

        public Creator(CreateService service) {
            this.service = service;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    this.service.createRenewal("CREATOR", PendingRenewalStatus.IN_PROGRESS);
                    if (random.nextBoolean() && random.nextBoolean()) {
                        logger.info("---- waiting ----");
                        Thread.sleep(1000);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class InProgress implements Runnable {

        private final String node;
        private final UpdateService service;
        private Integer count = 0;

        public InProgress(String node, UpdateService service) {
            this.node = node;
            this.service = service;
            logger.info("[{}] created.", this.node);
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(5000);
                    int result = this.service.updateRenewal(this.node, PendingRenewalStatus.IN_PROGRESS);
                    count += result;
                    logger.info("[{}] {} updated so far.", this.node, count);
                    Thread.sleep(6000);
                } catch (CannotAcquireLockException e) {
                    logger.info("[{}]----- DEAD LOCK --- waiting ", this.node);
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }

        }
    }

}
