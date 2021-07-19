package cn.gwssi.test.task;

public class TestWrite {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		test();
		test02();
	}
	private static void test02() {
		WriteTask write = new WriteTask();
		for(int i=0;i<4;i++){//四个线程
			new Thread(write).start();
		}
		
		OutputTask output = new OutputTask("abc.txt");
		new Thread(output).start();
		
	}

	/*private static void test() {
		WriteTask write = new WriteTask("abc.txt");
		for(int i=0;i<5;i++){
			new Thread(write).start();
		}
	}*/
}
