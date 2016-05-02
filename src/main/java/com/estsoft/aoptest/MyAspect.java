package com.estsoft.aoptest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component			// Bean 생성
@Aspect				// Proxy Bean 생성 
public class MyAspect {

	@Before("execution( public ProductVo com.estsoft.aoptest.ProductService.findProduct(String) )")		// JoinPoint + PointCut
	public void before(){		// advice 정의
		System.out.println("call [Before Advice]");
	}
	
	@After("execution( * *..aoptest.ProductService.*(..))")
	public void after(){	
		System.out.println("call [After Advice]");
	}
	
	// 패키지 줄일 때는 *..     <- 이렇게 .. 2개써야함	
	
	// public은 생략가능(modifier)
	// 				모든타입		메소드	
	@Around("execution(* *..aoptest.*.*(..))")			// aoptest의 모든 메소드가 실행될 때 실행됨
	public ProductVo around(ProceedingJoinPoint pjp) throws Throwable{ 
		
		// Before
		System.out.println("call [around.before Advice]");
		ProductVo vo = (ProductVo) pjp.proceed();
		
		// After
		System.out.println("call [around.after Advice]");
		
		return vo;
	}
	
	//															받을 변수 이름
	@AfterReturning(value="execution(* *..aoptest.*.*(..))", returning="vo")
	public void afterRetruning(ProductVo vo){
		System.out.println("call [afterRetruning Advice] : "+vo);
	}
	
	@AfterThrowing(value="execution(* *..aoptest.*.*(..))", throwing="ex")
	public void afterThrowing(Throwable ex){
		System.out.println("call [afterThrowing] : "+ex);
	}
}
