package com.basaki.controller;

import com.basaki.error.ErrorInfo;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import springfox.documentation.annotations.ApiIgnore;

/**
 * {@code CustomErrorController} used for showing error messages.
 * <p/>
 *
 * @author Indra Basak
 * @since 12/27/17
 */
@RestController
@ApiIgnore
@SuppressWarnings({ "squid:S1075" })
public class CustomErrorController implements ErrorController {

	private static final Logger log = LoggerFactory.getLogger(CustomErrorController.class);

	private static final String PATH = "/error";

	@Value("${debug:true}")
	private ErrorAttributeOptions debug;

	private final ErrorAttributes errorAttributes;

	@Autowired
	public CustomErrorController(final ErrorAttributes errorAttributes) {
		this.errorAttributes = errorAttributes;
	}

	@RequestMapping(value = PATH)
	ErrorInfo error(final HttpServletRequest request, final HttpServletResponse response) {
		final ErrorInfo info = new ErrorInfo();
		info.setCode(response.getStatus());
		final Map<String, Object> attributes = getErrorAttributes(request, debug);
		info.setMessage((String) attributes.get("message"));
		log.error((String) attributes.get("error"));

		return info;
	}

	public String getErrorPath() {
		return PATH;
	}

	private Map<String, Object> getErrorAttributes(final HttpServletRequest request,
			final ErrorAttributeOptions includeStackTrace) {
		final WebRequest webRequest = new ServletWebRequest(request);
		return errorAttributes.getErrorAttributes(webRequest,
				includeStackTrace);
	}
}
