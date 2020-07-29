package com.jokey.bingo.niuke;

/**
 * @author JokeyFeng
 * @date: 2020/7/25
 * @project: spring-boot
 * @package: com.jokey.bingo.niuke
 * @comment: 密码按如下规则进行计分，并根据不同的得分为密码进行安全等级划分。
 * <p>
 * 一、密码长度:
 * <p>
 * 5 分: 小于等于4 个字符
 * <p>
 * 10 分: 5 到7 字符
 * <p>
 * 25 分: 大于等于8 个字符
 * <p>
 * 二、字母:
 * <p>
 * 0 分: 没有字母
 * <p>
 * 10 分: 全都是小（大）写字母
 * <p>
 * 20 分: 大小写混合字母
 * <p>
 * 三、数字:
 * <p>
 * 0 分: 没有数字
 * <p>
 * 10 分: 1 个数字
 * <p>
 * 20 分: 大于1 个数字
 * <p>
 * 四、符号:
 * <p>
 * 0 分: 没有符号
 * <p>
 * 10 分: 1 个符号
 * <p>
 * 25 分: 大于1 个符号
 * <p>
 * 五、奖励:
 * <p>
 * 2 分: 字母和数字
 * <p>
 * 3 分: 字母、数字和符号
 * <p>
 * 5 分: 大小写字母、数字和符号
 * <p>
 * 最后的评分标准:
 * <p>
 * >= 90: 非常安全
 * <p>
 * >= 80: 安全（Secure）
 * <p>
 * >= 70: 非常强
 * <p>
 * >= 60: 强（Strong）
 * <p>
 * >= 50: 一般（Average）
 * <p>
 * >= 25: 弱（Weak）
 * <p>
 * >= 0:  非常弱
 * <p>
 * <p>
 * 对应输出为：
 * <p>
 * VERY_SECURE
 * <p>
 * SECURE,
 * <p>
 * VERY_STRONG,
 * <p>
 * STRONG,
 * <p>
 * AVERAGE,
 * <p>
 * WEAK,
 * <p>
 * VERY_WEAK,
 * <p>
 * <p>
 * 请根据输入的密码字符串，进行安全评定。
 * <p>
 * 注：
 * <p>
 * 字母：a-z, A-Z
 * <p>
 * 数字：-9
 * <p>
 * 符号包含如下： (ASCII码表可以在UltraEdit的菜单view->ASCII Table查看)
 * <p>
 * !"#$%&'()*+,-./     (ASCII码：x21~0x2F)
 * <p>
 * :;<=>?@             (ASCII<=><=><=><=><=>码：x3A~0x40)
 * <p>
 * [\]^_`              (ASCII码：x5B~0x60)
 * <p>
 * {|}~                (ASCII码：x7B~0x7E)
 */
public class PasswordSecureLevel {
	
	public static void main(String[] args) {
		System.out.println(getPwdSecurityLevel("saf234fds~~~@"));
	}
	
	public static String getPwdSecurityLevel(String Password) {
		int sum = 0;
		sum += lengthSecure(Password);
		sum += letterSecure(Password);
		sum += numSecure(Password);
		sum += symbolSecure(Password);
		sum += awardSecure(Password);
		if (sum >= 90) {
			return "VERY_SECURE";
		} else if (sum >= 80) {
			return " SECURE";
		} else if (sum >= 70) {
			return "VERY_STRONG";
		} else if (sum >= 60) {
			return "STRONG";
		} else if (sum >= 50) {
			return "AVERAGE";
		} else if (sum >= 25) {
			return "WEAK";
		}
		return "VERY_WEAK";
	}
	
	/**
	 * 长度校验
	 *
	 * @param password
	 * @return
	 */
	public static int lengthSecure(String password) {
		if (password.length() <= 4) {
			return 5;
		}
		if (password.length() <= 7) {
			return 10;
		}
		return 25;
	}
	
	/**
	 * 字母校验
	 *
	 * @param password
	 * @return
	 */
	public static int letterSecure(String password) {
		int upperLetter = 0, lowerLetter = 0;
		
		for (int i = 0; i < password.length(); i++) {
			String chart = String.valueOf(password.charAt(i));
			if (chart.matches("[A~Z]")) {
				upperLetter++;
			}
			if (chart.matches("[a~z]")) {
				lowerLetter++;
			}
		}
		
		if (upperLetter != 0 && lowerLetter != 0) {
			return 20;
		}
		
		if ((upperLetter == 0 && lowerLetter != 0) || (upperLetter != 0 && lowerLetter == 0)) {
			return 10;
		}
		return 0;
	}
	
	/**
	 * 数字校验
	 *
	 * @param password
	 * @return
	 */
	public static int numSecure(String password) {
		int num = 0;
		for (int i = 0; i < password.length(); i++) {
			if (password.charAt(i) >= '0' && password.charAt(i) <= '9') {
				num++;
			}
		}
		if (num == 0) {
			return 0;
		}
		if (num == 1) {
			return 10;
		}
		return 20;
	}
	
	/**
	 * 特殊符号校验
	 *
	 * @param password
	 * @return
	 */
	public static int symbolSecure(String password) {
		int symbol = 0;
		for (int i = 0; i < password.length(); i++) {
			char ch = password.charAt(i);
			if (ch >= 0x21 && ch <= 0x2F) {
				symbol++;
			}
			if (ch >= 0x3A && ch <= 0x40) {
				symbol++;
			}
			if (ch >= 0x5B && ch <= 0x60) {
				symbol++;
			}
			if (ch >= 0x7B && ch <= 0x7E) {
				symbol++;
			}
		}
		if (symbol == 0) {
			return 0;
		}
		if (symbol == 1) {
			return 10;
		}
		return 25;
	}
	
	/**
	 * 奖励校验
	 *
	 * @param password
	 * @return
	 */
	public static int awardSecure(String password) {
		int letter = letterSecure(password);
		int number = numSecure(password);
		int symbol = symbolSecure(password);
		if (letter != 0 && number != 0 && symbol == 0) {
			return 2;
		}
		
		if (letter == 10 && number != 0 && symbol != 0) {
			return 3;
		}
		if (letter == 20 && number != 0 && symbol != 0) {
			return 5;
		}
		return 0;
	}
}
