package com.shuyue.inventory_server.base.config;

import com.google.common.collect.Lists;
import com.shuyue.inventory_server.annotation.RepeatDisable;
import com.shuyue.inventory_server.controller.BaseController;
import com.shuyue.inventory_server.entity.user.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author haohua.rong
 * @description
 * @contact 13512835407@163.com
 * @date 2022/6/21 11:06
 */
@Component
@Aspect
@Slf4j
public class RepeatDisableAspect implements Ordered {
    @Override
    public int getOrder() {
        return 100;
    }

    /**
     * 使用ConcurrentHashMap 做缓存
     */
    private static final ConcurrentHashMap<String, Object> CACHES = new ConcurrentHashMap<>(200);
    // 使用ScheduledThreadPoolExecutor 作为缓存释放处理器
    private static final ScheduledThreadPoolExecutor EXECUTOR = new ScheduledThreadPoolExecutor(5, new ThreadPoolExecutor.DiscardPolicy());

    /**
     * 枚举对象保证INSTANCE单例
     */
    enum Locker {
        /**
         * 构建锁实例
         */
        INSTANCE() {
            @Override
            public boolean lock(final String key, Object value) {
                return Objects.isNull(CACHES.putIfAbsent(key, value));
            }

            @Override
            public void unLock(final boolean lock, final String key, final int delaySeconds) {
                if (lock) {
                    EXECUTOR.schedule(() -> {
                        CACHES.remove(key);
                    }, delaySeconds, TimeUnit.SECONDS);
                }
            }
        };

        abstract boolean lock(final String key, Object value);

        abstract void unLock(final boolean lock, final String key, final int delaySeconds);
    }

    @Pointcut("@annotation(com.shuyue.inventory_server.annotation.RepeatDisable)")
    public void point() {
    }

    @Around("point()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        RepeatDisable form = method.getAnnotation(RepeatDisable.class);
        String key = getCacheKey(method, pjp, form);

        Locker locker = Locker.INSTANCE;
        boolean lock = false;
        try {
            lock = locker.lock(key, key);
            if (lock) {
                return pjp.proceed();
            } else {
                throw new RuntimeException("请勿重复请求");
            }
        } finally {
            locker.unLock(lock, key, 2);
        }
    }

    /**
     * 缓存key生成方式
     *
     * @param method
     * @param pjp
     * @param form
     * @return
     */
    private String getCacheKey(Method method, ProceedingJoinPoint pjp, RepeatDisable form) {
//        Map<String, Object> map=getRequestParamsByJoinPoint(pjp);
//        User currentUser = ContextUtils.getCurrentUser();
//        String jsonString = JSON.toJSONString(map);
//        log.info("RepeatDisable {}", jsonString);
//        return DigestUtils.md5Hex(jsonString + method.getName() + form.name() + currentUser.getUserNo());
        User currentUser = BaseController.getUserInfo();
        String userNo = "";
        if (Objects.nonNull(currentUser)) {
            userNo = currentUser.getUserNo();
        }
        String classType = pjp.getTarget().getClass().getName();
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        String[] paramNames = methodSignature.getParameterNames();
        Class<?>[] parameterTypes = methodSignature.getParameterTypes();
        List<String> parameterTypesList = Lists.newArrayList(parameterTypes).stream().map(Class::getName).collect(Collectors.toList());
        String methodReference = classType + method.getName() + Lists.newArrayList(paramNames).toString() + parameterTypesList.toString();
        return DigestUtils.md5Hex(methodReference + form.name() + userNo);
    }

    private Map<String, Object> getRequestParamsByJoinPoint(JoinPoint joinPoint) {
        //参数名
        String[] paramNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        //参数值
        Object[] paramValues = joinPoint.getArgs();

        return buildRequestParam(paramNames, paramValues);
    }

    private Map<String, Object> buildRequestParam(String[] paramNames, Object[] paramValues) {
        Map<String, Object> requestParams = new HashMap<>();
        for (int i = 0; i < paramNames.length; i++) {
            Object value = paramValues[i];

            if (value instanceof HttpServletRequest || value instanceof HttpServletResponse || value instanceof Errors) {
                continue;
            }

            //如果是文件对象
            if (value instanceof MultipartFile) {
                MultipartFile file = (MultipartFile) value;
                value = file.getOriginalFilename();  //获取文件名
            }

            requestParams.put(paramNames[i], value);
        }

        return requestParams;
    }

}
