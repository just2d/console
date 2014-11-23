package com.nuoshi.console.common.smstask;



public class ProccessorSmsTask {
	public static SmsTaskThreadPoolManager manager = SmsTaskThreadPoolManager.INSTANCE;

	public static void addTask(String phone,String content){
		manager.addSmsTask(phone,content);
	}
	/*public static void main(String[] args) {

		BufferedReader phones = null;
		FileReader fileReader = null;
		try {
			fileReader = new FileReader("d:/phone/phone.txt");
			phones = new BufferedReader(fileReader);
			String phone = phones.readLine();
			while (phone != null) {
				addTask(phone);
				phone = phones.readLine();
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fileReader.close();
				phones.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	}*/
}
