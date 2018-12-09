package com.github.final60.schedule

import com.github.final60.service.BaseService
import com.github.final60.service.PodService
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class Scheduler(val podService: PodService) : BaseService() {

    private val logger = LoggerFactory.getLogger(Scheduler::class.java)

    /**
     * Update the list of known Pod services.
     */
    //@Scheduled(fixedRate = 3000)
    @Scheduled(cron = "*/5 * * * * ?")
    fun updateKnownPodServices() {

        logger.info("Started schedule for updating known Pod services.")

        podService.update()

        logger.info("Completed schedule for updating known Pod services.")
    }
}