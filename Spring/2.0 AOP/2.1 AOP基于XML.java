2.1 AOP基于XML

目标类：接口 + 实现
切面类：编写多个通知，采用aspectj 通知名称任意（方法名任意）
aop编程：将通知应用到目标类

public class MyAspect {
	
	public void myBefore(JoinPoint joinPoint){
		System.out.println("前置通知 ： " + joinPoint.getSignature().getName());
	}
	
	public void myAfterReturning(JoinPoint joinPoint,Object ret){
		System.out.println("后置通知 ： " + joinPoint.getSignature().getName() + " , -->" + ret);
	}
	
	public Object myAround(ProceedingJoinPoint joinPoint) throws Throwable{
		System.out.println("前");
		//手动执行目标方法
		Object obj = joinPoint.proceed();
		
		System.out.println("后");
		return obj;
	}
	
	public void myAfterThrowing(JoinPoint joinPoint,Throwable e){
		System.out.println("抛出异常通知 ： " + e.getMessage());
	}
	
	public void myAfter(JoinPoint joinPoint){
		System.out.println("最终通知");
	}
}

<bean id="userServiceId" class="cn.aspect.UserServiceImpl"></bean>
<bean id="myAspectId" class="cn.aspect.MyAspect"></bean>
	<!--  aop编程 
		<aop:aspect> 将切面类 声明“切面”，从而获得通知（方法）
			ref 切面类引用
		<aop:pointcut> 声明一个切入点，所有的通知都可以使用。
			expression 切入点表达式
			id 名称，用于其它通知引用
	-->
<aop:config>
		<aop:aspect ref="myAspectId">
			<aop:pointcut expression="execution(* cn.aspect.UserServiceImpl.*(..))" id="myPointCut"/>
			
			<!-- 3.1 前置通知 
				<aop:before method="" pointcut="" pointcut-ref=""/>
					method : 通知，及方法名
					pointcut :切入点表达式，此表达式只能当前通知使用。
					pointcut-ref ： 切入点引用，可以与其他通知共享切入点。
				通知方法格式：public void myBefore(JoinPoint joinPoint){
					参数1：org.aspectj.lang.JoinPoint  用于描述连接点（目标方法），获得目标方法名等
				例如：
			<aop:before method="myBefore" pointcut-ref="myPointCut"/>
			-->
			<aop:before method="myBefore" pointcut-ref="myPointCut"/>
			<!-- 3.2后置通知  ,目标方法后执行，获得返回值
				<aop:after-returning method="" pointcut-ref="" returning=""/>
					returning 通知方法第二个参数的名称
				通知方法格式：public void myAfterReturning(JoinPoint joinPoint,Object ret){
					参数1：连接点描述
					参数2：类型Object，参数名 returning="ret" 配置的
				例如：
			<aop:after-returning method="myAfterReturning" pointcut-ref="myPointCut" returning="ret" />
			-->
			
			<!-- 3.3 环绕通知 
				<aop:around method="" pointcut-ref=""/>
				通知方法格式：public Object myAround(ProceedingJoinPoint joinPoint) throws Throwable{
					返回值类型：Object
					方法名：任意
					参数：org.aspectj.lang.ProceedingJoinPoint
					抛出异常
				执行目标方法：Object obj = joinPoint.proceed();
				例如：
			<aop:around method="myAround" pointcut-ref="myPointCut"/>
			-->
			<!-- 3.4 抛出异常
				<aop:after-throwing method="" pointcut-ref="" throwing=""/>
					throwing ：通知方法的第二个参数名称
				通知方法格式：public void myAfterThrowing(JoinPoint joinPoint,Throwable e){
					参数1：连接点描述对象
					参数2：获得异常信息，类型Throwable ，参数名由throwing="e" 配置
				例如：
			<aop:after-throwing method="myAfterThrowing" pointcut-ref="myPointCut" throwing="e"/>
			-->
			<!-- 3.5 最终通知 -->			
			<aop:after method="myAfter" pointcut-ref="myPointCut"/>
				
		</aop:aspect>
</aop:config>


像书写的形式有下面两种：
<aop:config>
	<aop:aspect>
		<aop:pointcut expression="" id=""/>
		<aop:around method=""/>
	</aop:aspect>
</aop:config>
	
	
<aop:config>
	<aop:pointcut expression="" id=""/>
	<aop:aspect>
		<aop:around method=""/>
	</aop:aspect>
</aop:config>
