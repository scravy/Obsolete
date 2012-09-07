package net.abusingjava;


@Author("Julian Fleischer")
public class MemoryInfo extends Message {

	private static final long serialVersionUID = -2259029201036841429L;
	private final long $total;
	private final long $free;
	private final long $max;
	
	public MemoryInfo() {
		$total = Runtime.getRuntime().totalMemory();
		$free = Runtime.getRuntime().freeMemory();
		$max = Runtime.getRuntime().maxMemory();
	}
	
	public MemoryInfo(final Runtime $runtime) {
		$total = $runtime.totalMemory();
		$free = $runtime.freeMemory();
		$max = $runtime.maxMemory();
	}
	
	public long getTotal() {
		return $total;
	}
	
	public long getUsed() {
		return $total - $free;
	}
	
	public long getFree() {
		return $free;
	}
	
	public long getMax() {
		return $max;
	}
	
	@Override
	public String toString() {
		return toString(1, "Bytes");
	}
	
	public String toString(final int $factor, final String $unit) {
		return "Memory (total, used, free): "
				+ ($total / $factor) + " " + $unit + ", "
				+ (($total - $free) / $factor) + " " + $unit + ", "
				+ ($free / $factor) + " " + $unit;
	}
	
}
