package it.sample.rulesengine.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class UrlLogInterceptor extends HandlerInterceptorAdapter {

	private static final Logger log = LoggerFactory.getLogger(UrlLogInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	log.debug("--- UrlLogInterceptor.preHandle ---");
        return true;
        
    }
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    	 log.debug("--- UrlLogInterceptor.postHandle ---"); 
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    	log.debug("--- UrlLogInterceptor.afterCompletion ---");
    }
	
} //end class
