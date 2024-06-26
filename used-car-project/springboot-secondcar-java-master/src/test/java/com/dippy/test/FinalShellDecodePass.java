package com.dippy.test;

import com.alibaba.fastjson.JSONObject;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Random;

public class FinalShellDecodePass {

	public static void main(String[] args) throws Exception {
		//finalShellpath为finalShell的安装路径+连接信息保存的路径
		String finalShellpath = "D:\\finalshell\\conn\\";
		ArrayList<String> pathFiles = getPathFiles(finalShellpath);
		ArrayList<String> passwords = new ArrayList<>();
		for (String pathFile : pathFiles) {
			String jsonStr = readJsonFile(pathFile);
			JSONObject jsonObject = JSONObject.parseObject(jsonStr);
			String username = "";
			String password = "";
			try {
				username = jsonObject.getString("user_name");
				password = decodePass(jsonObject.getString("password"));
			} catch (Exception ignored) {
				password = "密码解析错误";
			}
			String host = jsonObject.getString("host");
			String port = jsonObject.getString("port");
			passwords.add(host + ": "+ port + ": " + username + ": " + password);
		}
		Collections.sort(passwords);
		for (String p : passwords) {
			System.out.println(p);
		}
	}

	/**
	 * 读取文件目录下的所有json文件
	 *
	 * @param dirname 目录
	 * @return 文件列表
	 */
	public static ArrayList<String> getPathFiles(String dirname) {
		File dir = new File(dirname);
		File[] files = dir.listFiles();
		ArrayList<String> file_names = new ArrayList<String>();
		assert files != null;
		for (File file : files) {
			//判断是否是目录
			if (file.isDirectory()) {
				file_names.add(dirname + file.getName());
			}
			//判断是否是隐藏文件
			if (file.isHidden()) {
				file_names.add(dirname + file.getName());
			}
			if (file.isFile() && (!file.isHidden())) {  //判断是否是文件并不能是隐藏文件
				file_names.add(dirname + file.getName());
			}
		}
		return file_names;
	}

	//读取json文件
	public static String readJsonFile(String fileName) {
		String jsonStr = "";
		try {
			File jsonFile = new File(fileName);
			FileReader fileReader = new FileReader(jsonFile);
			Reader reader = new InputStreamReader(new FileInputStream(jsonFile));
			int ch = 0;
			StringBuilder sb = new StringBuilder();
			while ((ch = reader.read()) != -1) {
				sb.append((char)ch);
			}
			fileReader.close();
			reader.close();
			jsonStr = sb.toString();
			//            System.out.println(jsonStr);
			return jsonStr;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static byte[] desDecode(byte[] data, byte[] head) throws Exception {
		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(head);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey securekey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(2, securekey, sr);
		return cipher.doFinal(data);
	}

	public static String decodePass(String data) throws Exception {
		if (data == null) {
			return null;
		} else {
			String rs = "";
			byte[] buf = Base64.getDecoder().decode(data);
			byte[] head = new byte[8];
			System.arraycopy(buf, 0, head, 0, head.length);
			byte[] d = new byte[buf.length - head.length];
			System.arraycopy(buf, head.length, d, 0, d.length);
			byte[] bt = desDecode(d, ranDomKey(head));
			rs = new String(bt);

			return rs;
		}
	}

	static byte[] ranDomKey(byte[] head) {
		long ks = 3680984568597093857L / (long)(new Random((long)head[5])).nextInt(127);
		Random random = new Random(ks);
		int t = head[0];

		for (int i = 0; i < t; ++i) {
			random.nextLong();
		}

		long n = random.nextLong();
		Random r2 = new Random(n);
		long[] ld = new long[] {(long)head[4], r2.nextLong(), (long)head[7], (long)head[3], r2.nextLong(), (long)head[1], random.nextLong(), (long)head[2]};
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		long[] var15;
		var15 = ld;
		int var14 = ld.length;

		for (int var13 = 0; var13 < var14; ++var13) {
			long l = var15[var13];

			try {
				dos.writeLong(l);
			} catch (IOException var18) {
				var18.printStackTrace();
			}
		}

		try {
			dos.close();
		} catch (IOException var17) {
			var17.printStackTrace();
		}

		byte[] keyData = bos.toByteArray();
		keyData = md5(keyData);
		return keyData;
	}

	public static byte[] md5(byte[] data) {
		String ret = null;
		byte[] res = null;

		try {
			MessageDigest m;
			m = MessageDigest.getInstance("MD5");
			m.update(data, 0, data.length);
			res = m.digest();
			ret = new BigInteger(1, res).toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return res;
	}
}

