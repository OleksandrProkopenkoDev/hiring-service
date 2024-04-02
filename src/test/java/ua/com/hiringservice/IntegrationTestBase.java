package ua.com.hiringservice;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ua.com.testsupport.config.MongoContainerConfig;
import ua.com.testsupport.config.PostgresContainerConfig;

/**
 * Base class for all Integration test classes.
 *
 * @author Artyom Kondratenko
 * @since 1/10/24
 */
@Transactional
@ActiveProfiles("unit")
@Import({PostgresContainerConfig.class, MongoContainerConfig.class})
@SpringBootTest(classes = HiringServiceApplication.class)
public abstract class IntegrationTestBase {}
