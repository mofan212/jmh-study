package indi.mofan.indi.mofan.service;

import org.springframework.stereotype.Service;

/**
 * @author mofan
 * @date 2023/12/31 17:46
 */
@Service
public class CallService {

    public void call() {
        for (int i = 0; i < 100; i++) {
            // do something
            double d = Math.sqrt(100);
        }
    }
}
