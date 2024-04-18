package com.learnjava.parallelstreams;

import static com.learnjava.util.CommonUtil.delay;
import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;
import static com.learnjava.util.LoggerUtil.log;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.learnjava.util.DataSet;

public class ParallelStreamsExample {
	public List<String> stringsTransform(List<String> nameList){
		return nameList
//				.stream()
				.parallelStream()
				.map(this::addNameLengthTransform)
				.collect(Collectors.toList());

	}

	/**
	 * use parallel or sequentioal stream dynamically
	 * @param nameList
	 * @param isParallel
	 * @return
	 */
	public List<String> stringsTransform_1(List<String> nameList, boolean isParallel){

		Stream<String> namesStream = nameList.stream();

		if(isParallel)
			namesStream.parallel();

		return namesStream
				.map(this::addNameLengthTransform)
				.collect(Collectors.toList());

	}

	public static void main(String[] args) {
		List<String> namesList = DataSet.namesList();

		ParallelStreamsExample pse = new ParallelStreamsExample();
		startTimer();
		List<String> resultList = pse.stringsTransform(namesList);
		log("resultList : " + resultList);
		timeTaken();
	}

	private String addNameLengthTransform(String name){
		delay(500);
		return name.length() + " - " + name;
	}
}
