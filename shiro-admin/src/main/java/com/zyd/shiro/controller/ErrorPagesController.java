/**
 * MIT License
 * Copyright (c) 2018 yadong.zhang
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.zyd.shiro.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @project: springboot-shiro
 * @description: 重写BasicErrorController, 主要负责系统的异常页面的处理以及错误信息的显示
 * 注意: 这个类里面的代码一定不能有异常或者潜在异常发生,否则可能会让程序陷入死循环
 * @date: 2019-08-15 7:42 PM
 * @version: 1.0
 * @website: https://yubuntu0109.github.io/
 */
@Slf4j
@Controller
@RequestMapping("/error")
@EnableConfigurationProperties({ServerProperties.class})
public class ErrorPagesController implements ErrorController {

    private ErrorAttributes errorAttributes;

    @Autowired
    private ServerProperties serverProperties;

    /**
     * @description: 初始化ExceptionController
     * @param: errorAttributes
     * @date: 2019-08-15 8:10 PM
     * @return:
     */
    @Autowired
    public ErrorPagesController(ErrorAttributes errorAttributes) {
        Assert.notNull(errorAttributes, "ErrorAttributes must not be null");
        this.errorAttributes = errorAttributes;
    }

    /**
     * @description: 404:指网页或文件未找到错误
     * @param: request
     * @param: response
     * @param: webRequest
     * @date: 2019-08-15 8:10 PM
     * @return: org.springframework.web.servlet.ModelAndView
     */
    @RequestMapping("/404")
    public ModelAndView errorHtml404(HttpServletRequest request, HttpServletResponse response, WebRequest webRequest) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        Map<String, Object> model = getErrorAttributes(webRequest, isIncludeStackTrace(request, MediaType.TEXT_HTML));

        return new ModelAndView("error/404", model);
    }

    /**
     * @description: 403:资源不可用错误
     * @param: request
     * @param: response
     * @param: webRequest
     * @date: 2019-08-15 8:11 PM
     * @return: org.springframework.web.servlet.ModelAndView
     */
    @RequestMapping("/403")
    public ModelAndView errorHtml403(HttpServletRequest request, HttpServletResponse response, WebRequest webRequest) {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        // 404拦截规则,如果是静态文件发生的404则不记录到DB
        Map<String, Object> model = getErrorAttributes(webRequest, isIncludeStackTrace(request, MediaType.TEXT_HTML));
        if (!String.valueOf(model.get("path")).contains(".")) {
            model.put("status", HttpStatus.FORBIDDEN.value());
        }
        return new ModelAndView("error/403", model);
    }

    /**
     * @description: 400:访问的页面域名不存在或者请求错误
     * @param: request
     * @param: response
     * @param: webRequest
     * @date: 2019-08-15 8:11 PM
     * @return: org.springframework.web.servlet.ModelAndView
     */
    @RequestMapping("/400")
    public ModelAndView errorHtml400(HttpServletRequest request, HttpServletResponse response, WebRequest webRequest) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        Map<String, Object> model = getErrorAttributes(webRequest, isIncludeStackTrace(request, MediaType.TEXT_HTML));
        return new ModelAndView("error/400", model);
    }

    /**
     * @description: 401:未经授权错误
     * @param: request
     * @param: response
     * @param: webRequest
     * @date: 2019-08-15 8:12 PM
     * @return: org.springframework.web.servlet.ModelAndView
     */
    @RequestMapping("/401")
    public ModelAndView errorHtml401(HttpServletRequest request, HttpServletResponse response, WebRequest webRequest) {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        Map<String, Object> model = getErrorAttributes(webRequest, isIncludeStackTrace(request, MediaType.TEXT_HTML));
        return new ModelAndView("error/401", model);
    }

    /**
     * @description: 500:服务器端发生异常
     * @param: request
     * @param: response
     * @param: webRequest
     * @date: 2019-08-15 8:12 PM
     * @return: org.springframework.web.servlet.ModelAndView
     */
    @RequestMapping("/500")
    public ModelAndView errorHtml500(HttpServletRequest request, HttpServletResponse response, WebRequest webRequest) {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        Map<String, Object> model = getErrorAttributes(webRequest, isIncludeStackTrace(request, MediaType.TEXT_HTML));
        return new ModelAndView("error/500", model);
    }

    /**
     * Determine if the stacktrace attribute should be included.
     *
     * @param request  the source request
     * @param produces the media type produced (or {@code MediaType.ALL})
     * @return if the stacktrace attribute should be included
     */
    protected boolean isIncludeStackTrace(HttpServletRequest request, MediaType produces) {
        ErrorProperties.IncludeStacktrace include = this.serverProperties.getError().getIncludeStacktrace();
        if (include == ErrorProperties.IncludeStacktrace.ALWAYS) {
            return true;
        }
        return include == ErrorProperties.IncludeStacktrace.ON_TRACE_PARAM && getTraceParameter(request);
    }


    /**
     * @description: 获取错误信息
     * @param: webRequest
     * @param: includeStackTrace
     * @date: 2019-08-15 8:13 PM
     * @return: java.util.Map<java.lang.String, java.lang.Object>
     */
    private Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        return this.errorAttributes.getErrorAttributes(webRequest, includeStackTrace);
    }

    /**
     * @description: 判断是否包含trace
     * @param: request
     * @date: 2019-08-15 8:13 PM
     * @return: boolean
     */
    private boolean getTraceParameter(HttpServletRequest request) {
        String parameter = request.getParameter("trace");
        return parameter != null && !"false".equalsIgnoreCase(parameter);
    }

    /**
     * @description: (never used)获取错误编码
     * @param: request
     * @date: 2019-08-15 8:14 PM
     * @return: org.springframework.http.HttpStatus
     */
    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request
                .getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        } catch (Exception ex) {
            log.error("获取当前HttpStatus发生异常", ex);
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    /**
     * @description: 实现错误路径, 暂时无用
     * @param:
     * @date: 2019-08-15 8:14 PM
     * @return: java.lang.String
     */
    @Override
    public String getErrorPath() {
        return "";
    }
}
