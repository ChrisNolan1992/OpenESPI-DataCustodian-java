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


import static org.custommonkey.xmlunit.XMLAssert.assertXpathExists;
import static org.energyos.espi.datacustodian.utils.factories.EspiFactory.newCalendar;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.custommonkey.xmlunit.exceptions.XpathException;
import org.energyos.espi.common.atom.UsagePointEntry;
import org.energyos.espi.common.domain.RetailCustomer;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.models.atom.LinkType;
import org.energyos.espi.datacustodian.domain.XMLTest;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.sun.syndication.feed.atom.Content;
import com.sun.syndication.feed.atom.Link;
import com.sun.syndication.io.FeedException;

public class EspiEntryTests extends XMLTest {

    private UsagePointEntry entry;

    @Before
    public void before() throws FeedException {
        UsagePoint usagePoint = new UsagePoint();
        usagePoint.setId(1L);
        usagePoint.setMRID("E8E75691-7F9D-49F3-8BE2-3A74EBF6BFC0");
        usagePoint.setDescription("Electric Meter");
        usagePoint.setPublished(newCalendar(2012, 10, 21));
        usagePoint.setUpdated(newCalendar(2012, 10, 28));
        RetailCustomer customer = new RetailCustomer();
        customer.setId(3L);
        usagePoint.setRetailCustomer(customer);
        entry = new UsagePointEntry(usagePoint);
    }

    @Test
    public void constructsEspiEntry() throws FeedException, SAXException, IOException, XpathException {

        assertNotNull("entry was null", entry);

        assertEquals("Electric Meter", entry.getTitle());
        assertEquals("Invalid entry id", "urn:uuid:E8E75691-7F9D-49F3-8BE2-3A74EBF6BFC0", entry.getId());
        assertNotNull("Published is null", entry.getPublished());
        assertNotNull("Updated is null", entry.getUpdated());
        assertTrue(entry.getOtherLinks().contains(entry.getSelfLink()));
        assertTrue(entry.getOtherLinks().contains(entry.getUpLink()));
        assertEquals("self", entry.getSelfLink().getRel());
        assertEquals("up", entry.getUpLink().getRel());

        Content content = (Content)entry.getContents().get(0);
        assertXpathExists("espi:UsagePoint", content.getValue());
    }

    @Test
    public void addRelatedLink() {
        String relatedHref = "related href";
        Link link = new Link();
        link.setRel(LinkType.RELATED);
        link.setHref(relatedHref);
        entry.getRelatedLinks().add(link);
        assertTrue(containsRelatedLink(relatedHref));
    }

    private boolean containsRelatedLink(String href) {
        for (Link link : entry.getRelatedLinks()) {
            if (link.getHref() == href) {
                return true;
            }
        }
        return false;
    }

    @Test
    public void setUpLinkHref() {
        Link link = new Link();
        link.setRel(LinkType.UP);
        link.setHref("up href");
        entry.setUpLink(link);
        assertEquals("up href", entry.getUpLink().getHref());
    }

    @Test
    public void setSelfLinkHref() {
        Link link = new Link();
        link.setRel(LinkType.UP);
        link.setHref("self href");
        entry.setSelfLink(link);
        assertEquals("self href", entry.getSelfLink().getHref());
    }
}