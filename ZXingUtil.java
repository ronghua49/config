package com.xyjsoft.core.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/** 
 * 类名: ZXingCodeUtil <br/> 
 * 类描述: 二维码工具类. <br/> 
 * date: 2018-4-13 上午10:01:13 <br/> 
 * 
 * @author 王浩伟 
 * @version 1.0
 * @since JDK 1.7 
 */
public class ZXingUtil {
	private static Logger logger = LoggerFactory.getLogger(ZXingUtil.class);
	private static final int QRCOLOR = 0xFF000000;   //默认是黑色
    private static final int BGWHITE = 0xFFFFFFFF;   //背景颜色
    
    /**支付宝logoBase64码*/
	public static final String alpBase64logo = "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";
	/**微信logoBase64码*/
	public static final String wxpBase64logo = "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";
    
	
	 /**
     * 生成一维码图片(Code128)
     * @param width 一维码宽度
     * @param height 一维码高度
     * @param content 一维码内容 
     * @return 一维码(Base64) 
     * */
    public static String getBarcode(int width,int height,String content) {
    	try {
    		// 文字编码  
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();  
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            
    		ZXingUtil zp = new ZXingUtil();
            BufferedImage bim = zp.getQrBufferedImage(content, BarcodeFormat.CODE_128, width, height, hints);
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.flush();
            ImageIO.write(bim, "png", baos);
            String imageBase64QRCode =  Base64.encodeBase64String(baos.toByteArray());
            baos.close();
            return "data:image/png;base64,"+imageBase64QRCode;
    	}catch(Exception e) {
    		logger.error("[ZXingUtil:getBarcode]",e);
    	}
    	return null;
    }
	/**
     * 生成二维码图片(Base64)
     * @param width 二维码宽度
     * @param height 二维码高度
     * @param content 二维码内容 
     * @return 二维码(Base64) 
     * */
    public static String getQRCode(int width,int height,String content){
   	    try
        {  
            ZXingUtil zp = new ZXingUtil();
            BufferedImage bim = zp.getQrBufferedImage(content, BarcodeFormat.QR_CODE, width, height, zp.getDecodeHintType());
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.flush();
            ImageIO.write(bim, "png", baos);
            String imageBase64QRCode =  Base64.encodeBase64String(baos.toByteArray());
            baos.close();
            return "data:image/png;base64,"+imageBase64QRCode;
        }
        catch (Exception e)
        {
        	logger.error("[ZXingUtil:getQRCode]",e);
        }
        return null;
    }
   
    /**
     * 生成带logo的二维码图片(Base64)
     * @param width 二维码宽度
     * @param height 二维码高度
     * @param content 二维码内容
     * @param logopath 二维码中的图片路径，绝对路径
     * @param qrcodetitle 二维码底部内容
     * @return 二维码(Base64)
     */
    public static String getLogoQRCode(int width,int height,String content,String logopath,String qrcodetitle)
    {
    	//String filePath = (javax.servlet.http.HttpServletRequest)request.getSession().getServletContext().getRealPath("/") + "resources/images/logoImages/llhlogo.png";
        //filePath是二维码logo的路径，但是实际中我们是放在项目的某个路径下面的，所以路径用上面的，把下面的注释就好
    	//String filePath = "C:/Users/luoguohui/Desktop/78310a55b319ebc4fa3aef658126cffc1f17168f.png";  
        try
        {  
            ZXingUtil zp = new ZXingUtil();
            BufferedImage bim = zp.getQrBufferedImage(content, BarcodeFormat.QR_CODE, width, height, zp.getDecodeHintType());
            BufferedImage logo = ImageIO.read(new File(logopath));
            return zp.addLogo_QRCode(bim, logo, new LogoConfig(), qrcodetitle);
        }
        catch (Exception e)
        {
        	logger.error("[ZXingUtil:getLogoQRCode]",e);
        }
        return null;
    }
    
