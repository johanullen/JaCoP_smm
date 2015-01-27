package main;

import org.jacop.constraints.*;
import org.jacop.core.*;
import org.jacop.search.*;

public class SendMoreMoney {
	public static void main(String[] args) {
		long T1, T2, T;
		T1 = System.currentTimeMillis();
		send();
		T2 = System.currentTimeMillis();
		T = T2 - T1;
		System.out.println("\n\t*** Execution time = " + T + " ms");
	}

	static void send() {
		Store store = new Store();
		IntVar s = new IntVar(store, "S", 0, 9);
		IntVar e = new IntVar(store, "E", 0, 9);
		IntVar n = new IntVar(store, "N", 0, 9);
		IntVar d = new IntVar(store, "D", 0, 9);
		IntVar m = new IntVar(store, "M", 0, 9);
		IntVar o = new IntVar(store, "O", 0, 9);
		IntVar r = new IntVar(store, "R", 0, 9);
		IntVar y = new IntVar(store, "Y", 0, 9);
		IntVar digits[] = { s, e, n, d, m, o, r, y };
		store.impose(new Alldiff(digits));
		/**
		 * Constraint for equation SEND = 1000 * S + 100 * E + N * 10 + D * 1
		 * MORE = 1000 * M + 100 * O + R * 10 + E * 1 MONEY = 10000 * M + 1000 *
		 * O + 100 * N + E * 10 + Y * 1
		 */
		store.impose(new Linear(store, new IntVar[] { s, e, n, d, m, o, r, e,
				m, o, n, e, y }, new int[] { 1000, 100, 10, 1, 1000, 100, 10,
				1, -10000, -1000, -100, -10, -1 }, "==", 0));
		store.impose(new XneqC(s, 0));
		store.impose(new XneqC(m, 0));
		System.out.println("Number of variables: " + store.size()
				+ "\nNumber of constraints: " + store.numberConstraints());
		Search<IntVar> label = new DepthFirstSearch<IntVar>();
		SelectChoicePoint<IntVar> select = new SimpleSelect<IntVar>(digits,
				new SmallestDomain<IntVar>(), new IndomainMin<IntVar>());
		label.setSolutionListener(new PrintOutListener<IntVar>());
		label.getSolutionListener().searchAll(true);
		boolean Result = label.labeling(store, select);
		if (Result) {
			System.out.println("\n*** Yes");
			System.out.println("Solution : " + java.util.Arrays.asList(digits));
		} else
			System.out.println("\n*** No");
	}
}
// JaCoP finds the solution.
// Number of variables: 8
// Number of constraints: 4
// Solution : [S=9, E=5, N=6, D=7, M=1, O=0, R=8, Y=2]
// Depth First Search DFS0
// [S = 9, M = 1, O = 0, E = 5, D = 7, N = 6, R = 8, Y = 2]
// Solution : [S=9, E=5, N=6, D=7, M=1, O=0, R=8, Y=2]
// Nodes : 6
// Decisions : 3
// Wrong Decisions : 3
// Backtracks : 3
// Max Depth : 3
// *** Yes
// Solution : [S = 9, E = 5, N = 6, D = 7, M = 1, O = 0, R = 8, Y = 2]
// *** Execution time = 21 ms