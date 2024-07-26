package com.basaki.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.basaki.error.ErrorInfo;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;

/**
 * {@code CustomErrorControllerTest} represents unit test for {@code
 * CustomErrorController}.
 * <p/>
 *
 * @author Indra Basak
 * @since 02/11/18
 */
public class CustomErrorControllerTest {

	@Mock
	private ErrorAttributes errorAttributes;

	@InjectMocks
	private CustomErrorController controller;

	private MockHttpServletRequest request;

	private MockHttpServletResponse response;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(
				new ServletRequestAttributes(request));

		response = new MockHttpServletResponse();
	}

	@Test
	public void testError() {
		final Map<String, Object> attributes = new HashMap<>();
		attributes.put("message", "test-message");
		attributes.put("error", "test-error");

		when(errorAttributes.getErrorAttributes(any(WebRequest.class),
				any(ErrorAttributeOptions.class))).thenReturn(attributes);

		final ErrorInfo errorInfo = controller.error(request, response);
		assertNotNull(errorInfo);
		assertEquals("test-message", errorInfo.getMessage());
		assertNotNull(controller.getErrorPath());
	}
}
