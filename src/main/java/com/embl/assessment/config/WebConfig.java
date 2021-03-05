package com.embl.assessment.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * Config related to the web layer can be here, like global exception status handler
 * In this app we only use DefaultHandlerExceptionResolver, this may cause the response won't
 * include exception messages when 400 happened
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

}
