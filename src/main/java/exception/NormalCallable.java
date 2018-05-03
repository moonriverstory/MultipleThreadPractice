package exception;

import java.util.concurrent.Callable;

public class NormalCallable implements Callable {
    @Override
    public String call() throws Exception {
        System.out.println("enter normal callable~");

        return "ok";
    }
}
