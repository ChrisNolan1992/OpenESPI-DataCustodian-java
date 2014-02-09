/*
 * Copyright 2013, 2014 EnergyOS.org
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.energyos.espi.datacustodian.service.impl;

import java.util.Iterator;
import java.util.List;

import org.energyos.espi.common.domain.BatchList;
import org.energyos.espi.common.domain.Subscription;
import org.energyos.espi.common.service.ResourceService;
import org.energyos.espi.datacustodian.service.NotificationService;
import javax.xml.datatype.XMLGregorianCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private ResourceService resourceService;
    
    @Override
    public void notify(Subscription subscription , XMLGregorianCalendar startDate, XMLGregorianCalendar endDate) {
        String thirdPartyNotificationURI = subscription.getApplicationInformation().getThirdPartyNotifyUri();
        String separator = "?";
        
        String subscriptionURI = subscription.getApplicationInformation().getDataCustodianResourceEndpoint() + "/Batch/Subscription/" + subscription.getId();
        if (startDate != null) {
        	subscriptionURI = subscriptionURI + separator + "published-min=" + startDate.toXMLFormat(); 
        	separator = "&";
        }
        if (endDate != null) {
        	subscriptionURI = subscriptionURI + separator + "published-max=" + endDate.toXMLFormat(); 
        }
        
        BatchList batchList = new BatchList();
        batchList.getResources().add(subscriptionURI);
        notifyInternal(thirdPartyNotificationURI, batchList);
    }

	@Override
	public void notify(List<Subscription> subscriptions,  XMLGregorianCalendar startDate, XMLGregorianCalendar endDate) {

        Iterator <Subscription> subscriptionsIterator = subscriptions.iterator();
        while (subscriptionsIterator.hasNext()) {
        	// TODO optimization for multiple notifications (subscriptions) to the same ThirdParty
        	// for now, just notify one at a time.
        	notify (subscriptionsIterator.next(), startDate, endDate);	
        }
	}
	
	
	private void notifyInternal(String thirdPartyNotificationURI, BatchList batchList) {
		
        try {
            restTemplate.postForLocation(thirdPartyNotificationURI, batchList);
        } catch (Exception e) {
        	System.out.printf("Notification Exception: %s --> %s :: %s\n", thirdPartyNotificationURI, e.toString());
        }
		
	}
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    public void setResourceService(ResourceService resourceService) {
    	this.resourceService = resourceService;
    }

}
