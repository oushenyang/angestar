/**
 * Copyright 2018-2020 stylefeng & fengshuonan (https://gitee.com/stylefeng)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.stylefeng.guns.sys.core.exception.aop;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.auth.exception.AuthException;
import cn.stylefeng.guns.base.auth.exception.OperationException;
import cn.stylefeng.guns.base.auth.exception.PermissionException;
import cn.stylefeng.guns.base.auth.exception.enums.AuthExceptionEnum;
//import cn.stylefeng.guns.core.exception.CardApiException;
import cn.stylefeng.guns.sys.core.auth.util.RedisUtil;
import cn.stylefeng.guns.sys.core.constant.state.RedisType;
import cn.stylefeng.guns.sys.core.exception.*;
import cn.stylefeng.guns.sys.core.exception.enums.BizExceptionEnum;
import cn.stylefeng.guns.sys.core.log.LogManager;
import cn.stylefeng.guns.sys.core.log.factory.LogTaskFactory;
//import cn.stylefeng.guns.sys.modular.system.model.result.ApiResultApi;
//import cn.stylefeng.guns.sys.modular.system.service.ApiResultService;
import cn.stylefeng.guns.sys.core.util.ApiResultUtil;
import cn.stylefeng.guns.sys.modular.system.model.result.ApiResultApi;
import cn.stylefeng.guns.sys.modular.system.service.SysApiResultService;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import cn.stylefeng.roses.kernel.model.response.ErrorResponseData;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static cn.stylefeng.roses.core.util.HttpContext.getIp;
import static cn.stylefeng.roses.core.util.HttpContext.getRequest;

/**
 * 全局的的异常拦截器（拦截所有的控制器）（带有@RequestMapping注解的方法上都会拦截）
 *
 * @author fengshuonan
 * @date 2016年11月12日 下午3:19:56
 */
@ControllerAdvice
@Order(-100)
@Slf4j
public class GlobalExceptionHandler {
    private final RedisUtil redisUtil;
    private final SysApiResultService sysApiResultService;

    public GlobalExceptionHandler(RedisUtil redisUtil, SysApiResultService sysApiResultService) {
        this.redisUtil = redisUtil;
        this.sysApiResultService = sysApiResultService;
    }

