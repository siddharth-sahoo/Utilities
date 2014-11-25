package com.awesome.pro.utilities;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;

import com.google.gson.JsonObject;

/**
 * Provides static methods to find all possible combinations
 *  of keys and their possible values.
 * @author Sankeerth.reddy
 */
public class CombinationGenerator {

	public static void main(String[] args) {
		Set<String> set = new HashSet<>();
		set.add("qwe");
		set.add("asd");
		set.add("zxc");
		List<List<String>> subsets = getSubsets(set);
		for (int i = 0; i < subsets.size(); i ++) {
			System.out.println(subsets.get(i));
		}
	}

	/**
	 * @param parameters Map of request keys and set of possible values.
	 * @return Set of all request JSONs encompassing all possible
	 * combinations of all sizes.
	 */
	public static Set<JsonObject> getAllParameterStrings(
			final Map<String, Set<String>> parameters) {
		Set<Set<String>> keyCombinations =
				getKeyCombinations(parameters.keySet());
		Set<Map<String, String>> combinations =
				getCombinationSet(parameters, keyCombinations);

		Set<JsonObject> queries = new HashSet<>();
		Iterator<Map<String, String>> iter = combinations.iterator();
		while (iter.hasNext()) {
			Map<String, String> currMap = iter.next();
			JsonObject json = convertToJSON(currMap);
			queries.add(json);
		}

		return queries;
	}

	/**
	 * @param parameters Map of request keys and set of possible values.
	 * @param combination Set of request strings for which combinations
	 * of values will be returned.
	 * @return Set of all request JSONs encompassing all possible
	 * combinations of values belonging to the specified keys.
	 */
	public static Set<JsonObject> getParameterStringsByCombination(
			final Map<String, Set<String>> parameters,
			final Set<String> combination) {
		Set<Set<String>> keyCombinations = new HashSet<>(
				Arrays.asList(combination));
		Set<Map<String, String>> combinations =
				getCombinationSet(parameters, keyCombinations);

		Set<JsonObject> queries = new HashSet<>();
		Iterator<Map<String, String>> iter = combinations.iterator();
		while (iter.hasNext()) {
			Map<String, String> currMap = iter.next();
			JsonObject json = convertToJSON(currMap);
			queries.add(json);
		}

		return queries;
	}

	/**
	 * @param parameters Map of request keys to possible values.
	 * @return Set of all request JSONs having all the keys and
	 * all possible combinations of their values.
	 */
	public static Set<JsonObject> getFullLengthParameterStrings(
			final Map<String, Set<String>> parameters) {
		Set<Set<String>> keyCombinations = new HashSet<>(
				Arrays.asList(parameters.keySet()));
		Set<Map<String, String>> combinations =
				getCombinationSet(parameters, keyCombinations);

		Set<JsonObject> queries = new HashSet<>();
		Iterator<Map<String, String>> iter = combinations.iterator();
		while (iter.hasNext()) {
			Map<String, String> currMap = iter.next();
			JsonObject json = convertToJSON(currMap);
			queries.add(json);
		}

		return queries;
	}

	/**
	 * @param parameters Map of request keys to possible values.
	 * @return Set of combinations. Each combination is a map of name of
	 * variable to its value.
	 */
	public static Set<Map<String, String>> getFullLengthCombinations(
			final Map<String, Set<String>> parameters) {
		Set<Set<String>> keyCombinations = new HashSet<>(Arrays.asList(
				parameters.keySet()));
		return getCombinationSet(parameters, keyCombinations);
	}

	/**
	 * @param superSet Super set of all values from which subsets
	 * will be created.
	 * @return List of subsets. The subsets will include an empty
	 * and the super set itself.
	 */
	public static <T> List<List<T>> getSubsets(final Set<T> superSet) {
		final ICombinatoricsVector<T> initialSet = Factory.
				<T>createVector(superSet);
		final Generator<T> generator = Factory.
				createSubSetGenerator(initialSet);
		final List<ICombinatoricsVector<T>> combinations =
				generator.generateAllObjects();
		final int size = combinations.size();
		final List<List<T>> subsets = new LinkedList<>();
		
		for (int i = 0; i < size; i ++) {
			subsets.add(combinations.get(i).getVector());
		}

		return subsets;
	}

