package com.wesley.autocomplete;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Utils {
    public static boolean isValid(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj instanceof String && obj.toString().trim().length() == 0) {
            return false;
        } else if (obj.getClass().isArray() && Array.getLength(obj) == 0) {
            return false;
        } else if (obj instanceof Collection && ((Collection) obj).isEmpty()) {
            return false;
        } else if (obj instanceof Map && ((Map) obj).isEmpty()) {
            return false;
        }
        return true;
    }    
    
}
