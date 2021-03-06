package com.github.dabasan.joglf.gl.input;

/**
 * Counts and flags used to store user input
 * 
 * @author Daba
 *
 */
public class CountsAndFlags {
	private final int element_num;

	private final int[] pressing_counts;
	private final int[] releasing_counts;
	private final boolean[] pressing_flags;

	public CountsAndFlags(int element_num) {
		this.element_num = element_num;

		pressing_counts = new int[element_num];
		releasing_counts = new int[element_num];
		pressing_flags = new boolean[element_num];

		for (int i = 0; i < element_num; i++) {
			pressing_counts[i] = 0;
			releasing_counts[i] = 0;
			pressing_flags[i] = false;
		}
	}

	/**
	 * Sets pressing counts to 0, releasing counts to 0 and pressing flags to
	 * false.
	 */
	public void Reset() {
		for (int i = 0; i < element_num; i++) {
			pressing_counts[i] = 0;
			releasing_counts[i] = 0;
			pressing_flags[i] = false;
		}
	}

	private boolean IsAvailableIndex(int index) {
		if (0 <= index && index < element_num) {
			return true;
		} else {
			return false;
		}
	}

	public void SetPressingFlag(int index, boolean flag) {
		if (this.IsAvailableIndex(index) == true) {
			pressing_flags[index] = flag;
		}
	}
	public int GetPressingCount(int index) {
		int ret = 0;

		if (this.IsAvailableIndex(index) == true) {
			ret = pressing_counts[index];
		}

		return ret;
	}
	public int GetReleasingCount(int index) {
		int ret = 0;

		if (this.IsAvailableIndex(index) == true) {
			ret = releasing_counts[index];
		}

		return ret;
	}

	public void UpdateCounts() {
		for (int i = 0; i < element_num; i++) {
			if (pressing_flags[i] == true) {
				pressing_counts[i]++;
				if (releasing_counts[i] != 0) {
					releasing_counts[i] = 0;
				}
			} else {
				releasing_counts[i]++;
				if (pressing_counts[i] != 0) {
					pressing_counts[i] = 0;
				}
			}
		}
	}
}
