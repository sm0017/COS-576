package com.spring.aop.catche.exa;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class ResizeAdvice implements MethodBeforeAdvice 
{
  
    public void before(Method method, Object[] args, Object target) throws Throwable {
          System.out.println("invoking " + method.getName() + " on " + target.getClass() + " Object");
          if(method.getName().equals("put")){
              System.out.println("before invoking " + method.getName());

              ((MyCache)target).resize();
          }
    }
}