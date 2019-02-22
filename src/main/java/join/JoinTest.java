package join;

public class JoinTest {
    public static void main(String[] args) {
        //创建一个线程
        Thread h = new Thread() {
            @Override
            public void run() {
                int loop = 0;
              while(true){
                  System.out.println("Thread "+Thread.currentThread().getName()+" is running~");
                  loop++;
                  if(loop==20){
                      break;
                  }
                  try {
                      Thread.sleep(500);
                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  }
              }
            }
        };

        h.start();


        try {
            h.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Main thread end~");
    }
    /**
     有join方法调用的：
     Thread Thread-0 is running~
     Thread Thread-0 is running~
     Thread Thread-0 is running~
     Thread Thread-0 is running~
     Thread Thread-0 is running~
     Thread Thread-0 is running~
     Thread Thread-0 is running~
     Thread Thread-0 is running~
     Thread Thread-0 is running~
     Thread Thread-0 is running~
     Thread Thread-0 is running~
     Thread Thread-0 is running~
     Thread Thread-0 is running~
     Thread Thread-0 is running~
     Thread Thread-0 is running~
     Thread Thread-0 is running~
     Thread Thread-0 is running~
     Thread Thread-0 is running~
     Thread Thread-0 is running~
     Thread Thread-0 is running~
     Main thread end~
     */
    /**
     没有join方法调用的：
     Main thread end~
     Thread Thread-0 is running~
     Thread Thread-0 is running~
     Thread Thread-0 is running~
     Thread Thread-0 is running~
     Thread Thread-0 is running~
     Thread Thread-0 is running~
     Thread Thread-0 is running~
     Thread Thread-0 is running~
     Thread Thread-0 is running~
     Thread Thread-0 is running~
     Thread Thread-0 is running~
     Thread Thread-0 is running~
     Thread Thread-0 is running~
     Thread Thread-0 is running~
     Thread Thread-0 is running~
     Thread Thread-0 is running~
     Thread Thread-0 is running~
     Thread Thread-0 is running~
     Thread Thread-0 is running~
     Thread Thread-0 is running~
     */
}
