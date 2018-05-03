package exception;

import java.util.concurrent.Callable;

public class ExceptionCallable implements Callable {
    @Override
    public String call() throws Exception {
        System.out.println("enter callable~");
        //demo exception
        System.out.println(3/0);
        return "ok";
    }
}
