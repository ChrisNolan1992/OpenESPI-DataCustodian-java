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

package org.energyos.espi.datacustodian.utils;

import org.energyos.espi.datacustodian.domain.IntervalBlock;
import org.energyos.espi.datacustodian.models.atom.FeedType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.xml.bind.JAXBException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("/spring/test-context.xml")
public class ATOMMarshallerIntervalBlockTests {

    @Autowired
    private ATOMMarshaller marshaller;

    private ClassPathResource sourceFile;
    private FeedType feedType;
    private IntervalBlock intervalBlock;

    @Before
    public void before() throws IOException, JAXBException {
        sourceFile = new ClassPathResource("/fixtures/IntervalBlock.xml");
        feedType = marshaller.unmarshal(sourceFile.getInputStream());
        intervalBlock = feedType.getEntries().get(2).getContent().getIntervalBlock();
    }

    @Test
    public void unmarshal_returnsIntervalBlock() throws IOException, JAXBException {
        assertEquals(IntervalBlock.class, intervalBlock.getClass());
    }

    @Test
    public void unmarshal_returnsIntervalBlockWithInterval() throws IOException, JAXBException {
        assertEquals(Long.valueOf(86400L), intervalBlock.getInterval().getDuration());
        assertEquals(Long.valueOf(1330578000L), intervalBlock.getInterval().getStart());
    }
}
