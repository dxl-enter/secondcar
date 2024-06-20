package com.dippy.common;

import com.dippy.common.handle.MyException;
import com.dippy.common.result.Result;
import com.dippy.common.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLSyntaxErrorException;

/*
@RestControllerAdvice:当我们定义了一个自定义返回参数格式时，希望得到统一的返回，如果在运行时发现了异常，也希望将异常统一返回
        拦截异常并统一处理
        让我们的异常得到期望的返回格式
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandle {


    /**
     * 400
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)// 状态码，  BAD_REQUEST代表400，
    @ExceptionHandler(value = RuntimeException.class)
    public Result handler(RuntimeException e) {
        log.error("400时异常：----------{}", e);
        return Result.fail(e.getMessage());
    }


    // @ResponseStatus(HttpStatus.NOT_FOUND)// 状态码，  BAD_REQUEST代表400，
    // @ExceptionHandler(value = RuntimeException.class)
    // public Result handler(NotFoundException e) {
    //     log.error("404时异常：----------{}", e);
    //     return Result.fail(e.getMessage());
    // }

    /**
     * 权限不足异常
     *
     * @param e
     * @return
     */
    // @ResponseStatus(HttpStatus.BAD_REQUEST)// 状态码，  BAD_REQUEST代表400，
    @ExceptionHandler(value = AccessDeniedException.class)
    public Result handler(AccessDeniedException e, HttpServletResponse response) throws IOException {
        log.error("权限不足时异常：----------{}", e);
        response.setHeader("Content-Type", "application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write("{\"status\":\"error\",\"msg\":\"权限不足，请联系管理员\"}");
        writer.flush();
        writer.close();
        return Result.fail(e.getMessage());
    }


    /**
     * 最大范围的异常运行时异常
     *
     * @param e
     * @return
     */

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "系统繁忙,请联系管理员!")
    @ExceptionHandler(value = Exception.class)
    public Result maxHandler(Exception e) {
        log.error("Exception最大范围异常：----------{}", e.toString());
        return Result.fail(e.getMessage());
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    public Result usernameHandler(UsernameNotFoundException e) {
        log.error("用户不存在异常：----------{}", e);
        return Result.fail(e.getMessage());
    }


    /**
     * 实体类校验异常处理
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)// 状态码，  UNAUTHORIZED代表401， 可以用来代表权限不够等
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result handler(MethodArgumentNotValidException e) {
        log.error("实体校验异常：----------{}", e);

        BindingResult bindingResult = e.getBindingResult();
        ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();

        return Result.fail(objectError.getDefaultMessage());
    }

    /**
     * 断言异常
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)// 状态码，  UNAUTHORIZED代表401， 可以用来代表权限不够等
    @ExceptionHandler(value = IllegalArgumentException.class)
    public Result handler(IllegalArgumentException e) {
        log.error("断言的异常：----------{}", e);
        return Result.fail(e.getMessage());
    }


    /**
     * mq异常
     *
     * @param e
     * @return
     */
    // @ResponseStatus(HttpStatus.UNAUTHORIZED)// 状态码，  UNAUTHORIZED代表401， 可以用来代表权限不够等
    @ExceptionHandler(value = ListenerExecutionFailedException.class)
    public Result handler(ListenerExecutionFailedException e) {
        log.error("MQ的异常：----------{}", e);
        return Result.fail(e.getMessage());
    }

    //DataAccessResourceFailureException

    /**
     * ES异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = DataAccessResourceFailureException.class)
    public Result handler(DataAccessResourceFailureException e) {
        log.error("ES的异常：----------{}", e);
        return Result.fail(e.getMessage());
    }


    /**
     * 处理业务异常,我们自己定义的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MyException.class)
    @ResponseBody
    @ResponseStatus()// 状态码，  UNAUTHORIZED代表401， 可以用来代表权限不够等
    public Result error(MyException e) {
        e.printStackTrace();
        log.error(e.getErrMsg());
        return Result.error(e.getCode(), e.getErrMsg());
    }

    @ExceptionHandler(SQLSyntaxErrorException.class)
    @ResponseBody
    // sql异常
    public Result sqlSyntaxErrorException(SQLSyntaxErrorException sqlException) {
        sqlException.printStackTrace();
        log.error("sql异常  {}",sqlException.getMessage());
        return Result.error(ResultCode.EXCEPTION_SQL.getCode(),ResultCode.EXCEPTION_SQL.getMessage());
    }

    /**
     * 处理请求方法异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    @ResponseStatus()// 状态码，  UNAUTHORIZED代表401， 可以用来代表权限不够等
    public Result httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        e.printStackTrace();
        log.error("请求的方法不支持的异常：----------{}", e);
        return Result.fail(e.getMessage());
    }



}
