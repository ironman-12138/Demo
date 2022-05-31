package org.example.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.example.common.utils.HttpUtil;
import org.example.common.utils.JsonUtil;
import org.example.common.utils.TLocalHelper;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

@Order(1)
@Slf4j
@Component
@Aspect
public class AllLogAspect {

    /**
     * 记录接口日志
     *
     * @param proceeding
     * @return
     */
    @Around(value = "execution(* org.example..controller..*.*(..))")
    public Object goodsLog(ProceedingJoinPoint proceeding) throws Throwable {
        Long startTime = System.currentTimeMillis();
        // 创建流水号
        TLocalHelper.createSeq();
        Thread.currentThread().setName(TLocalHelper.getSeq());
        HttpServletRequest request = HttpUtil.getRequest();
        Signature signature = proceeding.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        //请求参数
        String requestParam = JsonUtil.getJson(AspectUtil.getParameter(method, proceeding.getArgs()));
        if (request != null)
            log.info("接口请求路径:{},请求参数:{},流水号:{}", request.getRequestURI(), requestParam, TLocalHelper.getSeq());
        Object result = proceeding.proceed();
        if (request != null)
            log.info("接口执行结束,耗时:{}ms,返回数据:{},流水号:{}", System.currentTimeMillis() - startTime, Objects.nonNull(result) ? result.toString() : "null", TLocalHelper.getSeq());
        return result;
    }

}
