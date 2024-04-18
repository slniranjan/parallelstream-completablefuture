package com.learnjava.parallelstreams;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import com.learnjava.util.DataSet;

/**
 * parallel processing slow down the execution for the LinkedList.
 * LinkedList take much time to split.
 */
class LinkedListSpliteratorExampleTest {

	LinkedListSpliteratorExample linkedListSpliteratorExample
			= new LinkedListSpliteratorExample();


	@RepeatedTest(5)
	void multiplyEachValue() {
		//given
		int size = 1000000;
		LinkedList<Integer> inputList = DataSet.generateIntegerLinkedList(size);


		//when
		List<Integer> resultList  = linkedListSpliteratorExample.multiplyEachValue(inputList, 2,false);


		//then
		assertEquals(size, resultList.size());
	}

	@RepeatedTest(5)
	void multiplyEachValue_parallel() {
		//given
		int size = 1000000;
		LinkedList<Integer> inputList = DataSet.generateIntegerLinkedList(size);


		//when
		List<Integer> resultList  = linkedListSpliteratorExample.multiplyEachValue(inputList, 2,true);


		//then
		assertEquals(size, resultList.size());
	}
}
