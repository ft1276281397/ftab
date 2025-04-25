package com.thread;

/**
 * 线程执行方法（做一些项目启动后一直要执行的操作，比如根据时间自动更改订单状态，比如订单签收30天自动收货功能，比如根据时间来更改状态）
 */
public class MyThreadMethod extends Thread {

    /**
     * 重写run方法，定义线程执行的具体逻辑
     */
    public void run() {
        // 线程未中断时执行循环
        while (!this.isInterrupted()) {
            try {
                // 线程休眠5000毫秒（5秒）
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // 捕获中断异常并打印堆栈跟踪
                e.printStackTrace();
            }

            // ------------------ 开始执行 ---------------------------
            // 示例代码：打印当前时间戳
            // System.out.println("线程执行中:" + System.currentTimeMillis());
        }
    }
}
