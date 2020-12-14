package com.senko.microservices.composite.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import com.senko.api.core.product.Product;
import com.senko.api.event.Event;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static com.senko.api.event.Event.Type.CREATE;
import static com.senko.api.event.Event.Type.DELETE;

public class IsSameEventTests {

	ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testEventObjectCompare() throws JsonProcessingException {

    	// Event #1 and #2 are the same event, but occurs as different times
		// Event #3 and #4 are different events
		Event<Integer, Product> event1 = new Event<>(CREATE, 1, new Product(1, "name", 1, null));
		Event<Integer, Product> event2 = new Event<>(CREATE, 1, new Product(1, "name", 1, null));
		Event<Integer, Product> event3 = new Event<>(DELETE, 1, null);
		Event<Integer, Product> event4 = new Event<>(CREATE, 1, new Product(2, "name", 1, null));

		String event1JSon = mapper.writeValueAsString(event1);

		MatcherAssert.assertThat(event1JSon, Matchers.is(IsSameEvent.sameEventExceptCreatedAt(event2)));
		MatcherAssert.assertThat(event1JSon, Matchers.not(IsSameEvent.sameEventExceptCreatedAt(event3)));
		MatcherAssert.assertThat(event1JSon, Matchers.not(IsSameEvent.sameEventExceptCreatedAt(event4)));
    }
}
