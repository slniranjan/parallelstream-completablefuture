package com.learnjava.completablefuture;

import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;
import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.Test;

import com.learnjava.service.HelloWorldService;

class CompletableFutureHelloWorldTest {

	HelloWorldService hws = new HelloWorldService();
	CompletableFutureHelloWorld chw = new CompletableFutureHelloWorld(hws);

	@Test
	void helloWorld() {
		
		//when
		CompletableFuture<String> completableFuture = chw.helloWorld();

		//then
		completableFuture
				.thenApply(s -> {
					assertEquals("HELLO WORLD", s);
					return null;
				})
				.join();
	}

	@Test
	void helloworld_multiple_async_call() {

		//when
		String helloWorld = chw.helloworld_multiple_async_call();

		//then
		assertEquals("HELLO WORLD!", helloWorld);
	}

	@Test
	void helloworld_3_async_call() {

		//when
		String helloWorld = chw.helloworld_3_async_call();

		//then
		assertEquals("HELLO WORLD!HI COMPLETABLEFUTURE!", helloWorld);
	}

	@Test
	void helloworld_3_async_call_custom_threadpool() {
		//then
		String s = chw.helloworld_3_async_call_custom_threadpool();
		//when
		assertEquals("HELLO WORLD!HI COMPLETABLEFUTURE!", s);

		//check thread name. it will be not common forkjoin threadpool
	}

	@Test
	void helloworld_3_async_call_custom_threadpool_async() {
		//then
		String s = chw.helloworld_3_async_call_custom_threadpool_async();
		//when
		assertEquals("HELLO WORLD!HI COMPLETABLEFUTURE!", s);

		//check thread name. it will be not common forkjoin threadpool
	}

	@Test
	void helloworld_3_async_call_log_async() {
		//then
		String s = chw.helloworld_3_async_call_log_async();
		//when
		assertEquals("HELLO WORLD!HI COMPLETABLEFUTURE!", s);

		//check thread name. it may be use different thread for pipeline execution.
		// if the code performance issue identified use custom thread pools together with async methods
	}

	@Test
	void helloWorld_thenCompose() {
		//given
		startTimer();
		//when
		CompletableFuture<String> completableFuture = chw.helloWorld_thenCompose();

		//then
		completableFuture
				.thenAccept(s -> {
					assertEquals("HELLO WORLD!", s);
				})
				.join();

		timeTaken();
	}
}
