package br.com.zup.poc;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PENDING_RENEWALS")
public class PendingRenewals {

    @Id
    @Column(name = "SERVICE_ORDER_ID")
    private Long serviceOrderId;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private PendingRenewalStatus status;

    @Column(name = "SERVICE_ORDER")
    private String serviceOrder;

    @Column(name = "OFFER_REQUEST")
    private String offerRequest;

    @Column(name = "UPDATED_BY")
    private String updatedBy;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;

    public static PendingRenewals newRenewal() {
        PendingRenewals renewal = new PendingRenewals();
        renewal.serviceOrderId = new Random().nextLong();
        renewal.status = PendingRenewalStatus.PENDING;
        renewal.serviceOrder = UUID.randomUUID().toString();
        renewal.offerRequest = UUID.randomUUID().toString();
        renewal.updatedBy = "CREATOR";
        renewal.createdAt = LocalDateTime.now();
        return renewal;
    }

    public Long getServiceOrderId() {
        return serviceOrderId;
    }

    public void setServiceOrderId(Long serviceOrderId) {
        this.serviceOrderId = serviceOrderId;
    }

    public PendingRenewalStatus getStatus() {
        return status;
    }

    public void setStatus(PendingRenewalStatus status) {
        this.status = status;
    }

    public String getServiceOrder() {
        return serviceOrder;
    }

    public void setServiceOrder(String serviceOrder) {
        this.serviceOrder = serviceOrder;
    }

    public String getOfferRequest() {
        return offerRequest;
    }

    public void setOfferRequest(String offerRequest) {
        this.offerRequest = offerRequest;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
