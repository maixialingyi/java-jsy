package com.jsy.learn.meituan.http.aspect;/*
package com.mid.catexception.adapter.http.aspect;

import com.mid.catexception.adapter.http.exception.LecheckIllegalArgumentException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;


*/
/**
 * 参数校验切面
 *//*

@Aspect
@Slf4j
@Component
public class ParamValidAspect {
    @Around(value = "execution(* com.mid.catexception.adapter.http.controller..*.*(..))")
    public Object validate(ProceedingJoinPoint joinPoint) throws Throwable {
        BindingResult bs = null;
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            for (Object o : args) {
                if (o instanceof BindingResult) {
                    bs = (BindingResult) o;
                    break;
                }
            }
        }
        if (bs != null && bs.hasErrors()) {
            validateAnnotation(bs);
        }

        return joinPoint.proceed();
    }

    */
/**
     * 注解校验结果
     *
     * @param bindingResult
     *//*

    private void validateAnnotation(BindingResult bindingResult) throws LecheckIllegalArgumentException {
        StringBuilder msg = new StringBuilder();
        List<FieldError> errorList = bindingResult.getFieldErrors();
        for (FieldError error : errorList) {
            msg.append("参数：");
            msg.append(error.getField());
            msg.append(" ");
            msg.append(error.getDefaultMessage());
            msg.append("!");
        }
        throw new LecheckIllegalArgumentException(msg.toString());
    }
}
*/