    /**
     * 生成带logo的二维码图片(Base64)
     * @param width 二维码宽度
     * @param height 二维码高度
     * @param content 二维码内容
     * @param base64logo 二维码中的图片(base64)
     * @param qrcodetitle 二维码底部内容
     * @return 二维码(Base64)
     */
    public static String getLogoQRCode1(int width,int height,String content,String base64logo,String qrcodetitle)
    {
        try
        {  
            ZXingUtil zp = new ZXingUtil();
            BufferedImage bim = zp.getQrBufferedImage(content, BarcodeFormat.QR_CODE, width, height, zp.getDecodeHintType());
            byte[] logoByte = Base64.decodeBase64(base64logo);
            ByteArrayInputStream logoSteam = new ByteArrayInputStream(logoByte);
            BufferedImage logo = ImageIO.read(logoSteam);
            return zp.addLogo_QRCode(bim, logo, new LogoConfig(), qrcodetitle);
        }
        catch (Exception e)
        {
        	logger.error("[ZXingUtil:getLogoQRCode1]",e);
        }
        return null;
    }
    /**
     * 给二维码图片添加Logo
     * @param bim 二维码图片
     * @param logo logo图片
     * @param logoConfig logo配置(LogoConfig)
     * @param qrcodetitle 二维码底部内容
     */
    public String addLogo_QRCode(BufferedImage bim, BufferedImage logo, LogoConfig logoConfig, String qrcodetitle)
    {
        try
        {
            /** 读取二维码图片，并构建绘图对象  */
            BufferedImage image = bim;
            Graphics2D g = image.createGraphics();

            /** 设置logo的大小,本人设置为二维码图片的20%,因为过大会盖掉二维码  */
            int widthLogo = logo.getWidth(null)>image.getWidth()*3/10?(image.getWidth()*3/10):logo.getWidth(null), 
                heightLogo = logo.getHeight(null)>image.getHeight()*3/10?(image.getHeight()*3/10):logo.getWidth(null);

            /** logo放在中心  */
             int x = (image.getWidth() - widthLogo) / 2;
             int y = (image.getHeight() - heightLogo) / 2;
             /**
             * logo放在右下角
             *  int x = (image.getWidth() - widthLogo);
             *  int y = (image.getHeight() - heightLogo);
             */

            //开始绘制图片
            g.drawImage(logo, x, y, widthLogo, heightLogo, null);
            //g.drawRoundRect(x, y, widthLogo, heightLogo, 15, 15);
            //g.setStroke(new BasicStroke(logoConfig.getBorder()));
            //g.setColor(logoConfig.getBorderColor());
            //g.drawRect(x, y, widthLogo, heightLogo);
            g.dispose();

            //把商品名称添加上去，商品名称不要太长哦，这里最多支持两行。太长就会自动截取啦
            if (qrcodetitle != null && !qrcodetitle.equals("")) {
                //新的图片，把带logo的二维码下面加上文字
                BufferedImage outImage = new BufferedImage(400, 445, BufferedImage.TYPE_4BYTE_ABGR);
                Graphics2D outg = outImage.createGraphics();
                //画二维码到新的面板
                outg.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
                //画文字到新的面板
                outg.setColor(Color.BLACK); 
                outg.setFont(new Font("宋体",Font.BOLD,30)); //字体、字型、字号 
                int strWidth = outg.getFontMetrics().stringWidth(qrcodetitle);
                if (strWidth > 399) {
                    //长度过长就截取前面部分
                	//outg.drawString(productName, 0, image.getHeight() + (outImage.getHeight() - image.getHeight())/2 + 5 ); //画文字
                    //长度过长就换行
                    String productName1 = qrcodetitle.substring(0, qrcodetitle.length()/2);
                    String productName2 = qrcodetitle.substring(qrcodetitle.length()/2, qrcodetitle.length());
                    int strWidth1 = outg.getFontMetrics().stringWidth(productName1);
                    int strWidth2 = outg.getFontMetrics().stringWidth(productName2);
                    outg.drawString(productName1, 200  - strWidth1/2, image.getHeight() + (outImage.getHeight() - image.getHeight())/2 + 12 );
                    BufferedImage outImage2 = new BufferedImage(400, 485, BufferedImage.TYPE_4BYTE_ABGR);
                    Graphics2D outg2 = outImage2.createGraphics();
                    outg2.drawImage(outImage, 0, 0, outImage.getWidth(), outImage.getHeight(), null);
                    outg2.setColor(Color.BLACK); 
                    outg2.setFont(new Font("宋体",Font.BOLD,30)); //字体、字型、字号 
                    outg2.drawString(productName2, 200  - strWidth2/2, outImage.getHeight() + (outImage2.getHeight() - outImage.getHeight())/2 + 5 );
                    outg2.dispose(); 
                    outImage2.flush();
                    outImage = outImage2;
                }else {
                    outg.drawString(qrcodetitle, 200  - strWidth/2 , image.getHeight() + (outImage.getHeight() - image.getHeight())/2 + 12 ); //画文字 
                }
                outg.dispose(); 
                outImage.flush();
                image = outImage;
            }
            logo.flush();
            image.flush();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.flush();
            ImageIO.write(image, "png", baos);

            //二维码生成的路径，但是实际项目中，我们是把这生成的二维码显示到界面上的，因此下面的折行代码可以注释掉
            //可以看到这个方法最终返回的是这个二维码的imageBase64字符串
            //前端用 <img src="data:image/png;base64,${imageBase64QRCode}"/>  其中${imageBase64QRCode}对应二维码的imageBase64字符串
            
            /*写二维码真实图片*/
            //ImageIO.write(image, "png", new File("C:/Users/luoguohui/Desktop/TDC-" + new Date().getTime() + "test.png")); 
          
            String imageBase64QRCode =  Base64.encodeBase64String(baos.toByteArray());

            baos.close();
            return "data:image/png;base64,"+imageBase64QRCode;
        }
        catch (Exception e)
        {
            logger.error("[ZXingUtil:addLogo_QRCode]",e);
        }
        return null;
    }
    /**
     * 给二维码图片添加Logo
     * @param bim 二维码图片
     * @param logo logo图片
     * @param logoConfig logo配置(LogoConfig)
     * @param qrcodetitle 二维码底部内容
     */
    public BufferedImage addLogo_QRCodeT(BufferedImage bim, BufferedImage logo, LogoConfig logoConfig, String qrcodetitle)
    {
        try
        {
            /** 读取二维码图片，并构建绘图对象  */
            BufferedImage image = bim;
            Graphics2D g = image.createGraphics();

            /** 设置logo的大小,本人设置为二维码图片的20%,因为过大会盖掉二维码  */
            int widthLogo = logo.getWidth(null)>image.getWidth()*3/10?(image.getWidth()*3/10):logo.getWidth(null), 
                heightLogo = logo.getHeight(null)>image.getHeight()*3/10?(image.getHeight()*3/10):logo.getWidth(null);

            /** logo放在中心  */
             int x = (image.getWidth() - widthLogo) / 2;
             int y = (image.getHeight() - heightLogo) / 2;
             /**
             * logo放在右下角
             *  int x = (image.getWidth() - widthLogo);
             *  int y = (image.getHeight() - heightLogo);
             */

            //开始绘制图片
            g.drawImage(logo, x, y, widthLogo, heightLogo, null);
            //g.drawRoundRect(x, y, widthLogo, heightLogo, 15, 15);
            //g.setStroke(new BasicStroke(logoConfig.getBorder()));
            //g.setColor(logoConfig.getBorderColor());
            //g.drawRect(x, y, widthLogo, heightLogo);
            g.dispose();

            //把商品名称添加上去，商品名称不要太长哦，这里最多支持两行。太长就会自动截取啦
            if (qrcodetitle != null && !qrcodetitle.equals("")) {
                //新的图片，把带logo的二维码下面加上文字
                BufferedImage outImage = new BufferedImage(400, 445, BufferedImage.TYPE_4BYTE_ABGR);
                Graphics2D outg = outImage.createGraphics();
                //画二维码到新的面板
                outg.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
                //画文字到新的面板
                outg.setColor(Color.BLACK); 
                outg.setFont(new Font("宋体",Font.BOLD,30)); //字体、字型、字号 
                int strWidth = outg.getFontMetrics().stringWidth(qrcodetitle);
                if (strWidth > 399) {
                    //长度过长就截取前面部分
                	//outg.drawString(productName, 0, image.getHeight() + (outImage.getHeight() - image.getHeight())/2 + 5 ); //画文字
                    //长度过长就换行
                    String productName1 = qrcodetitle.substring(0, qrcodetitle.length()/2);
                    String productName2 = qrcodetitle.substring(qrcodetitle.length()/2, qrcodetitle.length());
                    int strWidth1 = outg.getFontMetrics().stringWidth(productName1);
                    int strWidth2 = outg.getFontMetrics().stringWidth(productName2);
                    outg.drawString(productName1, 200  - strWidth1/2, image.getHeight() + (outImage.getHeight() - image.getHeight())/2 + 12 );
                    BufferedImage outImage2 = new BufferedImage(400, 485, BufferedImage.TYPE_4BYTE_ABGR);
                    Graphics2D outg2 = outImage2.createGraphics();
                    outg2.drawImage(outImage, 0, 0, outImage.getWidth(), outImage.getHeight(), null);
                    outg2.setColor(Color.BLACK); 
                    outg2.setFont(new Font("宋体",Font.BOLD,30)); //字体、字型、字号 
                    outg2.drawString(productName2, 200  - strWidth2/2, outImage.getHeight() + (outImage2.getHeight() - outImage.getHeight())/2 + 5 );
                    outg2.dispose(); 
                    outImage2.flush();
                    outImage = outImage2;
                }else {
                    outg.drawString(qrcodetitle, 200  - strWidth/2 , image.getHeight() + (outImage.getHeight() - image.getHeight())/2 + 12 ); //画文字 
                }
                outg.dispose(); 
                outImage.flush();
                image = outImage;
            }
            logo.flush();
            image.flush();
            return image;
        }
        catch (Exception e)
        {
            logger.error("[ZXingUtil:addLogo_QRCodeT]",e);
        }
        return null;
    }
    /**
     * 生成二维码bufferedImage图片
     *
     * @param content
     *            编码内容
     * @param barcodeFormat
     *            编码类型
     * @param width
     *            图片宽度
     * @param height
     *            图片高度
     * @param hints
     *            设置参数
     * @return
     */
    public BufferedImage getQrBufferedImage(String content, BarcodeFormat barcodeFormat, int width, int height, Map<EncodeHintType, ?> hints)
    {
        MultiFormatWriter multiFormatWriter = null;
        BitMatrix bm = null;
        BufferedImage image = null;
        try
        {
            multiFormatWriter = new MultiFormatWriter();
            // 参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
            bm = multiFormatWriter.encode(content, barcodeFormat, width, height, hints);
            int w = bm.getWidth();
            int h = bm.getHeight();
            image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

            // 开始利用二维码数据创建Bitmap图片，分别设为黑（0xFFFFFFFF）白（0xFF000000）两色
            for (int x = 0; x < w; x++)
            {
                for (int y = 0; y < h; y++)
                {
                    image.setRGB(x, y, bm.get(x, y) ? QRCOLOR : BGWHITE);
                }
            }
        }
        catch (WriterException e)
        {
            logger.error("[ZXingUtil:getQrBufferedImage:WriterException]",e);
        }
        return image;
    }
    
