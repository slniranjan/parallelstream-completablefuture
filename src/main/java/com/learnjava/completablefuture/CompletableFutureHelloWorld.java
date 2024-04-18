package com.learnjava.completablefuture;

import static com.learnjava.util.CommonUtil.delay;
import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;
import static com.learnjava.util.LoggerUtil.log;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.learnjava.service.HelloWorldService;

public class CompletableFutureHelloWorld {
	HelloWorldService hws = new HelloWorldService();

	public CompletableFutureHelloWorld(HelloWorldService hws) {
		this.hws = hws;
	}

	public CompletableFuture<String> helloWorld() {
		return CompletableFuture
				.supplyAsync(hws::helloWorld)
				.thenApply(String::toUpperCase);
	}

	public String helloWorld_approach1() {
		String hello = hws.hello();
		String world = hws.world();
		return hello + world;
	}

	public String helloworld_multiple_async_call() {
		startTimer();

		CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> hws.hello());
		CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> hws.world());

		String hw = hello
				.thenCombine(world, (h, w)->h+w)
				.thenApply(String::toUpperCase)
				.join();

		timeTaken();
		return hw;
	}

	public String helloworld_3_async_call() {
		startTimer();

		CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> hws.hello());
		CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> hws.world());
		CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
			delay(1000);
			return "Hi CompletableFuture!";
		});

		String hw = hello
				.thenCombine(world, (h, w)->h+w)
				.thenCombine(hiCompletableFuture, (previous, current)-> previous+current)
				.thenApply(String::toUpperCase)
				.join();

		timeTaken();
		return hw;
	}

	public String helloworld_3_async_call_custom_threadpool() {
		startTimer();

		ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

		CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> hws.hello(), executorService);
		CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> hws.world(), executorService);
		CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
			delay(1000);
			return "Hi CompletableFuture!";
		}, executorService);

		String hw = hello
				.thenCombine(world, (h, w)->{
					log("thenCombine h/w");
					return h+w;
				})
				.thenCombine(hiCompletableFuture, (previous, current)-> {
					log("thenCombine previous/current");
					return previous+current;
				})
				.thenApply(s -> {
					log("thenApply");
					return s.toUpperCase();
				})
				.join();

		timeTaken();
		return hw;
	}

	public String helloworld_3_async_call_log_async() {
		startTimer();

		ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

		CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> hws.hello(), executorService);
		CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> hws.world(), executorService);
		CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
			delay(1000);
			return "Hi CompletableFuture!";
		}, executorService);

		String hw = hello
				.thenCombineAsync(world, (h, w)->{
					log("thenCombine h/w");
					return h+w;
				})
				.thenCombineAsync(hiCompletableFuture, (previous, current)-> {
					log("thenCombine previous/current");
					return previous+current;
				})
				.thenApplyAsync(s -> {
					log("thenApply");
					return s.toUpperCase();
				})
				.join();

		timeTaken();
		return hw;
	}

	public String helloworld_3_async_call_custom_threadpool_async() {
		startTimer();

		ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

		CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> hws.hello(), executorService);
		CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> hws.world(), executorService);
		CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
			delay(1000);
			return "Hi CompletableFuture!";
		}, executorService);

		String hw = hello
				.thenCombineAsync(world, (h, w)->{
					log("thenCombine h/w");
					return h+w;
				}, executorService)
				.thenCombineAsync(hiCompletableFuture, (previous, current)-> {
					log("thenCombine previous/current");
					return previous+current;
				}, executorService)
				.thenApplyAsync(s -> {
					log("thenApply");
					return s.toUpperCase();
				}, executorService)
				.join();

		timeTaken();
		return hw;
	}

	public CompletableFuture<String> helloWorld_thenCompose(){
		return CompletableFuture.supplyAsync(hws::hello)
				.thenCompose(previous->hws.worldFuture(previous)) //thenCompose waits until above stream finish
				.thenApply(String::toUpperCase);
//				join
	}

	public static void main(String[] args) {
//		HelloWorldService hws = new HelloWorldService();
//
//		CompletableFuture.supplyAsync(hws::helloWorld)
//				.thenApply(String::toUpperCase)
//				.thenAccept((result) -> {
//					log("Result is: " + result);
//				}).join(); //join wait to complete the above snippets
//		// below line execute immediately since reactive fashion.
//		log("Done!");
//		//		delay(2000);

	}
}
