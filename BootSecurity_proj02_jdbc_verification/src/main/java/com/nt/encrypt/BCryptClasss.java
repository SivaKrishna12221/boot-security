package com.nt.encrypt;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptClasss {
	public static void main(String[] args) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String code1 = encoder.encode("sithaRam");
		String code2 = encoder.encode("saiKrishna");

		System.out.println(code1);// $2a$10$QdZ310MX/qmO0gKZG.I4k.u167sINbgZKZ6.qAbYE6RuxA3DvuOAm
		System.out.println(code2);// $2a$10$psUY7IliNaAHqK8qdxLXX.P/dwUsHKeHi5.dUMX3xwODZacaEg1fy

	}
}
