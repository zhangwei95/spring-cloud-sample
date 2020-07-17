package com.zhangwei95.service;


import com.zhangwei95.entity.Two;
import com.zhangwei95.mapper.TwoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yueyi2019
 */
@Service
public class RmTwoService {
	
	@Autowired
    TwoMapper mapper;
	
	public String rm2() {
		Two o = new Two();
		o.setId(2);
		o.setName("rm2");
		mapper.insertSelective(o);
		
		return "";
	}
}
