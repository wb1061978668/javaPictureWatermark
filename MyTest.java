package com.wangb.cn.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

/**
 * Created by uchoice on 2016/11/25.
 */
public class MyTest {

	/**
	 * 图片添加水印
	 *
	 * @paramsrcImgPath需要添加水印的图片的路径
	 * @paramoutImgPath添加水印后图片输出路径
	 * @parammarkContentColor水印文字的颜色
	 * @paramfontSize文字大小
	 * @paramwaterMarkContent水印的文字,多排水印请使用"||"分割；如：湖北省武汉市xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx||春风网咖||2011-08-11 15:00:03
	 */
	public void waterPress(String srcImgPath, String outImgPath,
			Color markContentColor, int fontSize, String waterMarkContent) {
		try {
		String[] 	waterMarkContents=waterMarkContent.split("\\|\\|");
			// 读取原图片信息
			File srcImgFile = new File(srcImgPath);
			Image srcImg = ImageIO.read(srcImgFile);
			int srcImgWidth = srcImg.getWidth(null);
			int srcImgHeight = srcImg.getHeight(null);
			// 加水印
			BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bufImg.createGraphics();
			g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
			// Font font = new Font("Courier New", Font.PLAIN, 12);
			Font font = new Font("宋体", Font.PLAIN, fontSize);
			g.setColor(markContentColor);// 根据图片的背景设置水印颜色

			g.setFont(font);
			int t=0;
	for(int j=0;j<waterMarkContents.length;j++){
		waterMarkContent= waterMarkContents[j];
			
			int fontlen = getWatermarkLength(waterMarkContent, g);

			int line = fontlen / srcImgWidth;// 文字长度相对于图片宽度应该有多少行
            
			//int y = srcImgHeight - (line + 1) * fontSize;
//			int y = (line + 1) * fontSize;
			int y=(j+1)*fontSize+10+t;
			System.out.println("水印文字总长度:" + fontlen + ",图片宽度:" + srcImgWidth
					+ ",字符个数:" + waterMarkContent.length());

			// 文字叠加,自动换行叠加
			int tempX = 10;
			int tempY = y;
			int tempCharLen = 0;// 单字符长度
			int tempLineLen = 0;// 单行字符总长度临时计算
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < waterMarkContent.length(); i++) {
				char tempChar = waterMarkContent.charAt(i);
				tempCharLen = getCharLen(tempChar, g);

				tempLineLen += tempCharLen;

				if (tempLineLen >= srcImgWidth) {
					// 长度已经满一行,进行文字叠加
					g.drawString(sb.toString(), tempX, tempY);
t=t+fontSize;
					sb.delete(0, sb.length());// 清空内容,重新追加

					tempY += fontSize;

					tempLineLen = 0;
				}
				sb.append(tempChar);// 追加字符
			}

			g.drawString(sb.toString(), tempX, tempY);// 最后叠加余下的文字
	}
			g.dispose();

			// 输出图片
			FileOutputStream outImgStream = new FileOutputStream(outImgPath);
			ImageIO.write(bufImg, "jpg", outImgStream);
			outImgStream.flush();
			outImgStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getCharLen(char c, Graphics2D g) {
		return g.getFontMetrics(g.getFont()).charWidth(c);
	}

	/**
	 * 获取水印文字总长度
	 *
	 * @paramwaterMarkContent水印的文字
	 * @paramg
	 * @return水印文字总长度
	 */
	public int getWatermarkLength(String waterMarkContent, Graphics2D g) {
		return g.getFontMetrics(g.getFont()).charsWidth(
				waterMarkContent.toCharArray(), 0, waterMarkContent.length());
	}

	public static void main(String[] args) {
		// 原图位置, 输出图片位置, 水印文字颜色, 水印文字
		//String font = "qqqqqqssss||2222..... ooooddd水印 原图位置, 输出图片位置, 水印文字颜色, 水印文字 原图位置, 输出图片位置, ||水印文图位置, 输出图片位置, 水印文字颜色, 水印文字 原图位图位置, 输出图片位置, 水印文字颜色, 水印文字 原图位图位置, 输出图片位置, 水印文字颜色, 水印文字 原图位图位置, 输出图片位置, 水印文字颜色, 水印文字 原图位字颜色, 水印文字效果测水印效果整水印效果测水印效果整水印效果测水印效果整水印效果测水印效果整水印效果测水印效果整水印效果||测水印||效果整";
		String font ="湖北省武汉市xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx||春风网咖||2011-08-11 15:00:03";
		new MyTest().waterPress("D:\\fcga\\1.png",
				"D:\\fcga\\11.png", new Color(255, 255, 255,
						128), 30, font);
	}
}
