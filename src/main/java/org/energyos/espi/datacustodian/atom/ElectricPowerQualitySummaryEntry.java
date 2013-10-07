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
import org.energyos.espi.datacustodian.domain.ElectricPowerQualitySummary;

@SuppressWarnings("serial")
public class ElectricPowerQualitySummaryEntry extends EspiEntry<ElectricPowerQualitySummary> {


    public ElectricPowerQualitySummaryEntry(ElectricPowerQualitySummary espiObject) throws FeedException {
        super(espiObject);
    }

    protected String getSelfHref() {
        return "RetailCustomer/" + espiObject.getUsagePoint().getRetailCustomer().getId() +
                "/ElectricPowerQualitySummary/" + espiObject.getId();
    }

    protected String getUpHref() {
        return "RetailCustomer/" + espiObject.getUsagePoint().getRetailCustomer().getId() +
                "/UsagePoint/" + espiObject.getUsagePoint().getId() +
                "/ElectricPowerQualitySummary";
    }

    protected void buildRelatedLinks() {}
}
