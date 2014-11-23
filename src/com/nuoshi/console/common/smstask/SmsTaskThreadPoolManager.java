package com.nuoshi.console.common.smstask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SmsTaskThreadPoolManager {

	public static SmsTaskThreadPoolManager INSTANCE = new SmsTaskThreadPoolManager();
	// 线程池维护线程的最少数量
	private final static int POOL_MIN_SIZE = 2 ;
	// 线程池维护线程的最大数量
	private final static int POOL_MAX_SIZE = 8;
	// 线程池维护线程所允许的空闲时间
	private final static int POOL_KEEP_ALIVE_TIME = 0;
	// 线程池所使用的缓冲队列大小
	private final static int POOL_WORK_QUEUE_SIZE = 10;
	// 任务缓冲队列
	//private Queue<PhotoTaskRunnable> msgQueue = new LinkedList<PhotoTaskRunnable>();

	private SmsTaskThreadPoolManager() {
	}

	/*private final RejectedExecutionHandler handler = new RejectedExecutionHandler() {
		public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
			//System.out.println("消息放入队列中重新等待执行");
			msgQueue.offer((PhotoTaskRunnable) r);
		}
	};*/

//	public final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(Globals.poolMinSize, Globals.poolMaxSize, POOL_KEEP_ALIVE_TIME, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(POOL_WORK_QUEUE_SIZE), this.handler);
	public final ExecutorService service = Executors.newFixedThreadPool(8);

	/*// 访问图片缓存的调度线程
	private final Runnable accessBufferThread = new Runnable() {
		// 查看是否有待定请求，如果有，则创建一个新的photoTask，并添加到线程池中
		public void run() {
			if (!msgQueue.isEmpty()) {
				PhotoTaskRunnable task = msgQueue.poll();
				exc.execute(task);
			}
		}

	};*/
	// 调度线程池
//	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	//private final ScheduledFuture taskHandler = scheduler.scheduleAtFixedRate(accessBufferThread, 0, 1, TimeUnit.SECONDS);

	public void addSmsTask(String phone,String content) {
		Runnable task = new SmsTaskRunnable(phone,content);
		this.service.execute(task);
	}
}
