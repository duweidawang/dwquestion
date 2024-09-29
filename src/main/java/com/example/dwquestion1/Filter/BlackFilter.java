package com.example.dwquestion1.Filter;


import com.example.dwquestion1.util.BlackIpUtil;
import com.example.dwquestion1.util.NetUtils;
import lombok.extern.slf4j.Slf4j;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@WebFilter(urlPatterns = "/*")
public class BlackFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
            //获取ip地址
        String ipAddress = NetUtils.getIpAddress((HttpServletRequest) servletRequest);
        log.info("ip地址：{}",ipAddress);
        //判断是否在黑名单
        if(BlackIpUtil.isBlackIp(ipAddress)){
            //如果在返回提示信息
            servletResponse.setContentType("test/json;charset=UTF-8");
            servletResponse.getWriter().write("{\"黑名单ip 禁止访问\"}");
            return;
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
