package com.learnjava.parallelstreams;

import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.stopWatch;
import static com.learnjava.util.CommonUtil.stopWatchReset;
import static com.learnjava.util.CommonUtil.timeTaken;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.learnjava.util.DataSet;

class ParallelStreamsExampleTest {

	@Test
	void stringsTransform() {
		//given
		List<String> namesList = DataSet.namesList();

		//when
		startTimer();
		ParallelStreamsExample pse = new ParallelStreamsExample();
		List<String> transformList = pse.stringsTransform(namesList);
		timeTaken(); //can validate time taken to transform by looking console output
		//then
		assertEquals(4, transformList.size());
		transformList.forEach(name->{
			assertTrue(name.contains("-"));
		});
	}

	@ParameterizedTest
	@ValueSource(booleans = {false, true})
	void stringsTransform_1(boolean isParallel) {
		//given
		List<String> namesList = DataSet.namesList();

		//when
		startTimer();
		ParallelStreamsExample pse = new ParallelStreamsExample();
		List<String> transformList = pse.stringsTransform_1(namesList, isParallel);
		timeTaken(); //can validate time taken to transform by looking console output
		stopWatchReset();
		//then
		assertEquals(4, transformList.size());
		transformList.forEach(name->{
			assertTrue(name.contains("-"));
		});
	}
}
