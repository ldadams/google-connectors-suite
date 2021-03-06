/**
 * Mule Google Calendars Cloud Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.google.calendar.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;
import org.mule.module.google.calendar.automation.CalendarUtils;
import org.mule.module.google.calendar.model.Calendar;
import org.mule.module.google.calendar.model.Event;
import org.mule.module.google.calendar.model.EventDateTime;
import org.mule.modules.google.api.client.batch.BatchResponse;
import org.mule.modules.tests.ConnectorTestUtils;

public class BatchDeleteEventTestCases extends GoogleCalendarTestParent {

	@Ignore("Needs to be review")
	@Before
	public void setUp() throws Exception {
		
		loadTestRunMessage("batchDeleteEvent");

		// Insert calendar and get reference to retrieved calendar
		Calendar calendar = runFlowAndGetPayload("create-calendar");
		
		// Replace old calendar instance with new instance
		upsertOnTestRunMessage("calendarRef", calendar);
		upsertOnTestRunMessage("calendarId", calendar.getId());			

		// Get the sample event
		Event sampleEvent = getTestRunMessageValue("sampleEvent");
		
		// Get start and end time beans.
		EventDateTime eventStartTime = sampleEvent.getStart();
		EventDateTime eventEndTime = sampleEvent.getEnd();
		Integer numEvents = getTestRunMessageValue("numEvents");
		
		// Instantiate the events that we want to batch insert
		List<Event> events = new ArrayList<Event>();
		for (int i = 0; i < numEvents; i++) {
			Event event = CalendarUtils.getEvent("Test Event", eventStartTime, eventEndTime);
			events.add(event);
		}
		
		// Store the successfully persisted events in testObjects for later access
		BatchResponse<Event> batchResponse = insertEvents(calendar, events);
		List<Event> successful = batchResponse.getSuccessful();
		upsertOnTestRunMessage("calendarEventsRef", successful);
		
	}
	
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testBatchDeleteEvent() {
		try {			
						
			Calendar calendar = getTestRunMessageValue("calendarRef");
			List<Event> succcessful = getTestRunMessageValue("calendarEventsRef");
			 
			deleteEvents(calendar, succcessful);
			
			List<Event> events = runFlowAndGetPayload("get-all-events");
			assertTrue(events.isEmpty());
		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
	
	@After
	public void tearDown() throws Exception {
			String calendarId = getTestRunMessageValue("calendarId");
			deleteCalendar(calendarId);
	}
	
}
