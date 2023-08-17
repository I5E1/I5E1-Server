package fc5.i5e1server.common.util;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;

public interface ErrorMessageConverter {

    static String[] convert(BindingResult bindingResult) {

        return bindingResult.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toArray(String[]::new);
    }
}