//package com.embl.assessment.controller.v1;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.converter.HttpMessageNotReadableException;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@Component
//public class RestResponseStatusExceptionResolver extends AbstractHandlerExceptionResolver {
//
//    å@Override
//    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
//            Exception ex) {
//            if(ex instanceof HttpMessageNotReadableException){
//                ModelAndView modelAndView = new ModelAndView();
//                modelAndView.addObject("message", ex.getMessage());
//                modelAndView.setViewName("Error");
//                modelAndView.setStatus(HttpStatus.BAD_REQUEST);
//                return modelAndView;
//            }
//            if (ex instanceof MethodArgumentNotValidException) {
//                ModelAndView modelAndView = new ModelAndView();
//                modelAndView.addObject("message", ex.getMessage());
//                modelAndView.setViewName("Error");
//                modelAndView.setStatus(HttpStatus.BAD_REQUEST);
//                return modelAndView;
//            }
//
//        return null;
//    }
//
//    public int getOrder() {
//        return -1;
//    }
//
//}
