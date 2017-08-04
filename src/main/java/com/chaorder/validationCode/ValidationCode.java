package com.chaorder.validationCode;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONObject;
import org.patchca.background.SingleColorBackgroundFactory;
import org.patchca.color.ColorFactory;
import org.patchca.color.SingleColorFactory;
import org.patchca.filter.predefined.MarbleRippleFilterFactory;
import org.patchca.font.RandomFontFactory;
import org.patchca.service.Captcha;
import org.patchca.service.CaptchaService;
import org.patchca.service.ConfigurableCaptchaService;
import org.patchca.utils.encoder.EncoderHelper;
import org.patchca.word.RandomWordFactory;

/**
 * 验证码处理器工厂
 * 多彩、带干扰线的验证码；
 * @author iPan
 * @version 2014-2-19
 */
 
public class ValidationCode{
	private static String DEFAULT_CHARACTERS = "123456789ABCDEFGHIJKL"; // 自己设置！
	private static int DEFAULT_FONT_SIZE = 25;
	private static int DEFAULT_WORD_LENGTH = 4;
	private static int DEFAULT_WIDTH = 80;
	private static int DEFAULT_HEIGHT = 35;
	public static CaptchaService create(int fontSize, int wordLength, String characters, int width, int height) {
		ConfigurableCaptchaService service = null;
		// 字体大小设置
		RandomFontFactory ff = new RandomFontFactory();
		ff.setMinSize(fontSize);
		ff.setMaxSize(fontSize);
		// 生成的单词设置
		RandomWordFactory rwf = new RandomWordFactory();
		rwf.setCharacters(characters);
		rwf.setMinLength(wordLength);
		rwf.setMaxLength(wordLength);
		// 干扰线波纹
		MarbleRippleFilterFactory crff = new MarbleRippleFilterFactory();
		// 背景颜色
		SingleColorBackgroundFactory bgcf = new SingleColorBackgroundFactory(new Color(190, 190, 190));
		// 处理器
		service = new ExConfigurableCaptchaService();
		service.setFontFactory(ff);
		service.setWordFactory(rwf);
		service.setFilterFactory(crff);
		service.setBackgroundFactory(bgcf);
		// 生成图片大小（像素）
		service.setWidth(width);
		service.setHeight(height);
		return service;
	}
	public static CaptchaService create() {
		return create(DEFAULT_FONT_SIZE, DEFAULT_WORD_LENGTH, DEFAULT_CHARACTERS, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	// 随机变色处理
	static class ExConfigurableCaptchaService extends ConfigurableCaptchaService {
		private static final Random RANDOM = new Random();
		private List<SingleColorFactory> colorList = new ArrayList<SingleColorFactory>(); 
		public ExConfigurableCaptchaService() {
			//colorList.add(new SingleColorFactory(Color.blue));
			colorList.add(new SingleColorFactory(Color.black));
			//colorList.add(new SingleColorFactory(Color.red));
			//colorList.add(new SingleColorFactory(Color.pink));
			//colorList.add(new SingleColorFactory(Color.orange));
			//colorList.add(new SingleColorFactory(Color.green));
			//colorList.add(new SingleColorFactory(Color.magenta));
			//colorList.add(new SingleColorFactory(Color.cyan));
		}
		public ColorFactory nextColorFactory() {	
			int index = RANDOM.nextInt(colorList.size());
			return (ColorFactory) colorList.get(index);
		}
		@Override
		public Captcha getCaptcha() {
			ColorFactory cf = nextColorFactory();
			//MarbleRippleFilterFactory crff = (MarbleRippleFilterFactory) this.getFilterFactory();
			this.setColorFactory(cf);
			return super.getCaptcha();
		}
	}
	public JSONObject createValidationCode () throws IOException {
		
		CaptchaService service = ValidationCode.create();
		/* 这个jsonObject分别存储 code 和 随机地址(防止缓存)*/
		JSONObject valCode = new JSONObject();
		String captcha;
		/* 注意这种方式获取的URl前缀包含file:/需要手动过滤掉 */
		String address = new Integer((int)((Math.random()*9+1)*100000)).toString();
		String Url = this.getClass().getResource("/").toString();
		Url = Url.substring(5,Url.length());
		Url = Url+"../../resources/val-code/" + address + ".png";
		valCode.put("address", address);
		/* 输出图片 */
		FileOutputStream fos = null;
		try {
			File file = new File(Url);
			fos = new FileOutputStream(file);
			captcha = EncoderHelper.getChallangeAndWriteImage(service, "png", fos);
			valCode.put("code", captcha);
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (Exception e) {
				}
			}
		}
		return valCode;
	}
}