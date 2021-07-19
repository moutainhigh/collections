package com.gwssi.rodimus.pic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

public class BarCodeUtil {

	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;

	/**
	 * 
	 * @param content
	 *            二维码内容
	 * @param path
	 *            生成图片路径
	 * @param name
	 *            生成图片名
	 * @param length
	 *            长度
	 * @param width
	 *            宽度
	 */
	@SuppressWarnings("static-access")
	public static void genbarCode(String content, String path,
			int length, int width) {
		try {

			MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
			Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			hints.put(EncodeHintType.MARGIN, 0);
			BitMatrix bitMatrix = multiFormatWriter.encode(content,
					BarcodeFormat.QR_CODE, length, width, hints);
			File file = new File(path);
			BarCodeUtil zx = new BarCodeUtil();
			zx.writeToFile(bitMatrix, "jpg", file);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return image;
	}

	protected static void writeToFile(BitMatrix matrix, String format, File file)
			throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, file)) {
			throw new IOException("Could not write an image of format "
					+ format + " to " + file);
		}
	}

	protected static void writeToStream(BitMatrix matrix, String format,
			OutputStream stream) throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, stream)) {
			throw new IOException("Could not write an image of format "
					+ format);
		}
	}

	// public static void main(String[] args) {
	//
	// String content = "山西陕西";
	// String path = "E:/";
	// genbarCode(content,path,"Bddar_Code_Sampl22e.jpg",200,200);
	// genbarCode(content,path,"aabbcc.png",200,200);
	//
	// }

}
