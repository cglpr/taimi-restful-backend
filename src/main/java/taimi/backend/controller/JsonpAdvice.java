package taimi.backend.controller;

import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * Support for jsonp callback
 * -> http://srvhost/srv/path/a?callback=somemethod
 * <- somemethod([{data}])
 * 
 * @author vpotry
 *
 */
@ControllerAdvice
public class JsonpAdvice extends AbstractJsonpResponseBodyAdvice {
    public JsonpAdvice() {
        super("callback");
    }
} 