    /**
     * 参数校验错误
     *
     * @author fengshuonan
     * @Date 2020/2/5 11:50 下午
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponseData handleError(MissingServletRequestParameterException e) {
        log.warn("Missing Request Parameter", e);
        String message = String.format("Missing Request Parameter: %s", e.getParameterName());
        return new ErrorResponseData(400, message);
    }

    /**
     * 参数校验错误
     *
     * @author fengshuonan
     * @Date 2020/2/6 10:18 上午
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponseData handleError(MethodArgumentTypeMismatchException e) {
        log.warn("Method Argument Type Mismatch", e);
        String message = String.format("Method Argument Type Mismatch: %s", e.getName());
        return new ErrorResponseData(400, message);
    }

    /**
     * 参数校验错误
     *
     * @author fengshuonan
     * @Date 2020/2/6 10:19 上午
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponseData handleError(MethodArgumentNotValidException e) {
        log.warn("Method Argument Not Valid", e);
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        String message = String.format("%s:%s", error.getField(), error.getDefaultMessage());
        return new ErrorResponseData(400, message);
    }

    /**
     * 参数校验错误异常
     *
     * @author fengshuonan
     * @Date 2020/2/6 9:49 上午
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponseData handleError(BindException e) {
        log.warn("Bind Exception", e);
        FieldError error = e.getFieldError();
        String message = String.format("%s:%s", error.getField(), error.getDefaultMessage());
        return new ErrorResponseData(400, message);
    }

    /**
     * 参数校验错误异常
     *
     * @author fengshuonan
     * @Date 2020/2/8 12:20
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponseData handleError(ConstraintViolationException e) {
        log.warn("Constraint Violation", e);
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        ConstraintViolation<?> violation = violations.iterator().next();
        String path = ((PathImpl) violation.getPropertyPath()).getLeafNode().getName();
        String message = String.format("%s:%s", path, violation.getMessage());
        return new ErrorResponseData(400, message);
    }

    /**
     * 参数校验错误异常
     *
     * @author fengshuonan
     * @Date 2020/2/6 9:49 上午
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponseData handleError(HttpMessageNotReadableException e) {
        log.warn("HttpMessageNotReadableException ", e);
        String message = String.format("HttpMessageNotReadableException:%s", e.getMessage());
        return new ErrorResponseData(400, message);
    }

    /**
     * 认证异常--认证失败（账号密码错误，账号被冻结，token过期等）
     *
     * @author fengshuonan
     * @Date 2020/2/6 11:14 上午
     */
    @ExceptionHandler(AuthException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorResponseData unAuth(AuthException e) {
        return new ErrorResponseData(e.getCode(), e.getMessage());
    }

    /**
     * 认证异常--没有访问权限
     *
     * @author fengshuonan
     * @Date 2020/2/6 11:14 上午
     */
    @ExceptionHandler(PermissionException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorResponseData permissionExpection(PermissionException e) {
        return new ErrorResponseData(e.getCode(), e.getMessage());
    }

    /**
     * 接口系统返回异常
     *
     * @author fengshuonan
     * @Date 2020/2/6 11:14 上午
     */
    @ExceptionHandler(SystemApiException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ApiResult systemApiException(SystemApiException e) {
        return ApiResult.resultError(e.getCode(), e.getMessage(),e.getSuccess());
    }

    /**
     * 接口单码登录自定义返回异常
     *
     * @author fengshuonan
     * @Date 2020/2/6 11:14 上午
     */
    @ExceptionHandler(CardLoginException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object cardLoginException(CardLoginException e) {
        ApiResultApi apiResultApi = (ApiResultApi) redisUtil.get(RedisType.API_RESULT.getCode() + e.getAppId() + "-" +  e.getCode());
        if (ObjectUtil.isNull(apiResultApi)){
            apiResultApi = sysApiResultService.findApiResultApi(e.getAppId(),e.getCode());
            if (ObjectUtil.isNotNull(apiResultApi)){
                redisUtil.set(RedisType.API_RESULT.getCode() + e.getAppId() + "-" +  e.getCode(), apiResultApi,604800);
            }
        }
        Object object = null;
        //如果没有自定义
        if (StringUtils.isEmpty(apiResultApi.getCustomResultData())){
            if (apiResultApi.getResultSuccess()){
                cn.hutool.json.JSONObject json = JSONUtil.createObj();
                json.set("expireTime",DateUtil.format(e.getExpireTime(),"yyyy-MM-dd HH:mm:ss"));
                json.set("token",e.getData());
                if (StringUtils.isNotEmpty(e.getHoldCheck())){
                    json.set("holdCheck", e.getHoldCheck());
                }
                object = ApiResult.resultSuccess(apiResultApi.getResultCode(), apiResultApi.getResultRemark(),json,apiResultApi.getResultSuccess());
            }else {
                if (StringUtils.isNotEmpty(e.getHoldCheck())){
                    cn.hutool.json.JSONObject json = JSONUtil.createObj();
                    json.set("holdCheck",e.getHoldCheck());
                    object = ApiResult.resultSuccess(apiResultApi.getResultCode(), apiResultApi.getResultRemark(),json,apiResultApi.getResultSuccess());
                }else {
                    object = ApiResult.resultError(apiResultApi.getResultCode(), apiResultApi.getResultRemark(),apiResultApi.getResultSuccess());
                }
            }
        }else {
            String customResultData = apiResultApi.getCustomResultData();
            //TODO
            if (StringUtils.contains(customResultData, "%holdCheck%")){
                if (StringUtils.isNotEmpty(e.getHoldCheck())){
                    customResultData = customResultData.replaceAll("%holdCheck%",e.getHoldCheck());
                }else {
                    customResultData = customResultData.replaceAll("%holdCheck%","");
                }
            }
            if (StringUtils.contains(customResultData, "%token%")){
                customResultData = customResultData.replaceAll("%token%",String.valueOf(e.getData()));
            }
            if (StringUtils.contains(customResultData, "%timestamp10%")){
                customResultData = customResultData.replaceAll("%timestamp10%",String.valueOf(System.currentTimeMillis() / 1000));
            }
            if (StringUtils.contains(customResultData, "%timestamp13%")){
                customResultData = customResultData.replaceAll("%timestamp13%",String.valueOf(System.currentTimeMillis()));
            }
            if (StringUtils.contains(customResultData, "%expireTime%")){
                customResultData = customResultData.replaceAll("%expireTime%",DateUtil.format(e.getExpireTime(),"yyyy-MM-dd HH:mm:ss"));
            }
            if (StringUtils.contains(customResultData, "%currentTime%")){
                customResultData = customResultData.replaceAll("%currentTime%",DateUtil.now());
            }
            object = customResultData;
        }
        object = ApiResultUtil.setAlgorithm(object,e.getAppInfoApi());
        return object;
    }

    /**
     * 检测单码用户状态异常
     *
     * @author fengshuonan
     * @Date 2020/2/6 11:14 上午
     */
    @ExceptionHandler(CheckCardStatusException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object checkCardStatusException(CheckCardStatusException e) {
        ApiResultApi apiResultApi = (ApiResultApi) redisUtil.get(RedisType.API_RESULT.getCode() + e.getAppId() + "-" +  e.getCode());
        if (ObjectUtil.isNull(apiResultApi)){
            apiResultApi = sysApiResultService.findApiResultApi(e.getAppId(),e.getCode());
            if (ObjectUtil.isNotNull(apiResultApi)){
                redisUtil.set(RedisType.API_RESULT.getCode() + e.getAppId() + "-" +  e.getCode(), apiResultApi,604800);
            }
        }
        Object object = null;
        //如果没有自定义
        if (StringUtils.isEmpty(apiResultApi.getCustomResultData())){
            if (StringUtils.isNotEmpty(e.getHoldCheck())){
                Map<String, Object> map = new HashMap<>(2);
                map.put("holdCheck", e.getHoldCheck());
                JSONObject json = new JSONObject(map);
                object = ApiResult.resultSuccess(apiResultApi.getResultCode(), apiResultApi.getResultRemark(),json,apiResultApi.getResultSuccess());
            }else {
                object = ApiResult.resultError(apiResultApi.getResultCode(), apiResultApi.getResultRemark(),apiResultApi.getResultSuccess());
            }
        }else {
            String customResultData = apiResultApi.getCustomResultData();
            //TODO
            if (StringUtils.contains(customResultData, "%holdCheck%")){
                if (StringUtils.isNotEmpty(e.getHoldCheck())){
                    customResultData = customResultData.replaceAll("%holdCheck%",e.getHoldCheck());
                }else {
                    customResultData = customResultData.replaceAll("%holdCheck%","");
                }
            }
            if (StringUtils.contains(customResultData, "%token%")){
                customResultData = customResultData.replaceAll("%token%",String.valueOf(e.getData()));
            }
            if (StringUtils.contains(customResultData, "%timestamp10%")){
                customResultData = customResultData.replaceAll("%timestamp10%",String.valueOf(System.currentTimeMillis() / 1000));
            }
            if (StringUtils.contains(customResultData, "%timestamp13%")){
                customResultData = customResultData.replaceAll("%timestamp13%",String.valueOf(System.currentTimeMillis()));
            }
            if (StringUtils.contains(customResultData, "%expireTime%")){
                customResultData = customResultData.replaceAll("%expireTime%",DateUtil.format(e.getExpireTime(),"yyyy-MM-dd HH:mm:ss"));
            }
            if (StringUtils.contains(customResultData, "%currentTime%")){
                customResultData = customResultData.replaceAll("%currentTime%",DateUtil.now());
            }
            object = customResultData;
        }
        object = ApiResultUtil.setAlgorithm(object,e.getAppInfoApi());
        return object;
    }

    /**
     * 试用自定义返回异常
     *
     * @author fengshuonan
     * @Date 2020/2/6 11:14 上午
     */
    @ExceptionHandler(TrialException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object trialException(TrialException e) {
        ApiResultApi apiResultApi = (ApiResultApi) redisUtil.get(RedisType.API_RESULT.getCode() + e.getAppId() + "-" +  e.getCode());
        if (ObjectUtil.isNull(apiResultApi)){
            apiResultApi = sysApiResultService.findApiResultApi(e.getAppId(),e.getCode());
            if (ObjectUtil.isNotNull(apiResultApi)){
                redisUtil.set(RedisType.API_RESULT.getCode() + e.getAppId() + "-" +  e.getCode(), apiResultApi,604800);
            }
        }
        //如果没有自定义
        if (StringUtils.isEmpty(apiResultApi.getCustomResultData())){
            if (apiResultApi.getResultSuccess()){
                return ApiResult.success(apiResultApi.getResultCode(), e.getData()+",剩余"+e.getExpireTimeOrNum(),apiResultApi.getResultSuccess());
            }else {
                return ApiResult.success(apiResultApi.getResultCode(), String.valueOf(e.getData()),apiResultApi.getResultSuccess());
            }
        }else {
            String customResultData = apiResultApi.getCustomResultData();
            //TODO
            if (StringUtils.contains(customResultData, "%appCode%")){
                if (StringUtils.isNotEmpty(e.getAppCode())){
                    customResultData = customResultData.replaceAll("%appCode%",e.getAppCode());
                }else {
                    customResultData = customResultData.replaceAll("%appCode%","");
                }
            }
            if (StringUtils.contains(customResultData, "%timestamp10%")){
                customResultData = customResultData.replaceAll("%timestamp10%",String.valueOf(System.currentTimeMillis() / 1000));
            }
            if (StringUtils.contains(customResultData, "%timestamp13%")){
                customResultData = customResultData.replaceAll("%timestamp13%",String.valueOf(System.currentTimeMillis()));
            }
            if (StringUtils.contains(customResultData, "%expireTime%")){
                customResultData = customResultData.replaceAll("%expireTime%",e.getExpireTimeOrNum());
            }
            if (StringUtils.contains(customResultData, "%currentTime%")){
                customResultData = customResultData.replaceAll("%currentTime%",DateUtil.now());
            }
            return customResultData;
        }
    }

    /**
     * 验证码错误异常
     *
     * @author fengshuonan
     * @Date 2020/2/6 11:14 上午
     */
    @ExceptionHandler(InvalidKaptchaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponseData credentials(InvalidKaptchaException e) {
        String username = getRequest().getParameter("username");
        LogManager.me().executeLog(LogTaskFactory.loginLog(username, "验证码错误", getIp()));
        return new ErrorResponseData(AuthExceptionEnum.VALID_CODE_ERROR.getCode(), AuthExceptionEnum.VALID_CODE_ERROR.getMessage());
    }

    /**
     * 拦截业务异常
     *
     * @author fengshuonan
     * @Date 2020/2/6 11:14 上午
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponseData bussiness(ServiceException e) {
        log.error("业务异常:", e);
        if (LoginContextHolder.getContext().hasLogin()) {
            LogManager.me().executeLog(LogTaskFactory.exceptionLog(LoginContextHolder.getContext().getUserId(), e));
        }
        getRequest().setAttribute("tip", e.getMessage());
        return new ErrorResponseData(e.getCode(), e.getMessage());
    }

    /**
     * 拦截操作异常
     *
     * @author fengshuonan
     * @Date 2020/2/6 11:14 上午
     */
    @ExceptionHandler(OperationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponseData operationException(OperationException e) {
        getRequest().setAttribute("tip", e.getMessage());
        return new ErrorResponseData(e.getCode(), e.getMessage());
    }

    /**
     * 拦截未知的运行时异常
     *
     * @author fengshuonan
     * @Date 2020/2/6 11:15 上午
     */
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponseData notFount(Throwable e) {
        log.error("运行时异常:", e);
        if (LoginContextHolder.getContext().hasLogin()) {
            LogManager.me().executeLog(LogTaskFactory.exceptionLog(LoginContextHolder.getContext().getUserId(), e));
        }
        String message = String.format("服务器未知运行时异常: %s", e.getMessage());
        getRequest().setAttribute("tip", message);
        return new ErrorResponseData(BizExceptionEnum.SERVER_ERROR.getCode(), message);
    }
}
