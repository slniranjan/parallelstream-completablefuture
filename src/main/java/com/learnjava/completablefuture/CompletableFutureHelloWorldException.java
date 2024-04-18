package com.learnjava.completablefuture;

import static com.learnjava.util.CommonUtil.delay;
import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;
import static com.learnjava.util.LoggerUtil.log;

import java.util.concurrent.CompletableFuture;

import com.learnjava.service.HelloWorldService;

public class CompletableFutureHelloWorldException {
	HelloWorldService hws = new HelloWorldService();

	public CompletableFutureHelloWorldException(HelloWorldService hws) {
		this.hws = hws;
	}

	public String helloworld_3_async_call_handle() {
		startTimer();

		CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> hws.hello());
		CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> hws.world());
		CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
			delay(1000);
			return "Hi CompletableFuture!";
		});

		String hw = hello
				.handle((res, e)->{
					if(e != null){
						log("Exception is " + e);
						return "";
					}else {
						return res;
					}

				})
				.thenCombine(world, (h, w)->h+w)
				.handle((res, e)->{
					if(e != null){
						log("Exception after world is : " + e.getMessage());
						return "";
					}else {
						return res;
					}
				})
				.thenCombine(hiCompletableFuture, (previous, current)-> previous+current)
				.thenApply(String::toUpperCase)
				.join();

		timeTaken();
		return hw;
	}

	public String helloworld_3_async_call_exceptionally() {
		startTimer();

		CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> hws.hello());
		CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> hws.world());
		CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
			delay(1000);
			return "Hi CompletableFuture!";
		});

		String hw = hello
				.exceptionally(e->{
						log("Exception is " + e);
						return "";
				})
				.thenCombine(world, (h, w)->h+w)
				.exceptionally(e->{
						log("Exception after world is : " + e.getMessage());
						return "";
				})
				.thenCombine(hiCompletableFuture, (previous, current)-> previous+current)
				.thenApply(String::toUpperCase)
				.join();

		timeTaken();
		return hw;
	}

	public String helloworld_3_async_call_whenHandle() {
		startTimer();

		CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> hws.hello());
		CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> hws.world());
		CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
			delay(1000);
			return "Hi CompletableFuture!";
		});

		String hw = hello
				.whenComplete((res, e)->{
					log("res is :" + res);
					if(e != null){
						log("Exception is " + e);
					}
				})
				.thenCombine(world, (h, w)->h+w)
				.whenComplete((res, e)->{
					log("res is :" + res);
					if(e != null){
						log("Exception after world is : " + e.getMessage());
					}
				})
				.exceptionally(e->{
					log("Exception after thenCombine is : " + e.getMessage());
					return "";
				})
				.thenCombine(hiCompletableFuture, (previous, current)-> previous+current)
				.thenApply(String::toUpperCase)
				.join();

		timeTaken();
		return hw;
	}

}
