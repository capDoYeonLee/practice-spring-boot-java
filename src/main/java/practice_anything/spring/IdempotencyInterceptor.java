package practice_anything.spring;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class IdempotencyInterceptor implements HandlerInterceptor {

    Map<AtomicInteger, String> ketStore = new HashMap<AtomicInteger, String>();
    private final AtomicInteger keyIndex = new AtomicInteger(0);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        if ("POST".equals(request.getMethod())) {
            String idempotencyKey = request.getHeader("Idempotency-Key");

            if (idempotencyKey != null) {
                if (ketStore.containsValue(idempotencyKey)) {
                    return false;
                } else {
                    ketStore.put(keyIndex, idempotencyKey);
                    keyIndex.incrementAndGet();
                }
            }
        }
        return true;
    }
}
