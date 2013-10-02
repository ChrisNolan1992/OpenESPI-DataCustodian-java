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
import org.energyos.espi.datacustodian.domain.ElectricPowerUsageSummary;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBException;

import static org.energyos.espi.datacustodian.utils.factories.EspiFactory.newElectricPowerUsageSummaryWithUsagePoint;
import static org.junit.Assert.assertEquals;

public class ElectricPowerUsageSummaryEntryTests {

    private ElectricPowerUsageSummaryEntry entry;

    @Before
    public void setup() throws JAXBException, FeedException {
        ElectricPowerUsageSummary electricPowerUsageSummary = newElectricPowerUsageSummaryWithUsagePoint();
        electricPowerUsageSummary.setId(1L);
        electricPowerUsageSummary.getUsagePoint().setId(99L);
        electricPowerUsageSummary.getUsagePoint().getRetailCustomer().setId(88L);

        entry = new ElectricPowerUsageSummaryEntry(electricPowerUsageSummary);
    }

    @Test
    public void selfHref() {
        assertEquals("RetailCustomer/88/ElectricPowerUsageSummary/1", entry.getSelfHref());
    }

    @Test
    public void upHref() {
        assertEquals("RetailCustomer/88/UsagePoint/99/ElectricPowerUsageSummary", entry.getUpHref());
    }
}