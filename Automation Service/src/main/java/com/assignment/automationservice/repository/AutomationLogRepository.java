package com.assignment.automationservice.repository;

import com.assignment.automationservice.entity.AutomationLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AutomationLogRepository extends MongoRepository<AutomationLog, String> {
}