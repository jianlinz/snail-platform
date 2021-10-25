package indi.jl.sp.log.filter;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

public class DispatcherServletErrorFilter extends Filter<ILoggingEvent> {

    @Override
    public FilterReply decide(ILoggingEvent event) {
        if (Level.ERROR.levelInt == event.getLevel().levelInt) {
            if (event.getLoggerName().startsWith("org.apache.catalina.core.ContainerBase.[Tomcat].")
                    && event.getThrowableProxy() != null
                    && event.getThrowableProxy().getClassName().startsWith("org.springframework.security"))
                return FilterReply.DENY;
        }
        return FilterReply.NEUTRAL;
    }
}
