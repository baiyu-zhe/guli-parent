package site.luming.servicebase.config.exceptionhandler;

import commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    //指定出现了什么类型的异常-全局
    @ExceptionHandler(Exception.class)
    @ResponseBody // 返回数据
    public R error(Exception e) {
        e.printStackTrace();
        return R.error().message("执行了全局方法");
    }

    //特定异常-除数不能为0
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody // 返回数据
    public R error(ArithmeticException e) {
        e.printStackTrace();
        return R.error().message("执行了ArithmeticException异常处理...");
    }

    //自定义异常
    @ExceptionHandler(GuliException.class)
    @ResponseBody // 返回数据
    public R error(GuliException e) {
        e.printStackTrace();
        log.error(e.getMessage());
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