	/**
	 * @param parameters Map of request keys and their possible values.
	 * @param keyCombinations Set of key combinations for which the
	 * combinations of values will be formed.
	 * @return Set of all possible combinations.
	 */
	private static Set<Map<String, String>> getCombinationSet(
			final Map<String, Set<String>> parameters,
			final Set<Set<String>> keyCombinations) {
		Set<Map<String, String>> allCombinations = new HashSet<>();

		Iterator<Set<String>> iter1 = keyCombinations.iterator();
		while (iter1.hasNext()) {
			Set<String> combination = iter1.next();
			allCombinations.addAll(
					getFixedCombinationSet(parameters,
							combination)
						);
		}

		return allCombinations;
	}

	/**
	 * @param keys Set of keys.
	 * @return All possible combinations of input keys in all sizes.
	 */
	public static Set<Set<String>> getKeyCombinations(
			final Set<String> keys) {
		Set<Set<String>> keyCombinations = new HashSet<>();
		ICombinatoricsVector<String> initialVector =
				Factory.createVector(keys);

		for (int i = 1; i <= keys.size(); i++) {
			Generator<String> generator = Factory
					.createSimpleCombinationGenerator(
							initialVector, i);
			for (ICombinatoricsVector<String> combination
					: generator) {
				keyCombinations.add(new HashSet<>(
						combination.getVector()));
			}
		}

		return keyCombinations;
	}

	/**
	 * @param parameters Map of keys and their possible values.
	 * @param combination Combination set for which the
	 * combinations of values will be formed.
	 * @return Set of all combinations of values for the
	 * input key combination.
	 */
	private static Set<Map<String, String>> getFixedCombinationSet(
			final Map<String, Set<String>> parameters,
			final Set<String> combination) {
		Set<Map<String, String>> allCombinations = new HashSet<>();

		Set<Map<String, String>> currCombinations = new HashSet<>();
		Iterator<String> iter2 = combination.iterator();
		while (iter2.hasNext()) {
			String key = iter2.next();
			currCombinations = combineMaps(key,
					parameters.get(key), currCombinations);
		}

		allCombinations.addAll(currCombinations);
		return allCombinations;
	}

	/**
	 * @param key Key to be added.
	 * @param values Possible values of the specified key.
	 * @param combination Incremental map of all the keys and values.
	 * @return Updated map with the new key and its values included
	 * and combinations.
	 */
	private static Set<Map<String, String>> combineMaps(
			final String key, final Set<String> values,
			final Set<Map<String, String>> combination) {

		if (combination.size() == 0) {
			Iterator<String> iter = values.iterator();
			while (iter.hasNext()) {
				String currValue = iter.next();
				Map<String, String> map1 = new HashMap<>();
				map1.put(key, currValue);
				combination.add(map1);
			}
			return combination;
		}

		Set<Map<String, String>> tempSet = new HashSet<>();
		Iterator<Map<String, String>> iter = combination.iterator();
		while (iter.hasNext()) {
			Map<String, String> currMap = iter.next();

			Iterator<String> iter2 = values.iterator();
			while (iter2.hasNext()) {
				String currValue = iter2.next();
				Map<String, String> map1 = new HashMap<>();
				map1.putAll(currMap);
				map1.put(key, currValue);
				tempSet.add(map1);
			}
		}

		return tempSet;
	}

	/**
	 * @param currMap Input map to be converted.
	 * @return JSON object equivalent of the input map.
	 */
	private static JsonObject convertToJSON(
			final Map<String, String> map) {
		final JsonObject obj = new JsonObject();
		final Iterator<Entry<String, String>> iter = map.entrySet().iterator();
		
		while (iter.hasNext()) {
			final Entry<String, String> entry = iter.next();
			obj.addProperty(entry.getKey(), entry.getValue());
		}
		return obj;
	}
}