    /**
     *	获取二维码图像
     *	注：导出jar文件后供Jaspersoft Studio或ireport中的表达式直接使用
     * @param content
     * @param width
     * @param height
     * @return
     */
    public static BufferedImage toQrBufferedImage(String content,int width,int height){
		// 用于设置QR二维码参数
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        // 设置QR二维码的纠错级别（H为最高级别）具体级别信息
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 设置编码方式
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, 0);
        BitMatrix bitMatrix = null;
		try {
			bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height,hints);
		} catch (WriterException e) {
			logger.error("[ZXingUtil:toQrBufferedImage:WriterException]",e);
		}
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
	}
    /**
     * 生成带logo的二维码图片(Base64)
     * @param width 二维码宽度
     * @param height 二维码高度
     * @param content 二维码内容
     * @param base64logo 二维码中的图片(base64)
     * @param qrcodetitle 二维码底部内容
     * @return 二维码(BufferedImage)
     */
    public static BufferedImage toQrBufferedImageWithLogo(int width,int height,String content,String base64logo,String qrcodetitle)
    {
        try
        {  
            ZXingUtil zp = new ZXingUtil();
            BufferedImage bim = zp.getQrBufferedImage(content, BarcodeFormat.QR_CODE, width, height, zp.getDecodeHintType());
            byte[] logoByte = Base64.decodeBase64(base64logo);
            ByteArrayInputStream logoSteam = new ByteArrayInputStream(logoByte);
            BufferedImage logo = ImageIO.read(logoSteam);
            return zp.addLogo_QRCodeT(bim, logo, new LogoConfig(), qrcodetitle);
        }
        catch (Exception e)
        {
        	logger.error("[ZXingUtil:toQrBufferedImageWithLogo]",e);
        }
        return null;
    }
    /**
     * 设置二维码的格式参数
     *
     * @return
     */
    public Map<EncodeHintType, Object> getDecodeHintType()
    {
        // 用于设置QR二维码参数
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        // 设置QR二维码的纠错级别（H为最高级别）具体级别信息
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 设置编码方式
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, 0);
        /*hints.put(EncodeHintType.MAX_SIZE, 350);
        hints.put(EncodeHintType.MIN_SIZE, 100);*/

        return hints;
    }
}

class LogoConfig
{
    // logo默认边框颜色
    public static final Color DEFAULT_BORDERCOLOR = Color.WHITE;
    // logo默认边框宽度
    public static final int DEFAULT_BORDER = 2;
    // logo大小默认为照片的1/5
    public static final int DEFAULT_LOGOPART = 5;

    private final int border = DEFAULT_BORDER;
    private final Color borderColor;
    private final int logoPart;

    /**
     * Creates a default config with on color {@link #BLACK} and off color
     * {@link #WHITE}, generating normal black-on-white barcodes.
     */
    public LogoConfig()
    {
        this(DEFAULT_BORDERCOLOR, DEFAULT_LOGOPART);
    }

    public LogoConfig(Color borderColor, int logoPart)
    {
        this.borderColor = borderColor;
        this.logoPart = logoPart;
    }

    public Color getBorderColor()
    {
        return borderColor;
    }

    public int getBorder()
    {
        return border;
    }

    public int getLogoPart()
    {
        return logoPart;
    }
}
