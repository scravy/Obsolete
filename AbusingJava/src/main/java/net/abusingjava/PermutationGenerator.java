package net.abusingjava;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


public class PermutationGenerator<T> implements Iterable<List<T>> {

	final List<T> $list;
	
	PermutationGenerator(final List<T> $list) {
		this.$list = $list;
	}
	
	public static <T> PermutationGenerator<T> create(final List<T> $list) {
		return new PermutationGenerator<T>($list);
	}
	
	public class PIterator implements Iterator<List<T>> {

		private final Iterator<T> $listIterator = $list.iterator();
		
		private Iterator<List<T>> $pIterator = new OneValueIterator<List<T>>(null);
		private T $current;
		
		@Override
		public boolean hasNext() {
			return $pIterator.hasNext() || $listIterator.hasNext();
		}

		@Override
		public List<T> next() {
			if (!$pIterator.hasNext()) {
				List<T> $sublist = new ArrayList<T>($list);
				$current = $listIterator.next();
				$sublist.remove($current);
				$pIterator = new PermutationGenerator<T>($sublist).iterator();
			}
			List<T> $new = new ArrayList<T>($list.size());
			$new.add($current);
			$new.addAll($pIterator.next());
			return $new;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}
	
	@Override
	public Iterator<List<T>> iterator() {
		if ($list.size() < 1) {
			return new OneValueIterator<List<T>>($list.size() == 0 ? new ArrayList<T>(0) : $list);
		}
		return new PIterator();
	}

	public static void main(final String... $args) {
		PermutationGenerator<Integer> $generator = PermutationGenerator.create(Arrays.asList(1, 2 , 3));
		for (List<Integer> $permutation : $generator) {
			System.out.println($permutation);
		}
	}
}
