
public class Testes {

	public static void main(String[] args) {
		
		
		String str = "<p style='text-indent: 2em; margin-bottom: 15px;' ><p><videocontrols width=\"420\" height=\"280\" src=\"/bwcx//ueditor/jsp/upload/video/20190805/1564989964962031393.flv\" data-setup=\"{}\"></videocontrols></p></p>";
		
		
		str = str.replace("videocontrols", "video controls");
		System.out.println(str);
	}

}
