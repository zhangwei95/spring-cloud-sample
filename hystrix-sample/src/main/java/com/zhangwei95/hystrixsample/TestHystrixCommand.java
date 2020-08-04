package com.zhangwei95.hystrixsample;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @Description: HystrixCommand
 * @Author: zhangwei
 * @Date: 2020/8/4 09:10
 */
public class TestHystrixCommand extends HystrixCommand {

    private final String name;


    public TestHystrixCommand(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.name = name;
    }

    /**
     * 	如果继承的是HystrixObservableCommand，要重写Observable construct()，
     * 	具体业务逻辑
     */
    @Override
    protected Object run() throws Exception {

        int i = 1 / 0;
        return "正常调用 " + name;
    }


    @Override
    protected Object getFallback() {
        return "触发降级";
    }

    public static void main(String[] args) {
        /**
         * execute()：以同步阻塞方式执行run()。以demo为例，调用execute()后，
         * hystrix先创建一个新线程运行run()，
         * 	接着调用程序要在execute()调用处一直阻塞着，直到run()运行完成
         */
//		String result = (String)new TestHystrixCommand("hello").execute();


        /**
         * queue()：以异步非阻塞方式执行run()。以demo为例，
         * 	一调用queue()就直接返回一个Future对象，
         * 	同时hystrix创建一个新线程运行run()，
         * 	调用程序通过Future.get()拿到run()的返回结果，
         * 	而Future.get()是阻塞执行的
         */
        Future<String> futureResult = new TestHystrixCommand("hello").queue();
        String result = "";
        try {
            result = futureResult.get();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        System.out.println("程序结果："+result);
    }
}
