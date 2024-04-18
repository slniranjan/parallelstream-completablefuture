package com.learnjava.parallelstreams;

import static com.learnjava.util.LoggerUtil.log;

import java.util.List;
import java.util.stream.Collectors;

import com.learnjava.util.DataSet;

/**
 * collect is the better way here because it is do the operation in immutable fashion.
 * reduce operation do the mutable fashion. so memory optimization and efficiency footprint
 * collect method perform well
 */
public class CollectVsReduce {

	public static String collect() {
		List<String> namesList = DataSet.namesList();
		String result = namesList.parallelStream().collect(Collectors.joining());
		return result;
	}

	public static String reduce() {
		List<String> namesList = DataSet.namesList();
		String result = namesList.parallelStream().reduce("", (s1,s2) -> s1 + s2);
		return result;
	}

	public static void main(String[] args) {
		log("collect : " + collect());
		log("reduce : " + reduce());
	}
}
