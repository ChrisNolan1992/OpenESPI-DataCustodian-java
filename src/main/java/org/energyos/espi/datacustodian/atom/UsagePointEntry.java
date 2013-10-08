/*
 * Copyright 2013 EnergyOS.org
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

package org.energyos.espi.datacustodian.atom;

import com.sun.syndication.io.FeedException;
import org.energyos.espi.datacustodian.domain.UsagePoint;

@SuppressWarnings("serial")
public class UsagePointEntry extends EspiEntry<UsagePoint> {

    public UsagePointEntry(UsagePoint usagePoint) throws FeedException {
        super(usagePoint);
    }

    protected String getSelfHref() {
        return "RetailCustomer/" + espiObject.getRetailCustomer().getId() + "/UsagePoint/" + espiObject.getId();
    }

    protected String getUpHref() {
        return "RetailCustomer/" + espiObject.getRetailCustomer().getId() + "/UsagePoint";
    }

    protected void buildRelatedLinks() {
        if (espiObject.getMeterReadings().size() > 0) {
            addRelatedLink(getSelfLink().getHref() + "/MeterReading");
        }

        if (espiObject.getElectricPowerUsageSummaries().size() > 0) {
            addRelatedLink(getSelfLink().getHref() + "/ElectricPowerUsageSummary");
        }

        if (espiObject.getElectricPowerQualitySummaries().size() > 0) {
            addRelatedLink(getSelfLink().getHref() + "/ElectricPowerQualitySummary");
        }
    }
}

