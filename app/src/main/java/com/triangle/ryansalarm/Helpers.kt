package com.triangle.ryansalarm

import com.google.android.material.math.MathUtils


class Helpers
{
	companion object Static
	{
		/**
		 * Compares doubles for equivalence.
		 *
		 * @param A The first value
		 * @param B The second value
		 * @param Epsilon The two values are considered equal if there value difference is less then this
		 */
		fun doublesEquivalent(
			A: Double, B: Double,
			Epsilon: Double = MathUtils.DEFAULT_EPSILON.toDouble()
		): Boolean
		{
			return kotlin.math.abs(A - B) < Epsilon
		}
	}
}