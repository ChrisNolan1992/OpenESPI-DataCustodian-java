package org.energyos.espi.datacustodian.service;

import org.energyos.espi.datacustodian.domain.Subscription;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.List;

public interface SubscriptionService {
    Subscription createSubscription(OAuth2Authentication retailCustomer);

    List<Subscription> findAll();

    void persist(Subscription subscription);

    Subscription findByHashedId(String hashedId);
}
