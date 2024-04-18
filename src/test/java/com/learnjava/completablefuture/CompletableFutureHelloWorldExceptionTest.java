package com.learnjava.completablefuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.learnjava.service.HelloWorldService;

@ExtendWith(MockitoExtension.class)
class CompletableFutureHelloWorldExceptionTest {

	@Mock
	HelloWorldService helloWorldService = mock(HelloWorldService.class);

	@InjectMocks
	CompletableFutureHelloWorldException hwcfe;

	@Test
	void helloworld_3_async_call_handle() {
		//given
		when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception occured..."));
		when(helloWorldService.world()).thenCallRealMethod();

		//when
		String result = hwcfe.helloworld_3_async_call_handle();
		//then
		assertEquals(" WORLD!HI COMPLETABLEFUTURE!", result);
	}

	@Test
	void helloworld_3_async_call_handle_2() {
		//given
		when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception occured"));
		when(helloWorldService.world()).thenThrow(new RuntimeException("Exception occured"));

		//when
		String result = hwcfe.helloworld_3_async_call_handle();
		//then
		assertEquals("HI COMPLETABLEFUTURE!", result);
	}

	@Test
	void helloworld_3_async_call_handle_3() {
		//given
		when(helloWorldService.hello()).thenCallRealMethod();
		when(helloWorldService.world()).thenCallRealMethod();

		//when
		String result = hwcfe.helloworld_3_async_call_handle();
		//then
		assertEquals("HELLO WORLD!HI COMPLETABLEFUTURE!", result);
	}

	@Test
	void helloworld_3_async_call_exceptionally() {
		//given
		when(helloWorldService.hello()).thenCallRealMethod();
		when(helloWorldService.world()).thenCallRealMethod();

		//when
		String result = hwcfe.helloworld_3_async_call_exceptionally();
		//then
		assertEquals("HELLO WORLD!HI COMPLETABLEFUTURE!", result);
	}

	@Test
	void helloworld_3_async_call_exceptionally_2() {
		//given
		when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception occured"));
		when(helloWorldService.world()).thenThrow(new RuntimeException("Exception occured"));

		//when
		String result = hwcfe.helloworld_3_async_call_exceptionally();
		//then
		assertEquals("HI COMPLETABLEFUTURE!", result);
	}

	@Test
	void helloworld_3_async_call_whenHandle() {
		//given
		when(helloWorldService.hello()).thenCallRealMethod();
		when(helloWorldService.world()).thenCallRealMethod();

		//when
		String result = hwcfe.helloworld_3_async_call_whenHandle();
		//then
		assertEquals("HELLO WORLD!HI COMPLETABLEFUTURE!", result);
	}

	@Test
	void helloworld_3_async_call_whenHandle_2() {
		//given
		when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception occured"));
		when(helloWorldService.world()).thenThrow(new RuntimeException("Exception occured"));

		//when
		String result = hwcfe.helloworld_3_async_call_whenHandle();
		//then
		assertEquals("HI COMPLETABLEFUTURE!", result);
	}
}
