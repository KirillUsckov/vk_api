package ru.kduskov.vkapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kduskov.vkapi.model.audit.AuditRecord;

public interface AuditRecordRepository extends JpaRepository<AuditRecord, Integer> {
}
